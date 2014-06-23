<?php

namespace JyAccede\Bundle\LocationBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction($name)
    {
        return $this->render('JyAccedeLocationBundle:Default:index.html.twig', array('name' => $name));
    }
}
