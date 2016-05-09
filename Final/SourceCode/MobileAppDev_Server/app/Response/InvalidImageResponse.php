<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 5/8/2016
 * Time: 5:58 PM
 */

namespace App\Response;


class InvalidImageResponse extends BaseResponse {

    public function __construct($result, $messsage)
    {
        parent::__construct($this::INVALID_IMAGE, $result, $messsage);
    }

} 