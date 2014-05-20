<?php

/** 
* @file Article.php
*/

namespace Model;

class Article
{
    protected $id;

    protected $name;

    protected $description;

    /**
    *
    * @param id int
    * @param name String
    * @param createdAt String
    */
    public function __construct($id = null, $name = '', $description = ''){
        $this->id = $id;
        $this->name = $name;
        $this->description = $description;
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

    /** To get the description field.
    *
    * @return String
    */
    public function getDescription(){
        return $this->description;
    }

    /** To set the description field.
    *
    * @param String
    */
    public function setDescription($description){
        $this->description = $description;
    }

    /** To test if the article is new
    *
    *@return boolean
    */
    public function isNew(){
        return (null === $this->id);
    }
}
