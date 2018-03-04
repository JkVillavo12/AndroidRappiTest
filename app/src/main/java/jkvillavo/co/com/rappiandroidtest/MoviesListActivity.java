package jkvillavo.co.com.rappiandroidtest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jkvillavo.co.com.rappiandroidtest.adapter.AdapterMovie;
import jkvillavo.co.com.rappiandroidtest.commons.Commons;
import jkvillavo.co.com.rappiandroidtest.commons.Utilidades;
import jkvillavo.co.com.rappiandroidtest.localdb.manager.RoomMovieManager;
import jkvillavo.co.com.rappiandroidtest.rest.ApiClient;
import jkvillavo.co.com.rappiandroidtest.rest.ApiInterface;
import jkvillavo.co.com.rappiandroidtest.rest.model.movielist.Movie;
import jkvillavo.co.com.rappiandroidtest.rest.model.movielist.MoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListActivity extends AppCompatActivity implements AdapterMovie.IOnMovieAction {


    @BindView(R.id.movieList_textNoData)
    TextView movieListTextNoData;
    @BindView(R.id.search_buttonShow)
    ImageButton searchButtonShow;
    @BindView(R.id.search_textSaludo)
    TextView searchTextSaludo;
    @BindView(R.id.search_textTexto)
    TextView searchTextTexto;
    @BindView(R.id.search_editTextBusqueda)
    EditText searchEditTextBusqueda;
    @BindView(R.id.search_card)
    CardView searchCard;
    @BindView(R.id.moviesList_recycler)
    RecyclerView moviesListRecycler;
    @BindView(R.id.moviesList_coordinator)
    CoordinatorLayout moviesListCoordinator;
    @BindView(R.id.search_textInputBusqueda)
    TextInputLayout searchTextInputBusqueda;
    List<Movie> currentResponseMovies;
    private boolean verMas = false;
    private AdapterMovie adapterMovie;
    private int typeCategory = 0;

    public static final int BY_SEARCH = 10;

    @Override
    public void IOnMovieAction_show(Movie movie, ImageView imageView, TextView textViewTitulo) {

        Intent intent = new Intent(MoviesListActivity.this, MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Commons.BundleKeys.BUNDLE_MOVIE_ID, movie.getId());
        intent.putExtras(bundle);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, imageView, "transition_movie");
        startActivity(intent, options.toBundle());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(Commons.BundleKeys.BUNDLE_CATEGORY_TYPE)) {
                typeCategory = bundle.getInt(Commons.BundleKeys.BUNDLE_CATEGORY_TYPE);
            }
        }
        tryCallWsByType();

    }

    @Override
    protected void onStart() {

        super.onStart();
        configureListeners();
        verMas = false;
        configureVerMas();
        configureButtomSearch();

    }

    /**
     * Según el tipo de seleccion de categoria, llamamos un ws
     */
    private void callWSByType() {

        ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
        Call<MoviesResponse> call = null;

        switch (typeCategory) {
            case Commons.MovieCategory.CATEGORY_POPULARES:
                call = apiService.getPopularMovies(ApiClient.APIV3);
                break;
            case Commons.MovieCategory.CATEGORY_TOPRATED:
                call = apiService.getTopRatedMovies(ApiClient.APIV3);
                break;
            case Commons.MovieCategory.CATEGORY_UPCOMING:
                call = apiService.getUpComingMovies(ApiClient.APIV3);
                break;
            default:
                break;

        }

        call.enqueue(new Callback<MoviesResponse>() {

            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                currentResponseMovies = response.body().getResults();
                TaskInsertDatabase taskInsertDatabase = new TaskInsertDatabase();
                taskInsertDatabase.execute();
                loadMovies(currentResponseMovies);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
            }

        });

    }

    private void callWsSearchByQuery(String query) {

        ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
        Call<MoviesResponse> call = null;

        call = apiService.findMoviesByQuery(query, ApiClient.APIV3);

        call.enqueue(new Callback<MoviesResponse>() {

            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                currentResponseMovies = response.body().getResults();
                typeCategory = BY_SEARCH;
                TaskInsertDatabase taskInsertDatabase = new TaskInsertDatabase();
                taskInsertDatabase.execute();
                loadMovies(currentResponseMovies);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
            }

        });

    }

    private void configureButtomSearch() {

        searchEditTextBusqueda.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (searchEditTextBusqueda.getRight() - searchEditTextBusqueda.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (searchEditTextBusqueda.getText().toString().length() < 3) {
                            searchTextInputBusqueda.setErrorEnabled(true);
                            searchTextInputBusqueda.setError(getResources().getString(R.string.msg_caracteres, "3"));
                        } else {
                            searchTextInputBusqueda.setError(null);
                            searchTextInputBusqueda.setErrorEnabled(false);

                            tryCallWsSearchByQuery(searchEditTextBusqueda.getText().toString());
                        }

                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void configureListeners() {

        searchButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verMas = !verMas;
                configureVerMas();

            }
        });
    }

    private void configureVerMas() {

        if (verMas) {
            searchTextTexto.setVisibility(View.VISIBLE);
            searchCard.setVisibility(View.VISIBLE);
            searchButtonShow.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_36dp));
        } else {
            searchTextTexto.setVisibility(View.GONE);
            searchCard.setVisibility(View.GONE);
            searchButtonShow.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_36dp));
        }

    }

    /**
     * Cargamos la lista de peliculas en el recyclerView
     *
     * @param movies lista de peliculas
     */
    private void loadMovies(List<Movie> movies) {

        adapterMovie = new AdapterMovie(this, movies);

        moviesListRecycler.setAdapter(adapterMovie);
        moviesListRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        moviesListRecycler.setItemAnimator(new DefaultItemAnimator());

    }

    private void tryCallWsByType() {

        if (Utilidades.haveNetworkConnection(MoviesListActivity.this)) {
            try {
                callWSByType();
            } catch (Exception exc) {
                movieListTextNoData.setVisibility(View.VISIBLE);
                Utilidades.showAlertDialog(MoviesListActivity.this, getResources().getString(R.string.msg_generic_failWS));
            }
        } else {
            TaskLoadMoviesDB taskLoadMoviesDB = new TaskLoadMoviesDB();
            taskLoadMoviesDB.execute();
        }
    }

    private void tryCallWsSearchByQuery(String s) {

        if (Utilidades.haveNetworkConnection(MoviesListActivity.this)) {
            try {
                callWsSearchByQuery(s);
            } catch (Exception exc) {
                movieListTextNoData.setVisibility(View.VISIBLE);
                Utilidades.showAlertDialog(MoviesListActivity.this, getResources().getString(R.string.msg_generic_failWS));
            }
        } else {
            TaskLoadMoviesDBByQuery taskLoadMoviesDB = new TaskLoadMoviesDBByQuery();
            taskLoadMoviesDB.execute(s);
        }

    }

    private class TaskInsertDatabase extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {

            try {
                RoomMovieManager.insertMovies(getApplicationContext(), currentResponseMovies, typeCategory);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Ups fallo en la dblocal", Toast.LENGTH_SHORT);
            }

            return 1;
        }
    }

    private class TaskLoadMoviesDB extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {

            try {
                currentResponseMovies = RoomMovieManager.loadRoomMoviesByCategory(getApplicationContext(), String.valueOf(typeCategory));
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Ups fallo en la dblocal", Toast.LENGTH_SHORT);
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {
            if (currentResponseMovies != null) {
                if (!currentResponseMovies.isEmpty()) {
                    loadMovies(currentResponseMovies);
                } else {
                    movieListTextNoData.setVisibility(View.VISIBLE);
                    Utilidades.showSnackBarForPrimary((CoordinatorLayout) moviesListCoordinator, MoviesListActivity.this);
                }
            } else {
                movieListTextNoData.setVisibility(View.VISIBLE);
                Utilidades.showSnackBarForPrimary((CoordinatorLayout) moviesListCoordinator, MoviesListActivity.this);
            }
        }
    }

    private class TaskLoadMoviesDBByQuery extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... string) {

            try {
                currentResponseMovies = RoomMovieManager.loadRoomMoviesByCategoryAndQuery(getApplicationContext(), String.valueOf(BY_SEARCH), string[0]);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Ups fallo en la dblocal", Toast.LENGTH_SHORT);
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {
            if (currentResponseMovies != null) {
                if (!currentResponseMovies.isEmpty()) {
                    loadMovies(currentResponseMovies);
                } else {
                    movieListTextNoData.setVisibility(View.VISIBLE);
                    moviesListRecycler.setVisibility(View.GONE);
                    Utilidades.showSnackBarForPrimary((CoordinatorLayout) moviesListCoordinator, MoviesListActivity.this);
                }
            } else {
                movieListTextNoData.setVisibility(View.VISIBLE);
                Utilidades.showSnackBarForPrimary((CoordinatorLayout) moviesListCoordinator, MoviesListActivity.this);
            }
        }
    }
}
