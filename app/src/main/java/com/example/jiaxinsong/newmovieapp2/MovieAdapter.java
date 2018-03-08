package com.example.jiaxinsong.newmovieapp2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.jiaxinsong.newmovieapp2.models.MovieModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by 1694370 on 2/27/2018.
 */

public class MovieAdapter extends ArrayAdapter {

    public String urlbase = "https://image.tmdb.org/t/p/w500/";
    private List<MovieModel> movieModelList;
    private int resource;
    private LayoutInflater inflater;

    public MovieAdapter(Context context, int resource, List<MovieModel> objects) {
        super(context, resource, objects);


        movieModelList = objects;
        this.resource = resource;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, @Nullable View convertView,@Nullable ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
        }

        ImageView ivMovieIcon;
        TextView tvMovie;
        TextView tvLanguage;
        TextView tvDate;
        RatingBar rbMovie;
        TextView tvOverview;
        TextView tvCount;
        TextView tvPopular;

        ivMovieIcon = convertView.findViewById(R.id.ivIcon);
        tvMovie = convertView.findViewById(R.id.tvMovie);
        tvLanguage = convertView.findViewById(R.id.tvLanguage);
        tvDate = convertView.findViewById(R.id.tvDate);
        tvOverview = convertView.findViewById(R.id.tvOverview);
        rbMovie = convertView.findViewById(R.id.rbMovie);
        tvCount = convertView.findViewById(R.id.tvCount);
        tvPopular = convertView.findViewById(R.id.tvPopular);

        tvMovie.setText(movieModelList.get(position).getTitle());
        tvLanguage.setText("Original Language: "+movieModelList.get(position).getOriginal_laguage());
        tvDate.setText(" " + movieModelList.get(position).getRelease_date());
        tvOverview.setText("Overview: "+movieModelList.get(position).getOverview());
        rbMovie.setRating((float) (movieModelList.get(position).getVote_average() / 2));
        tvCount.setText("" + movieModelList.get(position).getVote_count()+" People Voted it:");
        tvPopular.setText("" + (int) movieModelList.get(position).getPopular()+" People Liked It");
        ImageLoader.getInstance().displayImage(urlbase+movieModelList.get(position).getImage(), ivMovieIcon);
        return convertView;
    }
}