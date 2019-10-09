package parasgargapps.voting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class registerNewUser extends AppCompatActivity {

    Button submit;
    Spinner myspinner;
    EditText AadharNumber, Password, City;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);

        AadharNumber = findViewById(R.id.aadharNumber);
        Password = findViewById(R.id.password);
        City = findViewById(R.id.city);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(registerNewUser.this,LoginActivity.class);
                startActivity(back);

                String Aadhar = AadharNumber.getText().toString();
                String password = Password.getText().toString();
                String city = City.getText().toString();
                //String Aadhar = "aakashgarg"; //TODO-P: Get the Aadhar no from User. //already got
                //String password = "testpassword"; //TODO-P: Get the the password from user. //already got
                //String city = "Delhi"; //TODO: Get City if required.

                //Call API for registering new Voter
                //when pressed "submit" button the voter data is sent to the the API for registeration of new Voter
                final String REGISTER_API_URL = "http://192.168.43.44:8080/api/v1/user/register?username=%s&city=%s";
                String registerUrl = String.format(REGISTER_API_URL, Aadhar, city);
                String useRegResponse = HttpRequester.sendPOST(registerUrl, password);

                System.out.println(useRegResponse);
                //TODO-P [Optional]: Show userRegResponse message to user and move back user to Login Page.

            }
        });

        myspinner = findViewById(R.id.state);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(registerNewUser.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.state));
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        myspinner.setAdapter(myAdapter);
    }
}