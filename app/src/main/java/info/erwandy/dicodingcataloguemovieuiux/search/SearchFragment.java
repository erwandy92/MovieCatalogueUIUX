package info.erwandy.dicodingcataloguemovieuiux.search;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import info.erwandy.dicodingcataloguemovieuiux.DetailMovieActivity;
import info.erwandy.dicodingcataloguemovieuiux.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>>{

    ListView listView;
    EditText editJudul;
    ImageView imgPoster;
    Button btnCari;
    MovieAdapter adapter;
    private View view;

    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        adapter     = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();

        listView    = (ListView)view.findViewById(R.id.lvMovie);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                MovieItems item = (MovieItems)parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);

                intent.putExtra(DetailMovieActivity.EXTRA_TITLE, item.getMov_title());
                intent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW, item.getMov_description());
                intent.putExtra(DetailMovieActivity.EXTRA_RELEASE_DATE, item.getMov_date());
                intent.putExtra(DetailMovieActivity.EXTRA_POSTER_JPG, item.getMov_image());
                intent.putExtra(DetailMovieActivity.EXTRA_RATE_COUNT, item.getMov_rate_count());
                intent.putExtra(DetailMovieActivity.EXTRA_RATE, item.getMov_rate());

                startActivity(intent);
            }
        });

        editJudul   = (EditText)view.findViewById(R.id.et_judul);
        imgPoster   = (ImageView)view.findViewById(R.id.imgMoviePict);

        btnCari     = (Button)view.findViewById(R.id.btn_cari);
        btnCari.setOnClickListener(movieListener);

        String judul_movie = editJudul.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, judul_movie);

        getLoaderManager().initLoader(0, bundle, SearchFragment.this);

        return view;
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int i, Bundle bundle) {
        String judulMovie = "";
        if (bundle != null){
            judulMovie = bundle.getString(EXTRAS_MOVIE);
        }
        return new MovieAsyncTaskLoader(getActivity(), judulMovie);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> movieItems) {
        adapter.setData(movieItems);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener movieListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String judul_movie = editJudul.getText().toString();
            if(TextUtils.isEmpty(judul_movie)){
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, judul_movie);
            getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
        }
    };

}
