package parasgargapps.voting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AadharDetailsActivity extends AppCompatActivity {
    TextView AadharDetails,name, District;
    CheckBox good;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_details);

        AadharDetails=findViewById(R.id.textView1);
        name=findViewById(R.id.textView2);
//        District=findViewById(R.id.textView3);

        String Name = getIntent().getExtras().getString("AadharNmuber","");
        name.setText(Name);



        good=findViewById(R.id.checkBox);

        good.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Intent next=new Intent(AadharDetailsActivity.this, ChooseCandidateActivity.class);
                next.putExtra("Holders",toString());
                startActivity(next);
            }
        });

    }
}
