package info.erwandy.dicodingcataloguemovieuiux.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
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
import info.erwandy.dicodingcataloguemovieuiux.DetailMovieActivity;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Cursor list;
    private Context context;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setListNotes(Cursor list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_now_playing, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MovieItems mov = getItem(position);

        holder.title.setText(mov.getMov_title());
        holder.overview.setText(mov.getMov_description());

        String release_date             = mov.getMov_date();
        SimpleDateFormat date_format    = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date_                          = date_format.parse(release_date);
            SimpleDateFormat new_date_format    = new SimpleDateFormat("E, MMM dd, yyyy");
            String date_of_release              = new_date_format.format(date_);
            holder.date.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(holder.itemView.getContext())
                .load("http://image.tmdb.org/t/p/w500/"+mov.getMov_image())
                .into(holder.poster);

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "Favorite: "+mov.getMov_title(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "Share: "+mov.getMov_title(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.cvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(holder.itemView.getContext(), DetailMovieActivity.class);
                Intent.putExtra(DetailMovieActivity.EXTRA_ID, mov.getMov_id());
                Intent.putExtra(DetailMovieActivity.EXTRA_TITLE, mov.getMov_title());
                Intent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW, mov.getMov_description());
                Intent.putExtra(DetailMovieActivity.EXTRA_POSTER_JPG, mov.getMov_image());
                Intent.putExtra(DetailMovieActivity.EXTRA_RELEASE_DATE, mov.getMov_date());
                Intent.putExtra(DetailMovieActivity.EXTRA_RATE, mov.getMov_rate());
                Intent.putExtra(DetailMovieActivity.EXTRA_RATE_COUNT, mov.getMov_rate_count());
                holder.itemView.getContext().startActivity(Intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.getCount();
    }

    private MovieItems getItem(int position){
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItems(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, overview, date;
        public ImageView poster;
        public Button btnFavorite, btnShare;
        public LinearLayout cvDetail;

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
    }

}
