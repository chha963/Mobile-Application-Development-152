<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 4/25/2016
 * Time: 9:51 AM
 */

namespace App\Response;


class SuccessResponse extends BaseResponse {

    public function __construct($result, $message)
    {
        parent::__construct($this::SUCCESS, $result, $message);
    }
} 