<?php
/**
 * Created by PhpStorm.
 * User: David
 * Date: 15/06/14
 * Time: 22:49
 */

namespace JyAccede\Bundle\APIBundle\Tools;

    /**
     * REST Client
     *
     * Allows to submit REST requests
     *
     * @author Sylvain V.
     * @link http://chez-syl.fr/
     * @license MIT
     */
    class RESTClient {

        /**
         * Properties
         */
        private $api_url;

        /**
         * Constructor
         */
        public function __construct() {
            /**
             * API server
             */
            $this->api_url = "http://api.navitia.io/v1/";
        }

        /**
         * GET request
         *
         * @param string $uri The needed resource
         *
         * @return json
         */
        public function get($uri) {
            return $this->exec('get', $uri);
        }

        /**
         * POST request
         *
         * @param string $uri The needed resource
         * @param array $data An associative array
         *
         * @return json
         */
        public function post($uri, $data) {
            return $this->exec('post', $uri, $data);
        }

        /**
         * PUT request
         *
         * @param string $uri The needed resource
         * @param array $data An associative array
         *
         * @return json
         */
        public function put($uri, $data) {
            return $this->exec('put', $uri, $data);
        }

        /**
         * DELETE request
         *
         * @param string $uri The needed resource
         *
         * @return json
         */
        public function delete($uri) {
            return $this->exec('delete', $uri);
        }

        /**
         * Execute request
         *
         * @param string $method Only "get", "post", "put" or "delete" are supported
         * @param string $uri The needed resource
         * @param array $data An associative array
         *
         * @return json
         */
        private function exec($method, $uri, $data = null) {
            echo strtoupper($method)." sur ".$uri;
            /**
             * Error variables
             */
            $error = false;
            $error_msg = '';

            /**
             * Init cURL
             */
            $handle = curl_init($this->api_url . $uri);

            /**
             * Additional headers
             */
            $headers = [];

            /**
             * Special treatment
             */
            switch($method) {
                case 'post':
                    /**
                     * Adding data
                     */
                    if($data) {
                        /**
                         * POST option
                         */
                        curl_setopt($handle, CURLOPT_POSTFIELDS, $data);
                    } else {
                        $error = true;
                        $error_msg = 'No data provided for that POST request';
                    }
                    break;
                case 'put':
                    /**
                     * Adding data
                     */
                    if($data) {
                        /**
                         * Converting array to an URL-encoded query string
                         */
                        $data = http_build_query($data, '', '&');

                        /**
                         * Opening PHP memory
                         */
                        $memory = fopen('php://memory', 'rw');
                        fwrite($memory, $data);
                        rewind($memory);

                        /**
                         * Simulating file upload
                         */
                        curl_setopt($handle, CURLOPT_INFILE, $memory);
                        curl_setopt($handle, CURLOPT_INFILESIZE, strlen($data));
                        curl_setopt($handle, CURLOPT_PUT, true);
                    } else {
                        $error = true;
                        $error_msg = 'No data provided for that PUT request';
                    }
                    break;
            }

            /**
             * Basic options
             */
            curl_setopt($handle, CURLOPT_HTTPHEADER, $headers);
            curl_setopt($handle, CURLOPT_RETURNTRANSFER, true);

            /**
             * Adding the method name to cURL
             */
            if($method !== 'get') {
                curl_setopt($handle, CURLOPT_CUSTOMREQUEST, strtoupper($method));
            }

            if($error) {
                /**
                 * Error
                 */
                $json = json_encode([
                    'api' => [
                        'status'    => 400,
                        'error'     => $error_msg
                    ]
                ]);
            } else {
                /**
                 * Result
                 */
                $json = curl_exec($handle);
            }


            if(curl_getinfo($handle,CURLINFO_HTTP_CODE )!=200){
                return null;
            }

            /**
             * Closing handle
             */
            curl_close($handle);

            return $json;
        }

    }

?>