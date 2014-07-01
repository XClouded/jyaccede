<?php

namespace JyAccede\Bundle\APIBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class FrontController extends Controller
{
    public function listAction(Request $request)
    {
        $isPmr = $request->request->get('isPmr');
        
        $stopPointsRepo=$this->getDoctrine()->getRepository("JyAccedeAPIBundle:StopPoint");
        $linesRepo=$this->getDoctrine()->getRepository("JyAccedeAPIBundle:Line");

        $stopPoints=$stopPointsRepo->findBy(array("isPmr"=>$isPmr),array("name"=>"asc"));
        $lines=$linesRepo->findBy(array("isPmr"=>$isPmr),array("name"=>"asc"));

        return $this->render('JyAccedeAPIBundle:Front:list.html.twig', array('lines' => $lines,"stops"=>$stopPoints));
    }
    
    public function updateAction(Request $request){
        if($request->isMethod("POST")){  
            $id = $request->request->get('id');
            if($request->request->get("type") == 'line'){
                $stopPointsRepo = $this->getDoctrine()->getRepository("JyAccedeAPIBundle:StopPoint");
                $entity = $stopPointsRepo->findOneBy(array("id"=>$id));
            }
            elseif($request->request->get("type") == 'stopPoint'){
                $linesRepo = $this->getDoctrine()->getRepository("JyAccedeAPIBundle:Line");
                $entity = $linesRepo->findOneBy(array("id"=>$id));
            }          
        }
        
        $this->redirect('/arrets_lignes');
    }
}
