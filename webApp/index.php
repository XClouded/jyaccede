<?php

require __DIR__ . '/autoload.php';

//Use
use Http\Request;

$request = Request::createFromGlobals();

if(!stristr($request->getUri(), '/admin')){
	$app = require __DIR__ . '/app/frontend/app.php';
}
else{
	$app = require __DIR__ . '/app/backend/app.php';
}

$app->run();
