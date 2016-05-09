<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 5/8/2016
 * Time: 6:30 PM
 */

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Response;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use JWTAuth;

class ProfileController extends Controller {

    public function getBought(Request $request) {
        $user = AuthController::getAuthUser($request->input('token'));

        if(!$user)
            return response()->json((new Response\LoginExpiredResponse([], 'Login Invalid or Expired'))->ToJsonArray(), 200);

        $product = DB::select('select * from product where user_id = ? AND type = 0 limit 5', $user->id);

        return response()->json((new Response\SuccessResponse($product,'success'))->ToJsonArray(), 200);
    }

    public function getSold(Request $request) {
        $user = AuthController::getAuthUser($request->input('token'));

        if(!$user)
            return response()->json((new Response\LoginExpiredResponse([], 'Login Invalid or Expired'))->ToJsonArray(), 200);

        $product = DB::select('select * from product where user_id = ? AND type = 1 limit 5', $user->id);

        return response()->json((new Response\SuccessResponse($product, 'success'))->ToJsonArray(), 200);
    }

    public function editProfile(Request $request) {
        $user = AuthController::getAuthUser($request->input('token'));

        if(!$user)
            return response()->json((new Response\LoginExpiredResponse([], 'Login Invalid or Expired'))->ToJsonArray(), 200);

        $credentials = $request->only('last_name', 'first_name', 'name', 'email', 'phone', 'address', 'birthday', 'token');

        $lname=$request->input('last_name');
        $fname=$request->input('first_name');
        $name = $request->input('name');
        $email = $request->input('email');
        $phone = $request->input('phone');
        $address = $request->input('address');
        $birth = $request->input('birthday');
        $token = $request->input('token');
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
                $affected = DB::update('UPDATE users SET
                first_name = "'.$fname.'",
                last_name = "'.$lname.'",
                phone = "'.$phone.'",
                birthday = "'.$birth.'",
                name = "'.$name.'",
                email = "'.$name.'",
                address = "'.$address.'",
                latitude = "'.$latlong['latitude'].'",
                longitude = "'.$latlong['longitude'] . '"
                WHERE remember_token = "'.$token.'";
                ');
            }
            catch (Exception $e) {
                return response()->json((new Response\AccountExistedResponse([], 'Can not update.'))->ToJsonArray(), 200);
            }
// 			try {
// 				$user = User::create($credentials);
// 			}
// 			catch (Exception $e) {
// 				return response()->json((new Response\AccountExistedResponse([], 'User already exists.'))->ToJsonArray(), 200);
// 			}
        }

        return response()->json((new Response\SuccessResponse([], 'Update successful'))->ToJsonArray(), 200);
    }

} 