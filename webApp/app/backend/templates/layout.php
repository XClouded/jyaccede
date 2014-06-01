<!DOCTYPE HTML>
<html>
    <head>
        <title>VisitMyCity, Administration !</title>

        <!-- BOOTSTRAP -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

        <!-- Custom BOOTSTRAP -->
        <link rel="stylesheet" href="web/css/layout.css">
    </head>
	
    <body>
        <div class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                  <a class="navbar-brand" href="#">Visit my city</a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li ><a href="/jyaccede/admin">Admin</a></li>
                        <li ><a href="/jyaccede/admin/articles">Articles</a></li>
                        <li ><a href="/jyaccede/admin/locations">Locations</a></li>
                        <li ><a href="/jyaccede">Home</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>

        <?= $templateEngine->renderBody($templateBody, $parameters); ?>

        <div id="footer">
            <div class="container">
                <p class="text-muted">
                    <a href="#">Contact us</a>
                </p>
            </div>
        </div>	
    </body>
</html>