<?php

trait EventDispatcherTrait
{
    private $events = [];

    /** Add a listener.
    * 
    * @param String $name Name of the listener
    * @param String $callable Name of the listener
    * 
    */
    public function addListener($name, $callable){
        $this->events[$name][] = $callable;
    }

    /** Call the corresponding callable (with arguments) for the given name
    * 
    * @param String $name
    * @param Array $arguments
    * 
    */
    public function dispatch($name, array $arguments = []){
        foreach ($this->events[$name] as $callable) {
            call_user_func_array($callable, $arguments);
        }
    }
}
