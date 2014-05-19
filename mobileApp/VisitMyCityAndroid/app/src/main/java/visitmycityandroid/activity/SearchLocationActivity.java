package visitmycityandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import visitmycityandroid.app.R;

public class SearchLocationActivity extends VisitMyCityActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);

        //event handler
        Button sendMessageButton = (Button)findViewById(R.id.searchButton);
        sendMessageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText inputAddress = (EditText) findViewById(R.id.inputAddress);
        EditText inputLatitude = (EditText) findViewById(R.id.inputLatitude);
        EditText inputLongitude = (EditText) findViewById(R.id.inputLongitude);

        Intent intentMaps = new Intent(this, MapsActivity.class);
        intentMaps.putExtra("address", inputAddress.getText().toString());
        intentMaps.putExtra("latitude", inputLatitude.getText().toString());
        intentMaps.putExtra("longitude", inputLongitude.getText().toString());
        startActivity(intentMaps);

        inputAddress.setText("");
        inputLatitude.setText("");
        inputLongitude.setText("");
    }
}
