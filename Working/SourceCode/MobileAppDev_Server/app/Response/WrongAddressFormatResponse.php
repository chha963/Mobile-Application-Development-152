<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 5/7/2016
 * Time: 10:42 AM
 */

namespace App\Response;


class WrongAddressFormatResponse extends BaseResponse {

    public function __construct($result, $message)
    {
        parent::__construct($this::ADDRESS_FORMAT, $result, $message);
    }
} 