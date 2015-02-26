package lelexxx.com.jyaccede.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import lelexxx.com.jyaccede.activity.places.CloserPlaceActivity;

import lelexxx.com.jyaccede.R;
import lelexxx.com.jyaccede.database.DataAccessLayer;
import lelexxx.com.jyaccede.model.CategoryModel;

public abstract class JyaccedeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
             case android.R.id.home:
                 finish();
                 break;
             case R.id.action_closerLocation :
                Intent intentCloser = new Intent(this, CloserPlaceActivity.class);
                startActivity(intentCloser);
                 break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    protected DataAccessLayer getDal(){
        List<Class> classes = new ArrayList<>();
        classes.add(CategoryModel.class);
        DataAccessLayer dal = DataAccessLayer.getInstance(this, classes);

        return dal;
    }
}
