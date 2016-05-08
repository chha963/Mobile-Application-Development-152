<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 5/4/2016
 * Time: 11:07 PM
 */

namespace App\Response;


class AccountExistedResponse extends BaseResponse {

    public function __construct($result, $messsage)
    {
        parent::__construct($this::ACCOUNT_EXISTED, $result, $messsage);
    }
} 