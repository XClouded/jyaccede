<?php

/** 
* @file Categorie.php
*/

namespace Model;

class Categorie
{
    protected $id;

    protected $name;

    /** Construct
    *
    * @param id int
    * @param name String
    * @param createdAt String
    */
    public function __construct($id = null, $name = null){
        $this->id = $id;
        $this->name = $name;
    }

    /** To get the id.
    *
    * @return int
    */
    public function getId(){
        return $this->id;
    }

    /** To set the id.
    *
    * @param int
    */
    public function setId($id){
        $this->id = $id;
    }

    /** To get the name.
    *
    * @return String
    */
    public function getName(){
        return $this->name;
    }

    /** To set the name.
    *
    * @param String
    */
    public function setName($name){
        $this->name = $name;
    }

    /** To test if the article is new
    *
    *@return boolean
    */
    public function isNew(){
        return (null === $this->id);
    }
}
