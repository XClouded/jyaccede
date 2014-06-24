<?php
/**
 * Created by PhpStorm.
 * User: david
 * Date: 09/06/2014
 * Time: 22:52
 */

namespace JyAccede\Bundle\APIBundle\Admin;


use Sonata\AdminBundle\Admin\Admin;
use Sonata\AdminBundle\Datagrid\ListMapper;
use Sonata\AdminBundle\Datagrid\DatagridMapper;
use Sonata\AdminBundle\Form\FormMapper;
use Sonata\AdminBundle\Route\RouteCollection;

class LinesAdmin extends Admin
{

    public function getBatchActions()
    {
        // retrieve the default batch actions (currently only delete)
        $actions = parent::getBatchActions();

        if (
            $this->hasRoute('edit') && $this->isGranted('EDIT') &&
            $this->hasRoute('delete') && $this->isGranted('DELETE')
        ) {
            $actions['changePMR'] = array(
                'label' => "Changer le status PMR",
                'ask_confirmation' => true
            );

            $actions['changeValid'] = array(
                'label' => "Changer le status Valide",
                'ask_confirmation' => true
            );

        }

        return $actions;
    }

    protected function configureRoutes(RouteCollection $collection)
    {
        // to remove a single route
        $collection->remove('create');
    }

   // Fields to be shown on create/edit forms
   protected function configureFormFields(FormMapper $formMapper)
   {
       $formMapper
           ->add('name')
           ->add("idCanalTp")
           ->add("isPmr")
           ->add("isValid")
       ;
   }

   // Fields to be shown on filter forms
   protected function configureDatagridFilters(DatagridMapper $datagridMapper)
   {
       $datagridMapper
            ->add("idCanalTp")
           ->add('name')
           ->add("isPmr")
           ->add("isValid")
       ;
   }

   // Fields to be shown on lists
   protected function configureListFields(ListMapper $listMapper)
   {
       $listMapper
           ->addIdentifier("idCanalTp")
           ->addIdentifier('name')
           ->add("isPmr")
          ->add("isValid")
       ;
   }
}