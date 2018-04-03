package info.erwandy.favoritemovierecords.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.erwandy.favoritemovierecords.DetailActivity;
import info.erwandy.favoritemovierecords.R;

import static info.erwandy.favoritemovierecords.database.DatabaseContract.getColumnString;
import static info.erwandy.favoritemovierecords.database.FavoriteColumns.COL_OVERVIEW;
import static info.erwandy.favoritemovierecords.database.FavoriteColumns.COL_POSTER;
import static info.erwandy.favoritemovierecords.database.FavoriteColumns.COL_RATE;
import static info.erwandy.favoritemovierecords.database.FavoriteColumns.COL_RATE_COUNT;
import static info.erwandy.favoritemovierecords.database.FavoriteColumns.COL_RELEASE_DATE;
import static info.erwandy.favoritemovierecords.database.FavoriteColumns.COL_TITLE;

public class FavAdapter extends CursorAdapter {

    public FavAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        return view;
    }


    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView title, overview, date;
        ImageView poster;
        LinearLayout cv_movie;

        if (cursor != null){
            title       = (TextView) view.findViewById(R.id.tv_item_name);
            poster      = (ImageView) view.findViewById(R.id.img_item_photo);
            overview    = (TextView) view.findViewById(R.id.tv_item_overview);
            date        = (TextView) view.findViewById(R.id.tv_item_date);
            cv_movie    = (LinearLayout)view.findViewById(R.id.cv_movie);

            title.setText(getColumnString(cursor, COL_TITLE));
            overview.setText(getColumnString(cursor, COL_OVERVIEW));

            String release_date             = getColumnString(cursor, COL_RELEASE_DATE);
            SimpleDateFormat date_format    = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date_                          = date_format.parse(release_date);
                SimpleDateFormat new_date_format    = new SimpleDateFormat("E, MMM dd, yyyy");
                String date_of_release              = new_date_format.format(date_);
                date.setText(date_of_release);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(view.getContext())
                    .load("http://image.tmdb.org/t/p/w500/"+getColumnString(cursor, COL_POSTER))
                    .into(poster);

            cv_movie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Intent = new Intent(context, DetailActivity.class);
                    Intent.putExtra(DetailActivity.EXTRA_TITLE, getColumnString(cursor, COL_TITLE));
                    Intent.putExtra(DetailActivity.EXTRA_OVERVIEW, getColumnString(cursor, COL_OVERVIEW));
                    Intent.putExtra(DetailActivity.EXTRA_POSTER_JPG, getColumnString(cursor, COL_POSTER));
                    Intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, getColumnString(cursor, COL_RELEASE_DATE));
                    Intent.putExtra(DetailActivity.EXTRA_RATE, getColumnString(cursor, COL_RATE));
                    Intent.putExtra(DetailActivity.EXTRA_RATE_COUNT, getColumnString(cursor, COL_RATE_COUNT));
                    context.startActivity(Intent);
                }
            });
        }
    }
}
