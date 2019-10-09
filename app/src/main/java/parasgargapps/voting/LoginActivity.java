package parasgargapps.voting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Button learnMore;
    EditText AadharNumber, password;
    Button registration;
    Button logIn;

    public static final String MY_PREFERENCE = "myPref";


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        learnMore = findViewById(R.id.learnmore);
        AadharNumber = findViewById(R.id.AadharNumber);
        password = findViewById(R.id.pass);
        registration = findViewById(R.id.registerHere);
        logIn = findViewById(R.id.login);

        learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent learnmore = new Intent(LoginActivity.this, LearnMore.class);
                startActivity(learnmore);
            }
        });



        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Aadhar = AadharNumber.getText().toString();
                String pass = password.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFERENCE, MODE_PRIVATE).edit();
                editor.putString("aadharNumber", Aadhar);
                editor.commit();


                if (!Aadhar.equalsIgnoreCase("")) {
                    if (!pass.equalsIgnoreCase("")){
                        Toast.makeText(LoginActivity.this, "Welcome " + Aadhar + " !!!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this, AadharDetailsActivity.class);
                        i.putExtra("AadharNmuber", Aadhar.toString());
                        startActivity(i);
                    } else {

                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Enter Aadhar Number", Toast.LENGTH_LONG).show();
                }
                if (!pass.equalsIgnoreCase("")){

                }
                else {
                    Toast.makeText(LoginActivity.this,"Enter Password", Toast.LENGTH_LONG).show();
                }

                //TODO-P: Remove lines assigning value aakashgarg & testpassword after complete integration.
              //  Aadhar = "aakashgarg"; //TODO: remove test line
              //  pass = "testpassword"; //TODO: remove Test line.
                final String LOGIN_URL_FORMAT = "http://192.168.43.44:8080/api/v1/user/login?username=%s";
                String loginUrl = String.format(LOGIN_URL_FORMAT, Aadhar);

                //Call API for login
                String response = HttpRequester.sendPOST(loginUrl, pass);
                Boolean isCorrectUser = Boolean.parseBoolean(response);
                //TODO-A: Check response string from Login API.
                if(isCorrectUser) {
                    UserLoginSession.userDetails.put("userName", Aadhar);
                    UserLoginSession.userDetails.put("password", pass);
                } else {
                    UserLoginSession.userDetails.clear();
                    //Ask for UerName & password again.
                   // Toast.makeText(LoginActivity.this,"Enter the correct ID:", Toast.LENGTH_LONG).show();// prompt the msg
                }
                //when pressed "logIn" button just send request to check if AadharNo and Password are correct

            }

            //TODO: Bad place for TODO:
//            Where will the code go after submit/lohin button is CLicked?


        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this,registerNewUser.class);
                startActivity(register);
            }
        });
    }
}