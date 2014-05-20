<?php

/** 
* @file Request.php
*/

namespace Http;

class Request
{
    const GET    = 'GET';
    const POST   = 'POST';
    const PUT    = 'PUT';
    const DELETE = 'DELETE';

    private $parameters = array();

    /** Create an instance of Request.
    * 
    */
    public static function createFromGlobals(){
		return new self($_GET, $_POST);
    }

    /** Construct of the class
    * 
    * @param $query Array GET parameters
    * @param $request Array POST parameters
    * 
    */
    public function __construct(array $query = array(), array $request = array()){
        $this->parameters = array_merge($query, $request);
    }

    /** Get the current method
    * 
    */
    public function getMethod(){
        $method = isset($_SERVER['REQUEST_METHOD']) ? $_SERVER['REQUEST_METHOD'] : self::GET;

        if (self::POST === $method){
            return $this->getParameter('_method', $method); //retourne la vrai méthode
        }

        return $method;
    }

    /** Get parameter for the Http Request
    *
    * @param $name String the name of the parameter
    * @param $default String the default value if $name is not found.
    * 
    */
    public function getParameter($name, $default = null){
        if(!array_key_exists($name, $this->parameters)){
            return $default;
        }
        
        return $this->parameters[$name];
    }

    /** Get the current URI
    * 
    */
    public function getUri(){
        $uri = isset($_SERVER['REQUEST_URI']) ? $_SERVER['REQUEST_URI'] : '/';

        if ($pos = strpos($uri, '?')){
            $uri = substr($uri, 0, $pos);
        }

        return $uri;
    }

    /**
    * 
    */
    public function guessBestFormat(){
        return 'text/html';
    }
}
