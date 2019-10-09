package parasgargapps.voting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChooseCandidateActivity extends AppCompatActivity implements CandidateAdapter.ItemClickListener {
    TextView tv;
    private List<Candidate> candidateList =new ArrayList<>();
    private RecyclerView recyclerView;
    private CandidateAdapter candidateAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_candidate);

        tv=findViewById(R.id.textView4);

        recyclerView=findViewById(R.id.recycler_view);

        candidateAdapter = new CandidateAdapter(candidateList);
        RecyclerView.LayoutManager hLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(hLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(candidateAdapter);
        candidateAdapter.setmClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpRequester.sendGET("https://api.myjson.com/bins/spshe");
                Log.d("Paras", "response from api : " + response);
                candidateList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray parties = jsonObject.getJSONArray("parties");
                    for (int i = 0; i < parties.length(); i++) {
                        JSONObject partyJsonObject = parties.getJSONObject(i);
                        Log.d("Paras", "party info : " + partyJsonObject.getString("name"));

                        String name  = partyJsonObject.getString("name");
                        String partyName  = partyJsonObject.getString("partyName");

                        Candidate holder =new Candidate(name,partyName);
                        candidateList.add(holder);

                    }

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            candidateAdapter.notifyDataSetChanged();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onItemClick(View view, Candidate candidate) {
        Toast.makeText(ChooseCandidateActivity.this, "Vote : " + candidate.getPartyName(), Toast.LENGTH_LONG).show();

        String selectedCandidate = candidate.getName();

        Intent j = new Intent(ChooseCandidateActivity.this, ConfirmationActivity.class);
        j.putExtra("selectedCandidate", selectedCandidate);
        startActivity(j);
    }

}
