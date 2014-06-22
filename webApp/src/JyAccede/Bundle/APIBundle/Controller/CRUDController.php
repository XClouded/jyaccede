<?php
/**
 * Created by PhpStorm.
 * User: David
 * Date: 17/06/14
 * Time: 22:37
 */

namespace JyAccede\Bundle\APIBundle\Controller;

use Sonata\AdminBundle\Datagrid\ProxyQueryInterface;
use Sonata\AdminBundle\Controller\CRUDController as CRUD;
use Symfony\Component\HttpFoundation\RedirectResponse;


class CRUDController extends CRUD{
    public function batchActionChangePMR(ProxyQueryInterface $selectedModelQuery)
    {

        $request = $this->get('request');
        $modelManager = $this->admin->getModelManager();

        $selectedModels = $selectedModelQuery->execute();

        // do the merge work here

        try {
            foreach ($selectedModels as $selectedModel) {
                /* @var \JyAccede\Bundle\APIBundle\Entity\StopPoint $selectedModel */
                $selectedModel->setIsPmr(!$selectedModel->getIsPmr());
                $modelManager->update($selectedModel);
            }
        } catch (\Exception $e) {
            $this->addFlash('sonata_flash_error', 'Euh ... y a un blaireau dans le moteur je crois...');

            return new RedirectResponse(
                $this->admin->generateUrl('list',$this->admin->getFilterParameters())
            );
        }

        $this->addFlash('sonata_flash_success', 'Rien à signaler chef !');

        return new RedirectResponse(
            $this->admin->generateUrl('list',$this->admin->getFilterParameters())
        );
    }


    public function batchActionChangeValid(ProxyQueryInterface $selectedModelQuery)
    {

        $request = $this->get('request');
        $modelManager = $this->admin->getModelManager();

        $selectedModels = $selectedModelQuery->execute();

        // do the merge work here

        try {
            foreach ($selectedModels as $selectedModel) {
                /* @var \JyAccede\Bundle\APIBundle\Entity\StopPoint $selectedModel */
                $selectedModel->setIsValid(!$selectedModel->getIsValid());
                $modelManager->update($selectedModel);
            }
        } catch (\Exception $e) {
            $this->addFlash('sonata_flash_error', 'Y a eu un souci dans le bouzin');

            return new RedirectResponse(
                $this->admin->generateUrl('list',$this->admin->getFilterParameters())
            );
        }

        $this->addFlash('sonata_flash_success', 'Tout a tourné nikel');

        return new RedirectResponse(
            $this->admin->generateUrl('list',$this->admin->getFilterParameters())
        );
    }
} 