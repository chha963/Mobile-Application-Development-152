<?php

	header("Content-Type:application/json");
	include("function.php");
	
	$lat = $_GET['latitude'];
	$lon = $_GET['longtitude'];
	
	if(!empty($lat) && !empty($lon))
	{
		$result = Get_Address_From_Google_Maps($lat, $lon);
		
		$jsondata = json_encode($result);
		
		echo $jsondata;
	}
?>