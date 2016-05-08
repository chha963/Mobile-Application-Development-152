<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 5/8/2016
 * Time: 10:07 AM
 */

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Response;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use JWTAuth;

class AdsController extends Controller  {

    public function getHeaderBanner(Request $request)
    {
        $user = AuthController::getAuthUser($request->input('token'));

        if(!$user)
            return response()->json((new Response\LoginExpiredResponse([], 'Login Invalid or Expired'))->ToJsonArray(), 200);

        $banner = DB::select(
            'SELECT icon, name, type, brief_description, price, link
                FROM ads,
                    (SELECT RAND() * (SELECT MAX(ads_id)-5 FROM ads) AS tid) AS tmp
                WHERE ads.ads_id >= tmp.tid
                ORDER BY ads_id ASC
                LIMIT 5'
        );

        return response()->json((new Response\SuccessResponse($banner,'success'))->ToJsonArray(), 200);
    }

} 