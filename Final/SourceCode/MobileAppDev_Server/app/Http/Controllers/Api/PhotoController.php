<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 5/8/2016
 * Time: 3:11 PM
 */

namespace App\Http\Controllers\Api;


class PhotoController {

    public static function postImage($image)
    {
        $res = \Cloudinary\Uploader::upload('data:image/png;base64,' . $image);
        return $res['secure_url'];
    }
} 