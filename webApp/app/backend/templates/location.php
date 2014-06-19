<body>
    <form action="/_jyaccede/admin/articles/<?= $article->getId(); ?>" method="POST">
        <legend>Update location</legend>
        <table>
            <tr>
                <td><label for="name">Name :</label></td>
                <td><input id="name" name="name" type="text" class="form-control" value="<?= $article->getName() ?>"/></td>
            </tr>
            <tr>
                <td><label for="latitude" >Latitude :</label></td>
                <td><input id="latitude" name="latitude" type="text" class="form-control" value="<?= $article->getLatitude() ?>"/></td>
            </tr>
            <tr>
                <td><label for="longitude" >Longitude :</label></td>
                <td><input id="longitude" name="longitude" type="text" class="form-control" value="<?= $article->getLongitude() ?>"/></td>
            </tr>
            <tr>
                <td><label for="idCategory" >Category :</label></td>
                <td><select id="idCategory" name="idCategory" class="form-control" value="<?= $article->getIdCatgory() ?>">
                        <?php foreach($categories as $id => $cat){ ?>
                        <option value="<?= $cat->getId() ?>"><?= $cat->getName() ?></option>
                        <?php } ?>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="disableAccess" >Disable access ?</label></td>
                <td><input type="checkbox" value="1" name="disableAccess" class="form-control"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Update" class="form-control"/></td>
            </tr>
        </table>
    </form>

    <a href="/jyaccede/admin/articles">Back</a>
</body>