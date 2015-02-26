package lelexxx.com.jyaccede.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;

import java.util.List;

import lelexxx.com.jyaccede.R;
import lelexxx.com.jyaccede.activity.places.CloserPlaceActivity;
import lelexxx.com.jyaccede.asyncTask.JaccedeGetCategoryTask;
import lelexxx.com.jyaccede.configuration.Variables;
import lelexxx.com.jyaccede.database.DataAccessLayer;
import lelexxx.com.jyaccede.interfaces.CategorieListener;
import lelexxx.com.jyaccede.model.CategoryModel;

public class MainActivity extends JyaccedeActivity implements CategorieListener  {

    private ActionBarDrawerToggle mDrawerToggle;

    SharedPreferences prefs = null;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    //region ACTIVITY LIFE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);

        //Connectivity init and settings
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        boolean isConnected = (network != null && network.isConnectedOrConnecting());
        if(!isConnected) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(R.string.error);
            alertDialog.setMessage(R.string.noConnectionError);
            alertDialog.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    finish();
                }
            });
            alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.show();
        }

        initDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("first_run", true)) {
            prefs.edit().putBoolean("first_run", false).commit();
            JaccedeGetCategoryTask jc = new JaccedeGetCategoryTask(this, Variables.SearchCategorieUrl);
            jc.execute();
        }
    }

    //endregion

    //region MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
            case R.id.action_closerLocation :
                Intent intentCloser = new Intent(this, CloserPlaceActivity.class);
                startActivity(intentCloser);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //endregion

    //region DRAWER

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if(mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(R.string.app_name);
    }

    /**
     * Init drawer component
     */
    private void initDrawer(){
        String[] itemName = new String[]{
                //getString(R.string.searchLocation),
                getString(R.string.closerLocation),
                //getString(R.string.addLocation)
        };
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the googleMaps for the list view
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_listeview_item, itemName));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.open,  /* "open drawer" description */
                R.string.close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(getString(R.string.app_name));
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(R.string.app_name);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        switch (position){
            case 0 :
                Intent intentCloser = new Intent(this, CloserPlaceActivity.class);
                startActivity(intentCloser);
                break;
        }
    }

    //endregion

    @Override
    public void OnCompleted(List<CategoryModel> categories) {
        DataAccessLayer dal = getDal();
        for(CategoryModel category : categories){
            dal.insertData(category);
        }
    }
}
