<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 4/25/2016
 * Time: 9:56 AM
 */

namespace App\Response;


class LoginFailResponse extends BaseResponse{

    public function __construct($result, $message)
    {
        parent::__construct($this::LOGIN_FAIL, $result, $message);
    }

} 