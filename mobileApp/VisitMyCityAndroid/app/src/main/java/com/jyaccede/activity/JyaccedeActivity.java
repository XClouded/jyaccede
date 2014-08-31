package com.jyaccede.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.jyaccede.activity.places.CloserPlaceActivity;

import jyaccede.app.R;

public abstract class JyaccedeActivity extends Activity{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
             case R.id.action_closerLocation :
                Intent intentCloser = new Intent(this, CloserPlaceActivity.class);
                startActivity(intentCloser);
                 break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}
