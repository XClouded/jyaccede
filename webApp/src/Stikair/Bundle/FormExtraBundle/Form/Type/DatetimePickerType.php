<?php

namespace Stikair\Bundle\FormExtraBundle\Form\Type;
use Symfony\Component\Form\AbstractType;
use Doctrine\ORM\EntityManager;
/**
 * Description of DatetimePickerType
 *
 * @author david
 */
class DatetimePickerType extends AbstractType{
    
    private $jsDate="";
    
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
    
    public function getName() {
        return "datetimepicker";
    }
    
    public function getParent()
    {
        return "text";
    }
    
    public function setDefaultOptions(\Symfony\Component\OptionsResolver\OptionsResolverInterface $resolver) {
        parent::setDefaultOptions($resolver);
        $resolver->setRequired(array("type"));
    }
    
    public function buildView(\Symfony\Component\Form\FormView $view, \Symfony\Component\Form\FormInterface $form, array $options) {
        
        switch($options["type"])
        {
            case "date":
                $this->jsDate="pickTime: false";
                break;
            case "time":
                $this->jsDate="pickDate: false";
                break;
            default:
                break;
        }
        
        
        $view->vars["type"]=$this->jsDate;
    }
    
    public function buildForm(\Symfony\Component\Form\FormBuilderInterface $builder, array $options) {
        parent::buildForm($builder, $options);
        $transforme=new \Symfony\Component\Form\Extension\Core\DataTransformer\DateTimeToStringTransformer(null,null,'d/m/Y H:i:s');
        $builder->addViewTransformer($transforme);
    }

//put your code here
}