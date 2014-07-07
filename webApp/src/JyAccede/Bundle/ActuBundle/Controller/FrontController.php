<?php

namespace JyAccede\Bundle\ActuBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class FrontController extends Controller
{
    public function homeAction(){
        $actuRep=$this->getDoctrine()->getRepository("JyAccedeActuBundle:Actu");
        $actus=$actuRep->findBy(array("active"=>"1"),array("id"=>"desc"),3);
        
        $categories=$this->getCategories();
        
        return $this->render('JyAccedeActuBundle:Front:home.html.twig', array('actus' => $actus, "categories" => $categories));
    }

    public function showAction($id){
        $actuRep=$this->getDoctrine()->getRepository("JyAccedeActuBundle:Actu");
        $actu=$actuRep->find($id);
        return $this->render('JyAccedeActuBundle:Front:show.html.twig', array('actu' => $actu));
    }
    
    private function signin($time, $method){
        $toSign = "GET\nx-jispapi-timestamp:".$time."\n".$method;
        $hash=hash_hmac("sha1",$toSign,"test-jispapi-secret-access-key");
        $signature=base64_encode($hash);
        
        return $signature;
    }
    
    protected function getCategories(){
        $url="http://dev.jaccede.com/api/v2/places/categories";
        $time=time();
        $method='/api/v2/places/categories';
        $authorization="JISAPI test-jispapi-access-key-id:".$this->signin($time, $method);

        /** Init cURL */
        $handle = curl_init($url);

        /** Additional headers */
        $headers =array("Authorization:".$authorization,"x-jispapi-timestamp:".$time);

        curl_setopt($handle, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($handle, CURLOPT_RETURNTRANSFER, true);

        $json = curl_exec($handle);

        curl_close($handle);
        return $json;
    }
}
