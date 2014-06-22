<?php

namespace JyAccede\Bundle\SearchBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class FrontController extends Controller
{

    protected function searchForCity($city){
        $time=time();
        $url="http://dev.jaccede.com/api/v2/places/search/?where=".$city;
        $toSign = "GET\nx-jispapi-timestamp:".$time."\n/api/v2/places/search/";
        $hash=hash_hmac("sha1",$toSign,"test-jispapi-secret-access-key");
        $signature=base64_encode($hash);

        $authorization="JISAPI test-jispapi-access-key-id:".$signature;

        /**
         * Init cURL
         */
        $handle = curl_init($url);

        /**
         * Additional headers
         */
        $headers =array("Authorization:".$authorization,"x-jispapi-timestamp:".$time);

        curl_setopt($handle, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($handle, CURLOPT_RETURNTRANSFER, true);

        $json = curl_exec($handle);

        curl_close($handle);
        return $json;
    }

    public function searchAction(Request $request)
    {
        $city=false;
        $json="{}";
        if($request->isMethod("POST"))
        {
            $city=$request->request->get("city");
            $json=$this->searchForCity($city);
        }
        return $this->render('JyAccedeSearchBundle:Front:search.html.twig',array("ville"=>$city,"json"=>$json));
    }
}
