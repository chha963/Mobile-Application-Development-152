<?php

	header("Content-Type:application/json");
	include("functions.php");
	
	$from = $_GET['from'];
	$to = $_GET['to'];
	$amount = $_GET['amount'];
	
	if(!empty($from) && !empty($to) && !empty($amount))
	{
		$result = Exchange_Currency($from, $to, $amount);
		
		if(empty($result))
		{
			deliver_response(200, "Unknown Currency", NULL);
		}
		else
		{
			deliver_response(200, "Exchange Complete", $result);
		}
	}
	else
	{
		deliver_response(400, "Invalid Response", NULL);
	}
	
	function deliver_response($status, $status_message, $data)
	{
		header("HTTP/1.1 $status $status_message");
		
		$response['status'] = $status;
		$response['status_message'] = $status_message;
		$response['data'] = $data;
		
		$jsondata = json_encode($response);
		
		echo $jsondata;
	}
?>