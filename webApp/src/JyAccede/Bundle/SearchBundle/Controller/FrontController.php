<?php

namespace JyAccede\Bundle\SearchBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class FrontController extends Controller{
    
    private function signin($time, $method){
        $toSign = "GET\nx-jispapi-timestamp:".$time."\n".$method;
        $hash=hash_hmac("sha1",$toSign,"test-jispapi-secret-access-key");
        $signature=base64_encode($hash);
        
        return $signature;
    }
    
    protected function searchForCity($city, $what){
        $time=time();
        $method='/api/v2/places/search/';
        $url="http://dev.jaccede.com/api/v2/places/search/?where=".$city."&what=".$what;

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

    public function searchAction(Request $request){
        $city=false;
        $json="{}";
        $userAddress="{}";
        $from="";
        
        if($request->isMethod("POST")){
            $city = $request->request->get("city");
            $what = $request->request->get("what");
            $how = $request->request->get("how");
            
            $from = $request->request->get("from");
            $url = "https://maps.googleapis.com/maps/api/geocode/json?address=".$from."&key=AIzaSyDsGVqmUU8__aMjhHLThN8Libiy9cGGXbg";
            $handle = curl_init(str_replace (" ", "+",$url));
            curl_setopt($handle, CURLOPT_RETURNTRANSFER, 1);
            $userAddress = curl_exec($handle);
            
            $json = $this->searchForCity($city, $what);
        }
        
        $categories=$this->getCategories();
        return $this->render('JyAccedeSearchBundle:Front:search.html.twig',
                             array("ville" => $city,
                                   "from" => $from,
                                   "json" => $json,
                                   "userAddress" => $userAddress,
                                   "categories" => $categories));
    }
}
