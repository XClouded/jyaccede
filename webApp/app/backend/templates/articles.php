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
    
    <form method="/jyaccede/admin/articles" method="post">
        <legend style="width:500px;">Entrez une nouvelle actualitée</legend>
        <table>
            <tr><td><label for="title" >Titre: </label></td><td><input id="title" name="title" type="text" class="form-control" /></td></tr>
            <tr><td><label for="content" >Contenu: </label></td><td><textarea cols="30" rows="5" id="name" name="name" class="form-control" ></textarea></td></tr>
        </table>
        <input type="submit" id="send" name="send" value="Envoyez" class="form-control" />
    </form>

    
</body>