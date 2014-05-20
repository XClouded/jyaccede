<?php
spl_autoload_register(function ($className)
{
    $className = ltrim($className, '\\');

    $fileName = str_replace('\\', DIRECTORY_SEPARATOR, $className) . '.php';

    foreach(array('/src/', '/tests/') as $path)
    {
        if(is_file($file = __DIR__ . $path . $fileName))
        {
            require $file;
        }
    }
});
