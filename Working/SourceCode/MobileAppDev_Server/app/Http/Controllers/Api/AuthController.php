<?php

namespace App\Http\Controllers\Api;

use JWTAuth;
use Mockery\CountValidator\Exception;
use Tymon\JWTAuth\Exceptions\JWTException;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Response;
use Illuminate\Support\Facades\DB;
use Tymon\JWTAuth\Exceptions\TokenExpiredException;
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
        $credentials = $request->only('familyandsurname', 'firstname', 'name', 'password', 'email', 'phone', 'street', 'ward', 'district', 'city', 'birthday');
		
		$rules = array(
			'email' => 'unique:users,email',
			'name' => 'unique:users,name'
		);
		
		$validator = Validator::make($credentials, $rules);
		
		if ($validator->fails()) 
		{
			return response()->json((new Response\AccountExistedResponse([], 'User already exists.'))->ToJsonArray(), 200);
		}
		else
		{
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

    private function normalLogin(Request $request)
    {
        $credentials = $request->only('name', 'password');
        try {
            if (!$token = JWTAuth::attempt($credentials)) {
                return response()->json((new Response\LoginFailResponse([], 'Login Fail. Please Check Username & PassWord'))->ToJsonArray(), 200);
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
