<?php

/*
 * This file is part of the Symfony package.
 *
 * (c) Fabien Potencier <fabien@symfony.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

namespace Stikair\Bundle\InitBundle\Composer;

use Symfony\Component\ClassLoader\ClassCollectionLoader;
use Symfony\Component\Process\Process;
use Symfony\Component\Process\PhpExecutableFinder;
use Composer\Script\CommandEvent;

class ScriptHandler {

    public static function extendUserBundle(CommandEvent $event) {

        $options = self::getOptions($event);
        $appDir = $options['symfony-app-dir'];

        if (!is_dir($appDir)) {
            echo 'The symfony-app-dir (' . $appDir . ') specified in composer.json was not found in ' . getcwd() . ', can not clear the cache.' . PHP_EOL;

            return;
        }

        static::executeCommand($event, $appDir, 'sonata:easy-extends:generate SonataUserBundle -d src', $options['process-timeout']);
    }

    public static function createDb(CommandEvent $event) {

        $options = self::getOptions($event);
        $appDir = $options['symfony-app-dir'];

        if (!is_dir($appDir)) {
            echo 'The symfony-app-dir (' . $appDir . ') specified in composer.json was not found in ' . getcwd() . ', can not clear the cache.' . PHP_EOL;

            return;
        }

        static::executeCommand($event, $appDir, 'doctrine:database:create', $options['process-timeout']);
    }

    public static function UpdateDb(CommandEvent $event) {

        $options = self::getOptions($event);
        $appDir = $options['symfony-app-dir'];

        if (!is_dir($appDir)) {
            echo 'The symfony-app-dir (' . $appDir . ') specified in composer.json was not found in ' . getcwd() . ', can not clear the cache.' . PHP_EOL;

            return;
        }

        $event->getIO()->write("--- Ajout des tables dans la BDD ---");
        static::executeCommand($event, $appDir, "doctrine:schema:update --dump-sql", $options['process-timeout']);
        static::executeCommand($event, $appDir, "doctrine:schema:update --force", $options['process-timeout']);
        $event->getIO()->write("--- Done ---");
    }

    /**
     * Clears the Symfony cache.
     *
     * @param $event CommandEvent A instance
     */
    public static function initAcls(CommandEvent $event) {
        $options = self::getOptions($event);
        $appDir = $options['symfony-app-dir'];

        if (!is_dir($appDir)) {
            echo 'The symfony-app-dir (' . $appDir . ') specified in composer.json was not found in ' . getcwd() . ', can not clear the cache.' . PHP_EOL;

            return;
        }

        static::executeCommand($event, $appDir, 'init:acl', $options['process-timeout']);
    }

    public static function createSuperAdminUser(CommandEvent $event) {
        $cmd = "fos:user:create --super-admin";

        $params = array("username" => "adminovs", "email" => "devteam@overscan.fr", "password" => "adminovs");

        $io = $event->getIO();

        $io->write("--- Création du super-admin ---");
        foreach ($params as $key => $default) {
            $value = $io->ask(sprintf('<question>%s</question> (<comment>%s</comment>): ', $key, $default), $default);
            $paramsGiven[$key] = $value;
        }

        $options = self::getOptions($event);
        $appDir = $options['symfony-app-dir'];

        if (!is_dir($appDir)) {
            echo 'The symfony-app-dir (' . $appDir . ') specified in composer.json was not found in ' . getcwd() . ', can not clear the cache.' . PHP_EOL;

            return;
        }

        $cmd.=" " . $paramsGiven['username'] . " " . $paramsGiven['email'] . " " . $paramsGiven['password'];

        static::executeCommand($event, $appDir, $cmd, $options['process-timeout']);
        static::executeCommand($event, $appDir, "sonata:admin:setup-acl", $options['process-timeout']);
        $io->write("--- Done ---");
    }

    protected static function executeCommand(CommandEvent $event, $appDir, $cmd, $timeout = 300) {
        $php = escapeshellarg(self::getPhp());
        $console = escapeshellarg($appDir . '/console');
        if ($event->getIO()->isDecorated()) {
            $console .= ' --ansi';
        }

        $process = new Process($php . ' ' . $console . ' ' . $cmd, null, null, null, $timeout);
        $process->run(function ($type, $buffer) {
            echo $buffer;
        });
        if (!$process->isSuccessful()) {
            throw new \RuntimeException(sprintf('An error occurred when executing the "%s" command.', escapeshellarg($cmd)));
        }
    }

    protected static function getOptions(CommandEvent $event) {
        $options = array_merge(array(
            'symfony-app-dir' => 'app',
            'symfony-web-dir' => 'web',
            'symfony-assets-install' => 'hard'
                ), $event->getComposer()->getPackage()->getExtra());

        $options['symfony-assets-install'] = getenv('SYMFONY_ASSETS_INSTALL') ? : $options['symfony-assets-install'];

        $options['process-timeout'] = $event->getComposer()->getConfig()->get('process-timeout');

        return $options;
    }

    protected static function getPhp() {
        $phpFinder = new PhpExecutableFinder;
        if (!$phpPath = $phpFinder->find()) {
            throw new \RuntimeException('The php executable could not be found, add it to your PATH environment variable and try again');
        }

        return $phpPath;
    }

}
