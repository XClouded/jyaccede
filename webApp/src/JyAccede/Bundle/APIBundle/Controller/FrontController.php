<?php

namespace JyAccede\Bundle\APIBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class FrontController extends Controller
{
    public function listAction()
    {
        $stopPointsRepo=$this->getDoctrine()->getRepository("JyAccedeAPIBundle:StopPoint");
        $linesRepo=$this->getDoctrine()->getRepository("JyAccedeAPIBundle:Line");

        $stopPoints=$stopPointsRepo->findBy(array("isPmr"=>"1"),array("name"=>"asc"));
        $lines=$linesRepo->findBy(array("isPmr"=>"1"),array("name"=>"asc"));

        $objects=array_merge($stopPoints,$lines);

        return $this->render('JyAccedeAPIBundle:Front:list.html.twig', array('lines' => $lines,"stops"=>$stopPoints));
    }
}
