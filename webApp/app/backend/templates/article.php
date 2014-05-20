<!DOCTYPE HTML>
<html>
	<head>
		<title>An article</title>
	</head>
	
	<body>
		<form action="/admin/articles/<?= $article->getId(); ?>" method="POST">
			<input type="hidden" value="PUT" name="_method" />
			<input type="text" value="<?= $article->getName(); ?>" name="name" /><br /><br />
			<textarea name="description"><?= $article->getDescription(); ?></textarea><br /><br />
			<input type="submit" value="Update">
		</form>
		
		<a href="/admin/articles">Back</a>
	</body>
</html>
