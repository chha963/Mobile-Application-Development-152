<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 5/4/2016
 * Time: 11:23 PM
 */

namespace App\Http\Controllers\Api;

use JWTAuth;
use App\Http\Controllers\Controller;
use App\Response;

class HelperController extends Controller {

    /**
     * Get city Infomation
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function cityInfo()
    {
        $ward = array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        $district = array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        $resultSet = array(
            "ward" => $ward,
            "district" => $district
        );

        return response()->json((new Response\SuccessResponse($resultSet, 'Information Fetch successful'))->ToJsonArray(), 200);
    }

} 