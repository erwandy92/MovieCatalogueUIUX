package info.erwandy.dicodingcataloguemovieuiux.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.erwandy.dicodingcataloguemovieuiux.R;
import info.erwandy.dicodingcataloguemovieuiux.search.DetailMovieActivity;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Cursor list;

    public FavoriteAdapter(Cursor items) {
        replaceAll(items);
    }

    public void replaceAll(Cursor items) {
        list = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_now_playing, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private MovieItems getItem(int position){
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItems(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView title, overview, date;
        public ImageView poster;
        public Button btnFavorite, btnShare;
        public LinearLayout cvDetail; //cv = cardview

        public ViewHolder(View itemView) {
            super(itemView);

            title       = (TextView) itemView.findViewById(R.id.tv_item_name);
            poster      = (ImageView) itemView.findViewById(R.id.img_item_photo);
            overview    = (TextView) itemView.findViewById(R.id.tv_item_overview);
            date        = (TextView) itemView.findViewById(R.id.tv_item_date);
            btnFavorite = (Button) itemView.findViewById(R.id.btn_set_favorite);
            btnShare    = (Button) itemView.findViewById(R.id.btn_set_share);
            cvDetail    = (LinearLayout) itemView.findViewById(R.id.cv_movie);
        }

        public void bind(final MovieItems item) {
            title.setText(item.getMov_title());
            overview.setText(item.getMov_description());

            String release_date = item.getMov_date();
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date_ = date_format.parse(release_date);

                SimpleDateFormat new_date_format = new SimpleDateFormat("E, MMM dd, yyyy");
                String date_of_release = new_date_format.format(date_);
                date.setText(date_of_release);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w500/"+item.getMov_image())
                    .into(poster);

            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "Favorite: "+item.getMov_title(), Toast.LENGTH_SHORT).show();
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "Share: "+item.getMov_title(), Toast.LENGTH_SHORT).show();
                }
            });

            cvDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent Intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                    Intent.putExtra(DetailMovieActivity.EXTRA_TITLE, item.getMov_title());
                    Intent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW, item.getMov_description());
                    Intent.putExtra(DetailMovieActivity.EXTRA_POSTER_JPG, item.getMov_image());
                    Intent.putExtra(DetailMovieActivity.EXTRA_RELEASE_DATE, item.getMov_date());
                    Intent.putExtra(DetailMovieActivity.EXTRA_RATE, item.getMov_rate());
                    Intent.putExtra(DetailMovieActivity.EXTRA_RATE_COUNT, item.getMov_rate_count());
                    itemView.getContext().startActivity(Intent);
                }
            });
        }
    }
}
