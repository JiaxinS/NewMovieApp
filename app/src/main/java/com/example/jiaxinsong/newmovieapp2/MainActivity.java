package com.example.jiaxinsong.newmovieapp2;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.tv.TvContentRating;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.jiaxinsong.newmovieapp2.models.MovieModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvMovies;
    private TextView textview;
    public int firstsearch;

    ArrayList<String> dataTitle = new ArrayList<>();
    ArrayList<Integer> dataVoteCount = new ArrayList<>();
    ArrayList<Double> dataVoteAverage = new ArrayList<>();
    ArrayList<String> dataLanguage = new ArrayList<>();
    ArrayList<Double> dataPopularty = new ArrayList<>();
    ArrayList <String> dataOverview= new ArrayList<>();
    ArrayList<String> dataDate = new ArrayList<>();
    ArrayList<String> dataPoster_Path = new ArrayList<>();

        public void onclick(View v) {
                 Intent myintent = new Intent(this,results.class);

                 for( int i = 0; i <dataTitle.size();i++){
                     if(dataTitle.contains(textview.getText().toString().toLowerCase()))
                     {
                         firstsearch = dataTitle.indexOf(textview.getText().toString().toLowerCase());

                         myintent.putExtra("firstsearch",firstsearch);
                     }
                 }
                 startActivity(myintent);
                 firstsearch =-1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview= findViewById(R.id.edtSeach);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        lvMovies = (ListView) findViewById(R.id.mymovielist);

        String[] myStringArray;
        myStringArray = new String[]{"1","2","3","4","5"};
        for (int i = 0; i<myStringArray.length; i ++){
            new JSONTask().execute("http://api.themoviedb.org/3/movie/popular?api_key=9dc955d50087c63a07941ae03100e094&page="+i);
        }
    }


    public class JSONTask extends AsyncTask<String, String, List<MovieModel>> {

        @Override
        protected List<MovieModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            List<MovieModel> movieModelList = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);

                JSONArray parentArray = parentObject.getJSONArray("results");

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    MovieModel movieModel = new MovieModel();
                    movieModel.setTitle(finalObject.getString("title"));
                    movieModel.setVote_count(finalObject.getInt("vote_count"));
                    movieModel.setVote_average(finalObject.getDouble("vote_average"));
                    movieModel.setOriginal_laguage(finalObject.getString("original_language"));
                    movieModel.setPopular(finalObject.getDouble("popularity"));
                    movieModel.setOverview(finalObject.getString("overview"));
                    movieModel.setRelease_date(finalObject.getString("release_date"));
                    movieModel.setImage(finalObject.getString("poster_path"));

                    movieModelList.add(movieModel);

                    dataTitle.add(finalObject.getString("title").toLowerCase());
                    dataVoteCount.add(finalObject.getInt("vote_count"));
                    dataVoteAverage.add(finalObject.getDouble("vote_average"));
                    dataLanguage.add(finalObject.getString("original_language"));
                    dataPopularty.add(finalObject.getDouble("popularity"));
                    dataOverview.add(finalObject.getString("overview"));
                    dataDate.add(finalObject.getString("release_date"));
                    dataPoster_Path.add(finalObject.getString("poster_path"));
                }
                return movieModelList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return movieModelList;
        }

        @Override
        protected void onPostExecute(List<MovieModel> result) {
            super.onPostExecute(result);
            MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row, result);
            lvMovies.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            new JSONTask().execute("http://api.themoviedb.org/3/movie/popular?api_key=9dc955d50087c63a07941ae03100e094");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}