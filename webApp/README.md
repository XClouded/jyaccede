[![Build Status](https://travis-ci.org/moustikAir/Sf2Starter.svg?branch=master)](https://travis-ci.org/moustikAir/Sf2Starter)

#Sf2 Starter Kit

Welcome to my Sf2 Starter Kit - a fully-functional Symfony2
application that you can use as the skeleton for your new applications.

This package is based on a Symfony2 basic project cleared of the default AcmeBundle.

It comes with SonataAdminBundle pre-configured and SonataUserBundle on top of FosUserBundle for user management.

Basically, install this stuff and you get a Sf2 project with an admin interface and user management out of the box.

## 1) Installation

Simply clone this repo or download the archive from github.
Once you get it on your machine, unpack it.

Then simply run composer install in your install directory.

```shell
$ composer install
```

This should run the basic install asking for the databases access and global symfony configuration.

Then it launches the Admin Configuration :

* Database creation
* Acl Initialization
* Database Tables creation
* SuperAdmin configuration

## 2) Usage

Once all the installation process is done, you can access your project admin interface via http://localhost/path/to/starterkit/web/app.php/admin/dashboard

You'll be asked for the admin access. 

And you're done.
