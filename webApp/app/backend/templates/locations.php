<body>
    <ul>
        <?php foreach($locations as $id => $location){ ?>
            <li>
                <a href="/admin/locations/<?= $location->getId() ?>"><?= $location->getName() ?></a>
                <form action="/admin/locations/<?= $location->getId(); ?>" method="POST">
                    <input type="hidden" name="_method" value="DELETE" />
                    <input type="submit" value="Delete">
                </form>
            </li><br/>
        <?php } ?>
    </ul>
    
    <form method="/jyaccede/admin/locations" method="post">
        <legend>Add location</legend>
        <table>
            <tr><td><label for="name">Name :</label></td><td><input id="name" name="name" type="text" class="form-control" /></td></tr>
            <tr><td><label for="latitude" >Latitude :</label></td><td><input id="latitude" name="latitude" type="text" class="form-control" /></td></tr>
            <tr><td><label for="longitude" >Longitude :</label></td><td><input id="longitude" name="longitude" type="text" class="form-control" /></td></tr>
            <tr>
                <td><label for="name_categorie" >Category :</label></td>
                <td><select id="name_categorie" name="name_categorie" class="form-control" >
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
                <td colspan="2"><input type="submit" value="Add" class="form-control"/></td>
            </tr>
        </table>
    </form>
</body>