<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

\Cloudinary::config(array(
    "cloud_name" => "govi-app",
    "api_key" => "975278865575821",
    "api_secret" => "ctmvFxen--BwMMyPm0yrvVdK9kU"
));

Route::get('/', function () {
    return view('welcome');
});

Route::group(['prefix' => 'api'], function() {
    Route::post('login', 'Api\AuthController@login');
    Route::post('register', 'Api\AuthController@register');
    Route::get('cityInfo', 'Api\LocationController@cityInfo');

    Route::post('headerBanner', 'Api\AdsController@getHeaderBanner');
    Route::post('categoryList', 'Api\ProductController@getCategoryList');

    Route::post('productDetail', 'Api\ProductController@getProduct');
    Route::post('productGallery', 'Api\ProductController@getGalleryURL');
    Route::post('userBought', 'Api\ProfileController@getBought');
    Route::post('userSold', 'Api\ProfileController@getSold');

    Route::post('editProfile', 'Api\ProfileController@editProfile');
    Route::post('editPassword', 'Api\ProfileController@editPassword');

    Route::post('publicProduct', 'Api\ProductController@publicProduct');
});
