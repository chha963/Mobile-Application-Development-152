<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 5/8/2016
 * Time: 9:57 AM
 */

namespace App\Http\Controllers\Api;

use JWTAuth;
use App\Http\Controllers\Controller;
use App\Response;

class LocationController extends Controller {

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

    public static function Get_LatLng_From_Google_Maps($address)
    {
        $address = urlencode($address);

        $url = "http://maps.googleapis.com/maps/api/geocode/json?address=$address&sensor=false";

        // Make the HTTP request
        $data = @file_get_contents($url);
        // Parse the json response
        $jsondata = json_decode($data,true);

        // If the json data is invalid, return empty array
        if ($jsondata["status"] != "OK")   return array();

        $LatLng = array(
            'latitude' => $jsondata["results"][0]["geometry"]["location"]["lat"],
            'longitude' => $jsondata["results"][0]["geometry"]["location"]["lng"],
        );

        return $LatLng;
    }
} 