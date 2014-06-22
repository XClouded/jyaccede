<?php

namespace Stikair\Bundle\FormExtraBundle\Form\Type;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\PropertyAccess\PropertyAccess;

/**
 * Description of ImageFileType
 *
 * @author david
 */
class ImageFileType extends AbstractType {

    public function getName() {
        return "image_file";
    }

    public function setDefaultOptions(\Symfony\Component\OptionsResolver\OptionsResolverInterface $resolver) {
        $resolver->setOptional(array("image_path"));
    }

    public function buildView(\Symfony\Component\Form\FormView $view, \Symfony\Component\Form\FormInterface $form, array $options) {
        $imageUrl=null;
        if (array_key_exists('image_path', $options)) {
            $parentData = $form->getParent()->getData();

            if (null !== $parentData) {
                $accessor = PropertyAccess::createPropertyAccessor();
                $imageUrl = $accessor->getValue($parentData, $options['image_path']);
            } else {
                $imageUrl = null;
            }

            // définit une variable "image_url" qui sera disponible à l'affichage du champ
            
        }
        $view->vars['image_path'] = $imageUrl;
    }

    public function getParent() {
        return "file";
    }

//put your code here
}
