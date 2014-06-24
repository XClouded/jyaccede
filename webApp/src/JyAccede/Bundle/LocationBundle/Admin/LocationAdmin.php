<?php
/**
 * Created by PhpStorm.
 * User: david
 * Date: 09/06/2014
 * Time: 22:52
 */

namespace JyAccede\Bundle\LocationBundle\Admin;


use Sonata\AdminBundle\Admin\Admin;
use Sonata\AdminBundle\Datagrid\ListMapper;
use Sonata\AdminBundle\Datagrid\DatagridMapper;
use Sonata\AdminBundle\Form\FormMapper;

class LocationAdmin extends Admin
{
   // Fields to be shown on create/edit forms
   protected function configureFormFields(FormMapper $formMapper)
   {
       $formMapper
           ->add('name')
           ->add('latitude')
           ->add('longitude')
           ->add('mark')
           ->add('disabledAccess')
           ->add('category')
       ;
   }

   // Fields to be shown on filter forms
   protected function configureDatagridFilters(DatagridMapper $datagridMapper)
   {
       $datagridMapper
           ->add('name')
           ->add('disabledAccess')
           ->add('category')
       ;
   }

   // Fields to be shown on lists
   protected function configureListFields(ListMapper $listMapper)
   {
       $listMapper
           ->addIdentifier('name')
           ->add('category')
           ->add('disabledAccess')
       ;
   }
}