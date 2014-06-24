<?php

namespace Stikair\Bundle\FormExtraBundle\Form\Type;

use Symfony\Component\Form\AbstractType;
use Doctrine\ORM\EntityManager;

/**
 * Description of CkEditor.php
 *
 * @author david
 */
class CkEditorType extends AbstractType
{
    
    /**
     * @var ObjectManager
     */
    private $om;

    /**
     * @param ObjectManager $om
     */
    public function __construct(EntityManager $om)
    {
        $this->om = $om;
    }
    
    public function getName()
    {
        return "ckeditor";
    }

    public function getParent()
    {
        return "textarea";
    }
    
    public function setDefaultOptions(\Symfony\Component\OptionsResolver\OptionsResolverInterface $resolver) {
        parent::setDefaultOptions($resolver);
        $resolver->setOptional(array("custom_config_path"));
    }
    
    public function buildView(\Symfony\Component\Form\FormView $view, \Symfony\Component\Form\FormInterface $form, array $options) {
        if(array_key_exists("custom_config_path", $options))
        {
            $this->setConfig($options["custom_config_path"]);
        }
        
        $view->vars["custom_config_path"]=$this->config;
    }
    
    public function setConfig($config) {
        $this->config=$config;
    }
}
