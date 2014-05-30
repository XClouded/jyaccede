<body>
    <form method="/jyaccede/admin/locations" method="post">
        <legend>Entrez un lieu</legend>
        <table>
            <tr><td><label for="name">Nom du lieu: </label></td><td><input id="name" name="name" type="text" class="form-control" /></td></tr>
            <tr><td><label for="description" >Br&egrave;ve description du lieu: </label></td><td><textarea cols="30" rows="5" id="name" name="name" class="form-control" ></textarea></td></tr>
            <tr><td><label for="latitude" >Latitude: </label></td><td><input id="latitude" name="latitude" type="text" class="form-control" /></td></tr>
            <tr><td><label for="longitude" >Longitude: </label></td><td><input id="longitude" name="longitude" type="text" class="form-control" /></td></tr>
            <tr>
                <td><label for="name_categorie" >Nom de la cat&eacute;gorie: </label></td>
                <td><select id="name_categorie" name="name_categorie" class="form-control" >
                        <?php foreach($categories as $id => $cat){ ?>
                        <option value="<?= $cat->getId() ?>"><?= $cat->getName() ?></option>
                        <?php } ?>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Add" class="form-control"/></td>
            </tr>
        </table>
    </form>
</body>