<head>
	<!-- BOOTSTRAP -->
	<link rel="stylesheet" href="http://getbootstrap.com/examples/jumbotron/jumbotron.css">
</head>

<div class="jumbotron">
	<div class="container">
		<h1>Hello, world!</h1>
		<p>This is a template for a simple marketing or informational website. It includes a large callout called a jumbotron and three supporting pieces of content. Use it as a starting point to create something more unique.</p>
		<p><a class="btn btn-primary btn-lg" role="button">Learn more &raquo;</a></p>
	</div>
</div>

<div class="container">
	<div class="row">
		<?php foreach($articles as $id => $article){ ?>
			<div class="col-md-4">
				<h2><?= $article->getName() ?></h2>
				<p><?= $article->getDescription() ?></p>
			</div>
		<?php } ?>
	</div>
</div>
