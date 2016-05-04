<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 4/25/2016
 * Time: 9:58 AM
 */

namespace App\Response;


class LoginExpiredResponse extends BaseResponse{

    public function __construct($result, $messsage)
    {
        parent::__construct($this::LOGIN_EXPIRED, $result, $messsage);
    }

} 