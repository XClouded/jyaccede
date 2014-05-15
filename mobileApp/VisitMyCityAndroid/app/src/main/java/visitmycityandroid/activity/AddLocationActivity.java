package visitmycityandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import visitmycityandroid.app.R;
import visitmycityandroid.asyncTask.PostLocationTask;
import visitmycityandroid.model.LocationModel;

public class AddLocationActivity extends VisitMyCityActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        //event handler
        Button sendMessageButton = (Button)findViewById(R.id.addLocationButton);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText inputName = (EditText) findViewById(R.id.inputName);
                EditText inputLatitude = (EditText) findViewById(R.id.inputLatitude);
                EditText inputLongitude = (EditText) findViewById(R.id.inputLongitude);
                EditText inputRemark = (EditText) findViewById(R.id.inputRemarque);
                LocationModel l = new LocationModel();
                l.setName(inputName.getText().toString());
                l.setLatitude(Double.parseDouble(inputLatitude.getText().toString()));
                l.setLongitude(Double.parseDouble(inputLongitude.getText().toString()));
                l.setRemark(inputRemark.getText().toString());

                PostLocationTask plt = new PostLocationTask(getApplicationContext());
                plt.execute(l);

                inputName.setText("");
                inputLatitude.setText("");
                inputLongitude.setText("");
                inputRemark.setText("");
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
