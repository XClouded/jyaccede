<?php

namespace JyAccede\Bundle\APIBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * StopPoint
 */
class StopPoint
{

    public function __construct(){
        $this->isPmr=false;
        $this->isValid=false;
    }
    /**
     * @var integer
     */
    private $id;

    /**
     * @var string
     */
    private $idCanalTp;

    /**
     * @var string
     */
    private $name;

    /**
     * @var boolean
     */
    private $isPmr;

    /**
     * @var boolean
     */
    private $isValid;


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
     * Set idCanalTp
     *
     * @param string $idCanalTp
     * @return StopPoint
     */
    public function setIdCanalTp($idCanalTp)
    {
        $this->idCanalTp = $idCanalTp;
    
        return $this;
    }

    /**
     * Get idCanalTp
     *
     * @return string 
     */
    public function getIdCanalTp()
    {
        return $this->idCanalTp;
    }

    /**
     * Set name
     *
     * @param string $name
     * @return StopPoint
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
     * Set isPmr
     *
     * @param boolean $isPmr
     * @return StopPoint
     */
    public function setIsPmr($isPmr)
    {
        $this->isPmr = $isPmr;
    
        return $this;
    }

    /**
     * Get isPmr
     *
     * @return boolean 
     */
    public function getIsPmr()
    {
        return $this->isPmr;
    }

    /**
     * Set isValid
     *
     * @param boolean $isValid
     * @return StopPoint
     */
    public function setIsValid($isValid)
    {
        $this->isValid = $isValid;
    
        return $this;
    }

    /**
     * Get isValid
     *
     * @return boolean 
     */
    public function getIsValid()
    {
        return $this->isValid;
    }
}