package info.erwandy.dicodingcataloguemovieuiux;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.erwandy.dicodingcataloguemovieuiux.adapter.MovieItems;
import info.erwandy.dicodingcataloguemovieuiux.database.FavoriteHelper;

import static info.erwandy.dicodingcataloguemovieuiux.database.DatabaseContract.CONTENT_URI;
import static info.erwandy.dicodingcataloguemovieuiux.database.FavoriteColumns.*;

public class DetailMovieActivity extends AppCompatActivity {

    public static String EXTRA_ID           = "extra_id";
    public static String EXTRA_TITLE        = "extra_title";
    public static String EXTRA_OVERVIEW     = "extra_overview";
    public static String EXTRA_RELEASE_DATE = "extra_release_date";
    public static String EXTRA_POSTER_JPG   = "extra_poster_jpg";
    public static String EXTRA_RATE_COUNT   = "extra_rate_count";
    public static String EXTRA_RATE         = "extra_rate";

    private TextView tvJudul, tvOverview, tvReleaseDate, tvRating;
    private ImageView imgPoster, imgFav;

    private Boolean isFavorite = false;
    private FavoriteHelper favoriteHelper;
    private MovieItems item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvJudul = (TextView)findViewById(R.id.detailJudul);
        tvOverview = (TextView)findViewById(R.id.detailOverview);
        tvReleaseDate = (TextView)findViewById(R.id.detailReleaseDate);
        tvRating = (TextView)findViewById(R.id.detailVote);
        imgPoster = (ImageView)findViewById(R.id.imgPoster);
        imgFav = (ImageView)findViewById(R.id.iv_fav);

        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String rating = getIntent().getStringExtra(EXTRA_RATE);
        String rating_count = getIntent().getStringExtra(EXTRA_RATE_COUNT);
        String poster_jpg = getIntent().getStringExtra(EXTRA_POSTER_JPG);
        String release_date = getIntent().getStringExtra(EXTRA_RELEASE_DATE);

        item = new MovieItems();
        item.setMov_id(id);
        item.setMov_title(title);
        item.setMov_description(overview);
        item.setMov_rate(rating);
        item.setMov_rate_count(rating_count);
        item.setMov_image(poster_jpg);
        item.setMov_date(release_date);

        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            tvReleaseDate.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvJudul.setText(title);
        tvOverview.setText(overview);
        tvRating.setText(rating_count+" Ratings ("+rating+"/10)");
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500/"+poster_jpg).into(imgPoster);

        loadDataSQLite();

        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavorite){
                    //if it's set as favorite, click to delete
                    deleteFav();
                }else{
                    //if it's NOT set as favorite, click to save
                    saveFav();
                }

                isFavorite = !isFavorite;
                changeFavColor();
            }
        });
    }

    private void loadDataSQLite() {
        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + item.getMov_id()),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        changeFavColor();
    }

    private void changeFavColor() {
        if(isFavorite){
            imgFav.setImageResource(R.drawable.ic_favorite);
        }else{
            imgFav.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    private void saveFav() {
//        Toast.makeText(this, "ID:"+item.getMov_id(), Toast.LENGTH_SHORT).show();
        ContentValues cv = new ContentValues();
        cv.put(COL_ID, item.getMov_id());
        cv.put(COL_TITLE, item.getMov_title());
        cv.put(COL_POSTER, item.getMov_image());
        cv.put(COL_RELEASE_DATE, item.getMov_date());
        cv.put(COL_RATE, item.getMov_rate());
        cv.put(COL_RATE_COUNT, item.getMov_rate_count());
        cv.put(COL_OVERVIEW, item.getMov_description());

        getContentResolver().insert(CONTENT_URI, cv);
        Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();
    }

    private void deleteFav() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + item.getMov_id()),
                null,
                null
        );
        Toast.makeText(this, R.string.remove, Toast.LENGTH_SHORT).show();
    }


}
