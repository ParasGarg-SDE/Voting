package parasgargapps.voting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class ConfirmationActivity extends AppCompatActivity {
    TextView tv1,display,tv2,tv3;
    Button no,vote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        tv1=findViewById(R.id.textView5);
        tv2=findViewById(R.id.textView7);
        tv3=findViewById(R.id.textView8);
        display=findViewById(R.id.textView6);

        final String DisplaySelectedCandidate = getIntent().getExtras().getString("selectedCandidate");
        display.setText(DisplaySelectedCandidate);

        no=findViewById(R.id.button2);
        vote=findViewById(R.id.button3);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k=new Intent(ConfirmationActivity.this,ChooseCandidateActivity.class);
                startActivity(k);
            }
        });

        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l= new Intent(ConfirmationActivity.this,ThanksActivity.class);
                startActivity(l);

                String Aadhar = UserLoginSession.userDetails.get("userName");
                String pass = UserLoginSession.userDetails.get("password");

                String party = "BJP"; //TODO-P: Get te Party Name here. Should be from obe of Party Enum in Backend Code.//cant get this
                String selectedCandidate = DisplaySelectedCandidate; //TODO : got the candidate name

                String VOTE_API_URL_FORMAT = "http://192.168.43.44:8080/api/v1/user/vote?username=%s&party=%s";
                String voteApi = String.format(VOTE_API_URL_FORMAT, Aadhar, party);

                //Call API to submit the vote
                final String voteResponse = HttpRequester.sendPOST(voteApi, pass);

                //TODO-P: Check the Vote response String and display it. Maybe the user has already voted.
                Log.d("paras", "response from vote API" + voteResponse);
                try {
                    JSONObject jsonObject = new JSONObject(voteResponse);
                }catch (Exception e){
                    e.printStackTrace();
                }

                //when pressed "vote" button the vote is counted to the API
                //count the vote with respect to party name or candidate name
            }
        });
    }
}
