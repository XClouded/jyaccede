<body>
    <ul>
        <?php foreach($articles as $id => $article){ ?>
            <li>
                <a href="/admin/articles/<?= $article->getId() ?>"><?= $article->getName() ?></a>
                <form action="/admin/articles/<?= $article->getId(); ?>" method="POST">
                    <input type="hidden" name="_method" value="DELETE" />
                    <input type="submit" value="Delete">
                </form>
            </li><br/>
        <?php } ?>
    </ul>

    <form action="/admin/articles" method="POST">
        <input type="text" name="name" /><br /><br />
        <textarea name="description"></textarea><br /><br />
        <input type="submit" value="Update">
    </form>
</body>