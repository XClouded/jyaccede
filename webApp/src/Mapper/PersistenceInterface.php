<?php

/** 
* @file PersistenceInterface.php
*/

namespace Mapper;

interface PersistenceInterface
{
    /** Check if the object already exist or not.
     * 
     * @param Object $object
     */
    public function persist($object);
    
    /** Delete an object from the database.
    *
    *@param $object Object
    *
    *@return
    */
    public function remove($object);

    /** Insert an object in the database.
    *
    *@param $object Object
    *
    *@return
    */
    public function insert($object);

    /** Update a object in the database.
    *
    *@param $object Object
    *
    *@return
    */
    public function update($object);
}
