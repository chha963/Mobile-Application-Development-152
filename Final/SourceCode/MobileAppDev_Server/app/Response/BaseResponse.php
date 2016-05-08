<?php

namespace App\Response;

class BaseResponse
{
    const SUCCESS = 0;
    const LOGIN_FAIL = 1;
    const ACCOUNT_EXISTED = 2;
    const LOGIN_EXPIRED = 3;
    const ADDRESS_FORMAT = 4;

	/**
	 * @var int
	 */
	private $responseCode;
	
	/**
	 * @var 
	 */
	private $responseResult;

    /**
     * @var
     */
    private $responseMessage;
	
	public function __construct($code, $result, $message)
	{
		$this->responseCode = $code;
        $this->responseResult = $result;
        $this->responseMessage = $message;
	}

    public function GetResponseCode()
    {
        return $this->responseCode;
    }

    public function GetResponseResult()
    {
        return $this->responseResult;
    }

    public function GetResponseMessage()
    {
        return $this->responseMessage;
    }

    public function ToJsonArray()
    {
        return array(
            "responseCd" => $this->GetResponseCode(),
            "responseMsg" => $this->GetResponseMessage(),
            "resultSet" => $this->GetResponseResult()
        );
    }
}

?>