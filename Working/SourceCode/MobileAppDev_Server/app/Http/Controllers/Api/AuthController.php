<?php

namespace App\Http\Controllers\Api;

use JWTAuth;
use Mockery\CountValidator\Exception;
use Tymon\JWTAuth\Exceptions\JWTException;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Response;
use Illuminate\Support\Facades\DB;
use App\User;
use Validator;

class AuthController extends Controller {

    /**
     * API Login, on success return JWT Auth token
     *
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function login(Request $request)
    {
        $type = $request->input('type');

        switch($type) {
            case 'normal':
                return $this->normalLogin($request);
                break;
            default:
                return response()->json(['error' => 'Could Not Create Token'], 500);
                break;
        }
    }

    /**
     * API Register
     *
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function register(Request $request)
    {
        $credentials = $request->only('last_name', 'first_name', 'name', 'password', 'email', 'phone', 'address', 'birthday');
		
		$rules = array(
			'email' => 'unique:users,email',
			'name' => 'unique:users,name',
		);
		
		$validator = Validator::make($credentials, $rules);

        if ($validator->fails())
        {
            return response()->json((new Response\AccountExistedResponse([], 'User already exists.'))->ToJsonArray(), 200);
        }
		else
		{
            $latlong = LocationController::Get_LatLng_From_Google_Maps($request->input('address'));
            if(empty($latlong))
            {
                return response()->json((new Response\WrongAddressFormatResponse([], 'Wrong Address.'))->ToJsonArray(), 200);
            }
            else
            {
                $credentials['latitude'] = $latlong['latitude'];
                $credentials['longitude'] = $latlong['longitude'];
            }

			try {
				$user = User::create($credentials);
			}
			catch (Exception $e) {
				return response()->json((new Response\AccountExistedResponse([], 'User already exists.'))->ToJsonArray(), 200);
			}
		}
		
        return response()->json((new Response\SuccessResponse([], 'Register successful'))->ToJsonArray(), 200);
    }

    /**
     * Log out
     * Invalidate the token, so user cannot use it anymore
     * They have to relogin to get a new token
     * 
     * @param Request $request
     */
    public function logout(Request $request) {
        $this->validate($request, [
            'token' => 'required' 
        ]);
        
        JWTAuth::invalidate($request->input('token'));
    }

    public static function getAuthUser($token)
    {
        $users = DB::select('select * from users where remember_token = ?', [$token]);

        if(count($users) != 1)
            return false;
        return $users;
    }

    private function normalLogin(Request $request)
    {
        $credentials = $request->only('name', 'password');
        try {
            if (!$token = JWTAuth::attempt($credentials)) {
                return response()->json((new Response\LoginFailResponse([], 'Login Fail. Please Check Username or PassWord'))->ToJsonArray(), 200);
            }
        } catch (JWTException $e) {
            return response()->json((new Response\LoginFailResponse([], 'Something Went Wrong.Please Try Again'))->ToJsonArray(), 200);
        }

        $users = DB::select('select id, avatar from users where name = ?', [$request->input('name')])[0];
        DB::update('update users set remember_token = :token where id = :id', ['token' => $token, 'id' => $users->id]);

        return response()->json((new Response\SuccessResponse(
            array(
                'token' => $token,
                'username' => $request->input('name'),
                'avatar' => $users->avatar
            ),
            'Login Success')
        )->ToJsonArray(), 200);
    }
}
