<body>
    <form action="/_jyaccede/admin/articles/<?= $article->getId(); ?>" method="POST">
        <legend>Update article</legend>
        <table>
            <tr>
                <td><label for="name" >Title :</label></td>
                <td><input id="name" name="name" type="text" class="form-control" value="<?= $article->getName(); ?>"/></td>
            </tr>
            <tr>
                <td><label for="description" >Content :</label></td>
                <td><textarea cols="30" rows="5" id="description" name="description" class="form-control" ><?= $article->getDescription(); ?></textarea></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Update" class="form-control" /></td>
            </tr>
        </table>
    </form>

    <a href="/_jyaccede/admin/articles">Back</a>
</body>