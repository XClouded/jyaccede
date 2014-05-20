<?php

/** 
* @file SqlException.php
*/

namespace Exception;

class SqlException extends \RuntimeException
{
    private $statusCode;

    public function __construct($statusCode = 500, $message = null, \Exception $previous = null){
        parent::__construct($message, 0, $previous);

        $this->statusCode = $statusCode;
    }

    public function getStatusCode(){
        return $this->statusCode;
    }
}
