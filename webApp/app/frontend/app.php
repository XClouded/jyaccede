<?php

//Use
use Http\Request;
use Mapper\ArticleDataMapper;
use Exception\HttpException;
use DAL\DataBase;

// Config
$debug = true;
$urlRoot = '/_jyaccede'; //Define root of THIS website
$app = new \App(new \View\TemplateEngine(__DIR__ . '/templates/'), $urlRoot, $debug);
$database = new DataBase(Conf::_DB_DSN_, Conf::_DB_USER_, Conf::_DB_PWD_, $options);

//Define routes which don't require authentification
$app->addListener('process.before', function(Request $req) use ($app, $urlRoot) {
    session_start();

    $allowed = [
        $urlRoot.'/' => [ Request::GET ],
        $urlRoot.'/login' => [ Request::GET ],
        $urlRoot.'/accessAdmin' => [ Request::GET ],
    ];

    if (isset($_SESSION['is_authenticated']) && true === $_SESSION['is_authenticated']){
        return;
    }

    foreach ($allowed as $pattern => $methods) {
        if (preg_match(sprintf('#^%s/?$#', $pattern), $req->getUri()) && in_array($req->getMethod(), $methods)) {
            return;
        }
    }

    switch ($req->guessBestFormat()) {
        case 'json':
            throw new HttpException(401);
    }
    
    throw new HttpException(401, 'Access denied !');
});

//Index
$app->get('/', function () use ($app, $database){
    $mapper = new ArticleDataMapper($database);
    $art = $mapper->findAll();

    return $app->render('index.php', array("articles" => $art), 'layout.php');
});
	
//Access to the login form
$app->get('/login', function() use ($app){
    return $app->render('login.php', array(), 'layout.php');
});
	
//Access to the login form
$app->get('/accessAdmin', function(Request $request) use ($app){
    $login = $request->getParameter('login', null);
    $password = $request->getParameter('password', null);

    if(null === $login){
        throw new HttpException(400, "Login is mandatory !");
    }

    if(null === $password){
        throw new HttpException(400, "Password is mandatory !");
    }

    if($login !== $password){
        $app->redirect('/login');
    }

    session_start();
    $_SESSION['is_authenticated'] = true;

    $app->redirect('/admin');
});
	
return $app;
