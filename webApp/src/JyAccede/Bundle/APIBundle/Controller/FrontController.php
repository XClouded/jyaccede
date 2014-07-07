<?php

namespace JyAccede\Bundle\APIBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class FrontController extends Controller{
    
    public function listAction(Request $request){
        $isPmr = $request->request->get('isPmr');
        if(!$isPmr && $request->isMethod("POST")){
            $isPmr = 0;
        }
        else{
            $isPmr = 1;
        }
        
        $stopPointsRepo=$this->getDoctrine()->getRepository("JyAccedeAPIBundle:StopPoint");
        $linesRepo=$this->getDoctrine()->getRepository("JyAccedeAPIBundle:Line");

        $stopPoints=$stopPointsRepo->findBy(array("isPmr"=>$isPmr),array("name"=>"asc"), 10);
        $lines=$linesRepo->findBy(array("isPmr"=>$isPmr),array("name"=>"asc"), 10);

        return $this->render('JyAccedeAPIBundle:Front:list.html.twig', array('lines' => $lines,"stops"=>$stopPoints));
    }
    
    public function updateAction(Request $request){
        if($request->isMethod("POST")){  
            $id = $request->request->get('id');
            if($request->request->get("type") == 'stopPoint'){
                $stopPointsRepo = $this->getDoctrine()->getRepository("JyAccedeAPIBundle:StopPoint");
                $entity = $stopPointsRepo->findOneBy(array("id"=>$id));
                $entity->setIsPmr($request->request->get("isPmr"));
            }
            elseif($request->request->get("type") == 'line'){
                $linesRepo = $this->getDoctrine()->getRepository("JyAccedeAPIBundle:Line");
                $entity = $linesRepo->findOneBy(array("id"=>$id));
                $entity->setIsPmr($request->request->get("isPmr"));
            }          
        }
        
        return $this->redirect('arrets_lignes');
    }
    
    public function getJourneysAction($source, $destination){
        $url=" http://api.navitia.io/v1/journeys?from=".$source."&to=".$destination;

        /** Init cURL */
        $handle = curl_init($url);
        /** Additional headers */
        $headers = array("Authorization: d626ee70-52ca-4e0c-94fc-cf870bca4e07");

        curl_setopt($handle, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($handle, CURLOPT_RETURNTRANSFER, true);

        $json = curl_exec($handle);
        
        var_dump($json);exit;
        
        return $this->render('JyAccedeAPIBundle:Front:journeys.html.twig', array('journeys' => $json));
    }
}
