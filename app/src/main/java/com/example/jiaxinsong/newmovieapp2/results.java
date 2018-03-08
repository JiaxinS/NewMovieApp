package com.example.jiaxinsong.newmovieapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class results extends AppCompatActivity {
    ArrayList<String> dataTitle = new ArrayList<>();
    ArrayList<Double> dataVoteCount = new ArrayList<>();
    ArrayList<Double> dataVoteAverage = new ArrayList<>();
    ArrayList<String> dataLanguage = new ArrayList<>();
    ArrayList<Double> dataPopularty = new ArrayList<>();
    ArrayList <String> dataOverview= new ArrayList<>();
    ArrayList<String> dataDate = new ArrayList<>();
    ArrayList<String> dataPoster_Path = new ArrayList<>();

    public int receiveSearch;

    TextView Title = null;
    TextView Popularity = null;
    TextView Overview = null;
    TextView Votecount = null;
    TextView Language = null;
    TextView Date = null;
    ImageView Poster = null;

    public String urlbase = "https://image.tmdb/org/t/p/w500/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Title = findViewById(R.id.resulttitle);
        Popularity = findViewById(R.id.resultpopular);
        Language = findViewById(R.id.resultlanguage);
        Overview =  findViewById(R.id.resultoverview);
        Date =  findViewById(R.id.reusltdate);
        Poster =  findViewById(R.id.resultimage);
        Intent receivedpackage = getIntent();
        receiveSearch = receivedpackage.getIntExtra("firstsearch", -1);

    }

    public void CallThis(String jsonText){

            try {
                JSONObject json = new JSONObject(jsonText);
                JSONArray dateArray = json.getJSONArray("results");

                for(int i=0; i<dateArray.length(); i++) {
                    JSONObject jsonObject = dateArray.getJSONObject(i);

                    dataTitle.add(jsonObject.getString("title").toLowerCase());
                    dataPopularty.add(jsonObject.getDouble("popularity"));
                    dataLanguage.add(jsonObject.getString("language"));
                    dataVoteCount.add(jsonObject.getDouble("vote_count"));
                    dataOverview.add(jsonObject.getString("overview"));
                    dataDate.add(jsonObject.getString("release_date"));
                    dataPoster_Path.add(jsonObject.getString("release_date"));

                    for (int j = 0; j < dataTitle.size(); j++) {
                        if (receiveSearch == 3) {

                            Title.setText(dataTitle.get(j));
                            Popularity.setText(" " + dataPopularty.get(j) + " People Liked It");
                            Language.setText(" " + dataLanguage.get(j));
                            Votecount.setText(" " + dataVoteCount.get(j));
                            Overview.setText(" " + dataOverview.get(j));
                            Date.setText(" " + dataDate.get(j));

                            ImageLoader.getInstance().displayImage(urlbase + dataPoster_Path.get(j), Poster);
                        }
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
}
