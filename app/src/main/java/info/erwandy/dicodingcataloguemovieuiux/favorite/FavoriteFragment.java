package info.erwandy.dicodingcataloguemovieuiux.favorite;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import info.erwandy.dicodingcataloguemovieuiux.R;
import info.erwandy.dicodingcataloguemovieuiux.adapter.FavoriteAdapter;

import static info.erwandy.dicodingcataloguemovieuiux.database.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private Context context;
    private Cursor list;
    private FavoriteAdapter adapter;

    RecyclerView rv_favorite;
    ProgressBar progressBar;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        context = view.getContext();

        progressBar = (ProgressBar)view.findViewById(R.id.progressbar_fav);

        adapter = new FavoriteAdapter(context);
        adapter.setListNotes(list);
        rv_favorite = (RecyclerView)view.findViewById(R.id.rv_category_fav);
        rv_favorite.setLayoutManager(new LinearLayoutManager(context));
        rv_favorite.setAdapter(adapter);

        new loadDataAsync().execute();
        showToast("Fav Frag onCreate");

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showToast("Fav Frag onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        new loadDataAsync().execute();
        showToast("Fav Frag onResume");
    }

    private class loadDataAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            showToast("Fav Frag onPreExe");
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return context.getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            progressBar.setVisibility(View.GONE);
            showToast("Fav Frag onPostExe");

            list = cursor;
//            adapter.replaceAll(list);
            adapter.setListNotes(list);
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0){
                showToast("Tidak ada data saat ini");
            }else{
                showToast("Total list: "+list.getCount());
            }
        }
    }

    private void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
