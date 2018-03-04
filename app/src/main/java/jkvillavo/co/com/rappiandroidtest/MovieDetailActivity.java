package jkvillavo.co.com.rappiandroidtest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import jkvillavo.co.com.rappiandroidtest.commons.Commons;
import jkvillavo.co.com.rappiandroidtest.commons.Utilidades;
import jkvillavo.co.com.rappiandroidtest.localdb.manager.RoomMovieDetailManager;
import jkvillavo.co.com.rappiandroidtest.localdb.model.RoomMovieDetail;
import jkvillavo.co.com.rappiandroidtest.rest.ApiClient;
import jkvillavo.co.com.rappiandroidtest.rest.ApiInterface;
import jkvillavo.co.com.rappiandroidtest.rest.model.moviedetail.MovieDetail;
import jkvillavo.co.com.rappiandroidtest.rest.model.movievideos.ResultVideosByMovieOut;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {


    @BindView(R.id.movieDetail_image)
    ImageView movieDetailImage;
    @BindView(R.id.movieDetail_toolbar)
    Toolbar movieDetailToolbar;
    @BindView(R.id.movieDetail_collapsing)
    CollapsingToolbarLayout movieDetailCollapsing;
    @BindView(R.id.movieDetail_appBarLayout)
    AppBarLayout movieDetailAppBarLayout;
    @BindView(R.id.movieDetail_imageVert)
    ImageView movieDetailImageVert;
    @BindView(R.id.movieDetail_textTitle)
    TextView movieDetailTextTitle;
    @BindView(R.id.movieDetail_textFecha)
    TextView movieDetailTextFecha;
    @BindView(R.id.movieDetail_textAverage)
    TextView movieDetailTextAverage;
    @BindView(R.id.movieDetail_fabVideo)
    FloatingActionButton movieDetailFabVideo;
    @BindView(R.id.movieDetail_coordinatorLayout)
    CoordinatorLayout movieDetailCoordinatorLayout;
    @BindView(R.id.movieDetail_textRatingsCount)
    TextView movieDetailTextRatingsCount;
    @BindView(R.id.movieDetail_textOverview)
    TextView movieDetailTextOverview;
    @BindView(R.id.movieDetail_textTagLine)
    TextView movieDetailTextTagLine;
    @BindView(R.id.movieDetail_imageHome)
    ImageView movieDetailImageHome;
    @BindView(R.id.movieDetail_textHomePage)
    TextView movieDetailTextHomePage;
    @BindView(R.id.movieDetail_imageGenero)
    ImageView movieDetailImageGenero;
    @BindView(R.id.movieDetail_textGenero)
    TextView movieDetailTextGenero;
    @BindView(R.id.movieDetail_imageCompanies)
    ImageView movieDetailImageCompanies;
    @BindView(R.id.movieDetail_textCompanies)
    TextView movieDetailTextCompanies;
    @BindView(R.id.movieDetail_textNoData)
    TextView movieDetailTextNoData;
    private int currentMovieId;
    private ResultVideosByMovieOut currentResultVideos;
    private MovieDetail currentMovie;
    private RoomMovieDetail currentRoomMovieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(movieDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(Commons.BundleKeys.BUNDLE_MOVIE_ID)) {
                currentMovieId = bundle.getInt(Commons.BundleKeys.BUNDLE_MOVIE_ID);
            }
        }

        trycallWSMovieDetail();
        trycallWSVideosByMovie();

        setListeners();
    }

    private void trycallWSMovieDetail() {

        if (Utilidades.haveNetworkConnection(MovieDetailActivity.this)) {
            try {
                movieDetailTextNoData.setVisibility(View.GONE);
                callWSMovieDetail();
            } catch (Exception exc) {
                movieDetailTextNoData.setVisibility(View.VISIBLE);
                Utilidades.showAlertDialog(MovieDetailActivity.this, getResources().getString(R.string.msg_generic_failWS));
            }
        } else {
            movieDetailTextNoData.setVisibility(View.VISIBLE);
            Utilidades.showSnackBarForPrimary((CoordinatorLayout) movieDetailCoordinatorLayout, MovieDetailActivity.this);

            TaskLoadMoviesDB taskLoadMoviesDB = new TaskLoadMoviesDB();
            taskLoadMoviesDB.execute();
        }
    }

    private void trycallWSVideosByMovie() {

        if (Utilidades.haveNetworkConnection(MovieDetailActivity.this)) {
            try {
                callWSVideosByMovie();
            } catch (Exception exc) {
                Utilidades.showAlertDialog(MovieDetailActivity.this, getResources().getString(R.string.msg_generic_failWS));
            }
        } else {
            Utilidades.showSnackBarForPrimary((CoordinatorLayout) movieDetailCoordinatorLayout, MovieDetailActivity.this);
        }
    }

    /**
     * Llamamos el ws del detalle de una pelicula
     */
    private void callWSMovieDetail() {

        ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
        Call<MovieDetail> call = apiService.getMovieDetails(currentMovieId, ApiClient.APIV3);

        call.enqueue(new Callback<MovieDetail>() {

            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                currentMovie = response.body();
                currentRoomMovieDetail = RoomMovieDetail.createFromMovieDetail(currentMovie);
                TaskInsertDatabase taskInsertDatabase = new TaskInsertDatabase();
                taskInsertDatabase.execute();
                loadMovie(currentMovie);
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
            }

        });


    }

    private void callWSVideosByMovie() {

        ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
        Call<ResultVideosByMovieOut> call = apiService.getVideosByMovie(currentMovieId, ApiClient.APIV3);

        call.enqueue(new Callback<ResultVideosByMovieOut>() {

            @Override
            public void onResponse(Call<ResultVideosByMovieOut> call, Response<ResultVideosByMovieOut> response) {
                currentResultVideos = response.body();

                movieDetailFabVideo.setVisibility(View.GONE);

                if (currentResultVideos.getResults() != null) {
                    if (!currentResultVideos.getResults().isEmpty()) {
                        movieDetailFabVideo.setVisibility(View.VISIBLE);
                        movieDetailFabVideo.animate()
                                .alpha(1.0f)
                                .setDuration(1000);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResultVideosByMovieOut> call, Throwable t) {
            }

        });

    }

    private void hideHomePage() {
        movieDetailTextHomePage.animate()
                .alpha(0.0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        movieDetailTextHomePage.setVisibility(View.GONE);
                    }
                });

        movieDetailImageHome.animate()
                .alpha(0.0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        movieDetailImageHome.setVisibility(View.GONE);
                    }
                });
    }

    /**
     * Cargamos la informacion de la pelicula que llega por medio del WS
     */
    private void loadMovie(MovieDetail currentMovieIn) {

        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_BASE_URL + currentMovieIn.getBackdrop_path()).into(movieDetailImage);
        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_BASE_URL + currentMovieIn.getPoster_path()).into(movieDetailImageVert);
        movieDetailTextTitle.setText(currentMovieIn.getTitle());
        movieDetailTextFecha.setText(currentMovieIn.getRelease_date());
        movieDetailTextAverage.setVisibility(View.VISIBLE);
        movieDetailTextAverage.setText(String.valueOf(currentMovieIn.getVote_average()));
        movieDetailTextRatingsCount.setText(getResources().getString(R.string.msg_ratings, String.valueOf(currentMovieIn.getVote_count())));
        movieDetailTextOverview.setText(currentMovieIn.getOverview());

        if ("".equalsIgnoreCase(currentMovieIn.getTagline())) {
            movieDetailTextTagLine.setVisibility(View.GONE);
        } else {
            movieDetailTextTagLine.setVisibility(View.VISIBLE);
            movieDetailTextTagLine.setText(currentMovieIn.getTagline());
        }

        if (currentMovieIn.getHomepage() != null) {
            if (!"".equalsIgnoreCase(currentMovieIn.getHomepage())) {
                movieDetailTextHomePage.setText(currentMovieIn.getHomepage());
                movieDetailTextHomePage.setVisibility(View.VISIBLE);
                movieDetailImageHome.setVisibility(View.VISIBLE);
            } else {
                hideHomePage();
            }
        } else {
            hideHomePage();
        }

        // generos
        movieDetailImageGenero.setVisibility(View.GONE);
        String textGenre = Utilidades.getStringFromListGenres(currentMovieIn.getGenres());
        if (!textGenre.equalsIgnoreCase("")) {
            movieDetailImageGenero.setVisibility(View.VISIBLE);
            movieDetailTextGenero.setText(textGenre);
        }

        // Companies
        movieDetailImageCompanies.setVisibility(View.GONE);
        movieDetailTextCompanies.setVisibility(View.GONE);
        String textCompanies = Utilidades.getStringFromListCompanies(currentMovieIn.getProduction_companies());
        if (!textCompanies.equalsIgnoreCase("")) {
            movieDetailImageCompanies.setVisibility(View.VISIBLE);
            movieDetailTextCompanies.setVisibility(View.VISIBLE);
            movieDetailTextCompanies.setText(textCompanies);
        }

    }

    private void loadMovie(RoomMovieDetail currentMovieIn) {

        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_BASE_URL + currentMovieIn.getBackdrop_path()).into(movieDetailImage);
        Glide.with(getApplicationContext()).load(ApiClient.IMAGE_BASE_URL + currentMovieIn.getPoster_path()).into(movieDetailImageVert);
        movieDetailTextTitle.setText(currentMovieIn.getTitle());
        movieDetailTextFecha.setText(currentMovieIn.getRelease_date());
        movieDetailTextAverage.setVisibility(View.VISIBLE);
        movieDetailTextAverage.setText(String.valueOf(currentMovieIn.getVote_average()));
        movieDetailTextRatingsCount.setText(getResources().getString(R.string.msg_ratings, String.valueOf(currentMovieIn.getVote_count())));
        movieDetailTextOverview.setText(currentMovieIn.getOverview());

        if ("".equalsIgnoreCase(currentMovieIn.getTagline())) {
            movieDetailTextTagLine.setVisibility(View.GONE);
        } else {
            movieDetailTextTagLine.setVisibility(View.VISIBLE);
            movieDetailTextTagLine.setText(currentMovieIn.getTagline());
        }

        if (currentMovieIn.getHomepage() != null) {
            if (!"".equalsIgnoreCase(currentMovieIn.getHomepage())) {
                movieDetailTextHomePage.setText(currentMovieIn.getHomepage());
                movieDetailTextHomePage.setVisibility(View.VISIBLE);
                movieDetailImageHome.setVisibility(View.VISIBLE);
            } else {
                hideHomePage();
            }
        } else {
            hideHomePage();
        }

        // generos
        movieDetailImageGenero.setVisibility(View.GONE);
        if (!currentMovieIn.getGenres().equalsIgnoreCase("")) {
            movieDetailImageGenero.setVisibility(View.VISIBLE);
            movieDetailTextGenero.setText(currentMovieIn.getGenres());
        }

        // Companies
        movieDetailImageCompanies.setVisibility(View.GONE);
        movieDetailTextCompanies.setVisibility(View.GONE);
        if (!currentMovieIn.getProduction_companies().equalsIgnoreCase("")) {
            movieDetailImageCompanies.setVisibility(View.VISIBLE);
            movieDetailTextCompanies.setText(currentMovieIn.getProduction_companies());
            movieDetailTextCompanies.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Seteamos los listeners en la actividad
     */
    private void setListeners() {

        movieDetailFabVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random rand = new Random();
                int randomNum = rand.nextInt((currentResultVideos.getResults().size() - 1));
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Commons.Webs.YOUTUBE + currentResultVideos.getResults().get(randomNum).getKey()));
                startActivity(browserIntent);

            }
        });

        movieDetailTextHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentMovie.getHomepage()));
                startActivity(browserIntent);
            }
        });

    }

    private class TaskInsertDatabase extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {

            try {
                RoomMovieDetailManager.insertMovieDetail(getApplicationContext(), currentRoomMovieDetail);
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
                currentRoomMovieDetail = RoomMovieDetailManager.loadRoomMovieDetail(getApplicationContext(), currentMovieId);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Ups fallo en la dblocal", Toast.LENGTH_SHORT);
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {
            if (currentRoomMovieDetail != null) {
                loadMovie(currentRoomMovieDetail);
                movieDetailTextNoData.setVisibility(View.GONE);
            }
        }
    }


}
