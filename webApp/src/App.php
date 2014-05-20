<?php

use Exception\ExceptionHandler;
use Exception\HttpException;
use Routing\Route;
use View\TemplateEngineInterface;
use Http\Request;
use Http\Response;

class App
{
    use \EventDispatcherTrait;
	
    const GET    = 'GET';
    const POST   = 'POST';
    const PUT    = 'PUT';
    const DELETE = 'DELETE';

    /**
     * @var array
     */
    private $routes = array();

    /**
     * @var TemplateEngineInterface
     */
    private $templateEngine;

    /** Define if debug mode is activated
     * @var boolean
     */
    private $debug;

    /**
     * @var statusCode
     */
    private $statusCode;
	
	/**
	*
	*/
	private $urlRoot;

	/** Contruct
	*
	* @param TemplateEngineInterface $templateEngine
	* @param boolean $debug
	*
	*/
    public function __construct(TemplateEngineInterface $templateEngine, $urlRoot = '', $debug = false){
        $this->templateEngine = $templateEngine;
        $this->debug          = $debug;
		$this->urlRoot        = $urlRoot;

        $exceptionHandler = new ExceptionHandler($templateEngine, $this->debug);
        set_exception_handler(array($exceptionHandler, 'handle'));
    }

    /** Render a view for a given template
     * @param string $template
     * @param array  $parameters
     * @param int    $statusCode
     *
     * @return string
     */
    public function render($template, array $parameters = array(), $layout = null, $statusCode = 200){
        $this->statusCode = $statusCode;

        return $this->templateEngine->render($template, $parameters, $layout);
    }

    /** Register a route for GET HTTP verb
     * @param string   $pattern
     * @param callable $callable
     *
     * @return App
     */
    public function get($pattern, $callable){
        $this->registerRoute(Request::GET, $pattern, $callable);

        return $this;
    }
	
	/** Register a route for POST HTTP verb
     * @param string   $pattern
     * @param callable $callable
     *
     * @return App
     */
    public function post($pattern, $callable){
        $this->registerRoute(Request::POST, $pattern, $callable);

        return $this;
    }
	
	/** Register a route for PUT HTTP verb
     * @param string   $pattern
     * @param callable $callable
     *
     * @return App
     */
    public function put($pattern, $callable){
        $this->registerRoute(Request::PUT, $pattern, $callable);

        return $this;
    }
	
	/** Register a route for DELETE HTTP verb
     * @param string   $pattern
     * @param callable $callable
     *
     * @return App
     */
    public function delete($pattern, $callable){
        $this->registerRoute(Request::DELETE, $pattern, $callable);

        return $this;
    }

	/** Launch the applicaton
	*
	* @param Request $request The HTTP request
	*
	*/
    public function run(Request $request = NULL){
        if (NULL === $request){
			$request = Request::createFromGlobals();
        }

        $method = $request->getMethod();
        $uri    = $request->getUri();

        foreach ($this->routes as $route){
			if ($route->match($method, $uri)){
				return $this->process($request, $route);
			}
        }

        throw new HttpException(404, 'Page Not Found !');
    }

    /** Execute a route for a specified request
     * @param Route $route
     */
    private function process(Request $request, Route $route) {
        $this->dispatch('process.before', [ $request ]);
		
        try{
            http_response_code($this->statusCode);

            $arguments = $route->getArguments();
            array_unshift($arguments, $request);

            $response = call_user_func_array($route->getCallable(), $arguments);
            if (!$response instanceof Response){
				$response = new Response($response);
            }

            $response->send();
        }
        catch (HttpException $e){
            throw $e;
        }
        catch (\Exception $e){
            throw new HttpException(500, null, $e);
        }
    }

    /** Redirect application to a given URL
    *@param string $to
    */
    public function redirect($to, $statusCode = 302){
        http_response_code($statusCode);
        header(sprintf('Location: %s%s', $this->urlRoot, $to));

        die;
    }

    /** Add route to the application
     * @param string   $method
     * @param string   $pattern
     * @param callable $callable
     */
    private function registerRoute($method, $pattern, $callable){
        $this->routes[] = new Route($method, $this->urlRoot.''.$pattern, $callable);
    }
}