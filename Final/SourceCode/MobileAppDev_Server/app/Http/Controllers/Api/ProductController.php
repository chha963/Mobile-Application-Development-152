<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 5/8/2016
 * Time: 1:19 PM
 */

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Response;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use JWTAuth;
use App\Product;
use App\Gallery;

class ProductController extends Controller {

    public function getCategoryList(Request $request)
    {
        $user = AuthController::getAuthUser($request->input('token'));

        if(!$user)
            return response()->json((new Response\LoginExpiredResponse([], 'Login Invalid or Expired'))->ToJsonArray(), 200);

        $list = DB::select('select category_id, category_name from category');

        return response()->json((new Response\SuccessResponse($list, 'success'))->ToJsonArray(), 200);
    }

    public function getProduct(Request $request) {
        $user = AuthController::getAuthUser($request->input('token'));

        if(!$user)
            return response()->json((new Response\LoginExpiredResponse([], 'Login Invalid or Expired'))->ToJsonArray(), 200);

        $product = DB::select('select * from product p_id = ?', [$request->input('p_id')])[0];

        return response()->json((new Response\SuccessResponse($product,'success'))->ToJsonArray(), 200);
    }

    public function getTopBuyProductList(Request $request)
    {
        $user = AuthController::getAuthUser($request->input('token'));

        if(!$user)
            return response()->json((new Response\LoginExpiredResponse([], 'Login Invalid or Expired'))->ToJsonArray(), 200);

        $list = DB::select(
            'SELECT *
             FROM product
             WHERE type = 0
             ORDER BY p_id DESC
             LIMMIT 100'
        );

        return response()->json((new Response\SuccessResponse($list, 'success'))->ToJsonArray(), 200);
    }

    public function getGalleryURL(Request $request)
    {
        $user = AuthController::getAuthUser($request->input('token'));

        if(!$user)
            return response()->json((new Response\LoginExpiredResponse([], 'Login Invalid or Expired'))->ToJsonArray(), 200);

        $gallery = DB::select('select url from gallery_url where p_id = ?', [$request->input('p_id')]);

        return response()->json((new Response\SuccessResponse($gallery,'success'))->ToJsonArray(), 200);
    }

    public function publicProduct(Request $request)
    {
        $user = AuthController::getAuthUser($request->input('token'));

        if(!$user)
            return response()->json((new Response\LoginExpiredResponse([], 'Login Invalid or Expired'))->ToJsonArray(), 200);

        if($user->type == 0) // Normal user
            $date_end = date('Y-m-d G:i:s', strtotime("+7 days"));
        else if($user->type == 1)
            $date_end = date('Y-m-d G:i:s', strtotime("+10 days"));
        else
            response()->json(['error' => 'Error'], 500);

        $credentials = $request->only('name', 'type', 'category_id', 'price', 'brief_description', 'detail_description', 'icon', 'catalogue');

        $credentials['user_id'] = $user->id;
        $credentials['date_post'] =date('Y-m-d G:i:s');
        $credentials['date_end'] = $date_end;
        try {
            $credentials['icon'] = PhotoController::postImage($request->input('icon'));
            $credentials['catalogue'] = PhotoController::postImage($request->input('catalogue'));
        }
        catch(\Cloudinary\Error $e)
        {
            return response()->json((new Response\InvalidImageResponse([], 'fail'))->ToJsonArray(), 200);
        }

        $product = Product::create($credentials);

        $galleryList = $request->input('gallery');
        foreach($galleryList as $gallery)
        {
            try {
                Gallery::create($product->p_id, PhotoController::postImage($gallery['image']));
            }
            catch(\Cloudinary\Error $e)
            {
                DB::delete('delete from gallery_url where p_id = ?', [$product->p_id]);
                $product->delete();
                return response()->json((new Response\InvalidImageResponse([], 'fail'))->ToJsonArray(), 200);
            }
        }

        return response()->json((new Response\SuccessResponse([], 'success'))->ToJsonArray(), 200);
    }
} 