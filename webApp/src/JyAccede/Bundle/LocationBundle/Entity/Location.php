<?php

namespace JyAccede\Bundle\LocationBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Location
 */
class Location
{
    /**
     * @var integer
     */
    private $id;

    /**
     * @var string
     */
    private $name;

    /**
     * @var float
     */
    private $latitude;

    /**
     * @var float
     */
    private $longitude;

    /**
     * @var float
     */
    private $mark;

    /**
     * @var boolean
     */
    private $disabledAccess;


    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set name
     *
     * @param string $name
     * @return Location
     */
    public function setName($name)
    {
        $this->name = $name;
    
        return $this;
    }

    /**
     * Get name
     *
     * @return string 
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * Set latitude
     *
     * @param float $latitude
     * @return Location
     */
    public function setLatitude($latitude)
    {
        $this->latitude = $latitude;
    
        return $this;
    }

    /**
     * Get latitude
     *
     * @return float 
     */
    public function getLatitude()
    {
        return $this->latitude;
    }

    /**
     * Set longitude
     *
     * @param float $longitude
     * @return Location
     */
    public function setLongitude($longitude)
    {
        $this->longitude = $longitude;
    
        return $this;
    }

    /**
     * Get longitude
     *
     * @return float 
     */
    public function getLongitude()
    {
        return $this->longitude;
    }

    /**
     * Set mark
     *
     * @param float $mark
     * @return Location
     */
    public function setMark($mark)
    {
        $this->mark = $mark;
    
        return $this;
    }

    /**
     * Get mark
     *
     * @return float 
     */
    public function getMark()
    {
        return $this->mark;
    }

    /**
     * Set disabledAccess
     *
     * @param boolean $disabledAccess
     * @return Location
     */
    public function setDisabledAccess($disabledAccess)
    {
        $this->disabledAccess = $disabledAccess;
    
        return $this;
    }

    /**
     * Get disabledAccess
     *
     * @return boolean 
     */
    public function getDisabledAccess()
    {
        return $this->disabledAccess;
    }
    /**
     * @var \JyAccede\Bundle\LocationBundle\Entity\Category
     */
    private $category;


    /**
     * Set category
     *
     * @param \JyAccede\Bundle\LocationBundle\Entity\Category $category
     * @return Location
     */
    public function setCategory(\JyAccede\Bundle\LocationBundle\Entity\Category $category = null)
    {
        $this->category = $category;
    
        return $this;
    }

    /**
     * Get category
     *
     * @return \JyAccede\Bundle\LocationBundle\Entity\Category 
     */
    public function getCategory()
    {
        return $this->category;
    }
    /**
     * @var integer
     */
    private $category_id;


    /**
     * Set category_id
     *
     * @param integer $categoryId
     * @return Location
     */
    public function setCategoryId($categoryId)
    {
        $this->category_id = $categoryId;
    
        return $this;
    }

    /**
     * Get category_id
     *
     * @return integer 
     */
    public function getCategoryId()
    {
        return $this->category_id;
    }

    public function __toString(){
        return $this->name."";
    }
}