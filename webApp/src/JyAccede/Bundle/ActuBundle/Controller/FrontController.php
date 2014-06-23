<?php

namespace JyAccede\Bundle\ActuBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class FrontController extends Controller
{
    public function homeAction()
    {
        $actuRep=$this->getDoctrine()->getRepository("JyAccedeActuBundle:Actu");
        $actus=$actuRep->findBy(array("active"=>"1"),array("id"=>"desc"),3);
        return $this->render('JyAccedeActuBundle:Front:home.html.twig', array('actus' => $actus));
    }

    public function showAction($id)
    {
        $actuRep=$this->getDoctrine()->getRepository("JyAccedeActuBundle:Actu");
        $actu=$actuRep->find($id);
        return $this->render('JyAccedeActuBundle:Front:show.html.twig', array('actu' => $actu));
    }
}
