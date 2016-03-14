<?php

	function Exchange_Currency($from, $to, $amount)
	{
		$VietName_Currency = array( 'USD' => 22871.0,
									'EUR' => 25013.3,
									'GBP' => 31736.3,
									'CAD' => 16928.3,
									'AUD' => 16310.5);
									
		if($from == 'VND')
		{
			return intval($amount) / $VietName_Currency[$to];
		}
		else
		{
			return NULL;
		}
	}

?>