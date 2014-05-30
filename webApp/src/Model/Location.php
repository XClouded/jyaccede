<?php

/** 
* @file Location.php
*/
namespace Model;

class Location{
    protected $id;

    protected $name;

    protected $latitude;
		
    protected $longitude;

    protected $mark;

    protected $idCategory;

    protected $disabledAccess;

    /** Construct
    *
    * @param id int
    * @param name String
    * @param createdAt String
    */
    public function __construct($id = null, $name = null, $latitude = null, $longitude = null, $mark = 0, $idCategory = null, $disabledAccess = false){
        $this->id = $id;
        $this->name = $name;
        $this->latitude = $latitude;
        $this->longitude = $longitude;
        $this->mark = $mark;
        $this->idCategory = $idCategory;
        $this->disabledAccess = $disabledAccess;
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

    /** To get the latitude field.
    *
    * @return String
    */
    public function getLatitude(){
        return $this->latitude;
    }

    /** To set the latitude field.
    *
    * @param String
    */
    public function setLatitude($latitude){
        $this->latitude = $latitude;
    }
	
	 /** To get the longitude field.
    *
    * @return String
    */
    public function getLongitude(){
        return $this->longitude;
    }
	
	/** To set the longitude field.
    *
    * @param String
    */
	public function setLongitude($longitude){
        $this->longitude = $longitude;
    }
	
	/** To get the mark field.
    *
    * @return String
    */
    public function getMark(){
        return $this->mark;
    }
	
	/** To set the mark field.
    *
    * @param String
    */
	public function setMark($mark){
        $this->mark = $mark;
    }
	
	/** To get the idCategory field.
    *
    * @return String
    */
    public function getIdCategory(){
        return $this->idCategory;
    }
	
	/** To set the idCategory field.
    *
    * @param String
    */
	public function setIdCategory($idCategory){
        $this->idCategory = $idCategory;
    }
	
	/** To get the disabledAccess field.
    *
    * @return String
    */
    public function getDisabledAccess(){
        return $this->disabledAccess;
    }
	
	/** To set the longitude field.
    *
    * @param String
    */
	public function setDisabledAccess($disabledAccess){
        $this->disabledAccess = $disabledAccess;
    }

    /** To test if the article is new
    *
    *@return boolean
    */
    public function isNew(){
        return (null === $this->id);
    }
}
