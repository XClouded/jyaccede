<body>
    <ul>
        <?php foreach($articles as $id => $article){ ?>
            <li> 
                <form action="/_jyaccede/admin/articles/<?= $article->getId(); ?>" method="POST">
                    <input type="hidden" name="_method" value="DELETE" />
                    <a href="/jyaccede/admin/articles/<?= $article->getId() ?>"><?= $article->getName() ?></a>
                    <input type="submit" value="Delete" >
                </form>
            </li><br/>
        <?php } ?>
    </ul>
    
    <form action="/_jyaccede/admin/articles" method="POST">
        <legend>Add article</legend>
        <table>
            <tr><td><label for="name" >Title :</label></td><td><input id="name" name="name" type="text" class="form-control" /></td></tr>
            <tr><td><label for="description" >Content :</label></td><td><textarea cols="30" rows="5" id="description" name="description" class="form-control" ></textarea></td></tr>
            <tr><td colspan="2"><input type="submit" value="Add" class="form-control" /></td></tr>
        </table>
    </form>

</body>