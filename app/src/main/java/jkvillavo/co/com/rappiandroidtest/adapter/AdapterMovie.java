package jkvillavo.co.com.rappiandroidtest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jkvillavo.co.com.rappiandroidtest.R;
import jkvillavo.co.com.rappiandroidtest.rest.ApiClient;
import jkvillavo.co.com.rappiandroidtest.rest.model.movielist.Movie;

/**
 * Created by JkVillavo12Col on 2/03/18.
 */

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MovieItemViewHolder> {

    private List<Movie> listMovies;
    private Context context;
    private View.OnClickListener listener;
    private AdapterMovie.IOnMovieAction mListener;

    public interface IOnMovieAction {

        void IOnMovieAction_show(Movie movie, ImageView imageView, TextView textViewTitulo);

    }

    public AdapterMovie(Context context, List<Movie> listTitles) {

        if (context instanceof AdapterMovie.IOnMovieAction) {
            mListener = (AdapterMovie.IOnMovieAction) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IOnMovieAction");
        }

        this.context = context;
        this.listMovies = listTitles;
    }


    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new AdapterMovie.MovieItemViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MovieItemViewHolder holder, final int position) {

        Movie movie = listMovies.get(position);
        holder.textViewTitulo.setText(movie.getTitle());
        holder.textViewAverage.setText(String.valueOf(movie.getVoteAverage()));
        holder.textViewFecha.setText(movie.getReleaseDate());

        Glide.with(context).load(ApiClient.IMAGE_BASE_URL + movie.getBackdropPath()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.IOnMovieAction_show(listMovies.get(position), holder.imageView, holder.textViewTitulo);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class MovieItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitulo, textViewFecha, textViewAverage;
        ImageView imageView;
        CardView cardView;

        public MovieItemViewHolder(View view) {

            super(view);
            textViewTitulo = (TextView) view.findViewById(R.id.itemMovie_textTitle);
            textViewFecha = (TextView) view.findViewById(R.id.itemMovie_textDate);
            textViewAverage = (TextView) view.findViewById(R.id.itemMovie_textAverage);
            cardView = (CardView) view.findViewById(R.id.itemMovie_card);
            imageView = (ImageView) view.findViewById(R.id.itemMovie_image);

        }

    }
}
