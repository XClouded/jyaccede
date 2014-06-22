<body>
    <ul>
        <?php foreach($locations as $id => $location){ ?>
            <li>
                <form action="/_jyaccede/admin/locations/<?= $location->getId(); ?>" method="POST">
                    <input type="hidden" name="_method" value="DELETE" />
                    <a href="/jyaccede/admin/locations/<?= $location->getId() ?>"><?= $location->getName() ?></a>
                    <input type="submit" value="Delete">
                </form>
            </li><br/>
        <?php } ?>
    </ul>
    
    <form action="/_jyaccede/admin/locations" method="post">
        <legend>Add location</legend>
        <table>
            <tr><td><label for="name">Name :</label></td><td><input id="name" name="name" type="text" class="form-control" /></td></tr>
            <tr><td><label for="latitude" >Latitude :</label></td><td><input id="latitude" name="latitude" type="text" class="form-control" /></td></tr>
            <tr><td><label for="longitude" >Longitude :</label></td><td><input id="longitude" name="longitude" type="text" class="form-control" /></td></tr>
            <tr>
                <td><label for="idCategory" >Category :</label></td>
                <td><select id="idCategory" name="idCategory" class="form-control" >
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