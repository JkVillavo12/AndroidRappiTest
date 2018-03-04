package jkvillavo.co.com.rappiandroidtest.localdb.manager;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jkvillavo.co.com.rappiandroidtest.localdb.RoomMovieDatabase;
import jkvillavo.co.com.rappiandroidtest.localdb.model.RoomMovie;
import jkvillavo.co.com.rappiandroidtest.rest.model.movielist.Movie;

/**
 * Created by JkVillavo12Col on 3/03/18.
 */

public class RoomMovieManager {

    /**
     * Insertamos las Movie que llegan desde la actividad
     *
     * @param applicationContext
     * @param currentResponseMovies
     * @param typeCategory
     */
    public static void insertMovies(Context applicationContext, List<Movie> currentResponseMovies, int typeCategory) {

        RoomMovieDatabase db;
        db = Room.databaseBuilder(applicationContext,
                RoomMovieDatabase.class, RoomMovieDatabase.DB_NAME).fallbackToDestructiveMigration().build();

        List<RoomMovie> roomMovies = RoomMovie.creatRoomMovieFromMovie(currentResponseMovies, typeCategory);
        for (RoomMovie roomMovie : roomMovies) {
            db.roomMovieDao().insertMovies(roomMovie);
        }

        db.close();
    }

    public static List<Movie> loadRoomMoviesByCategory(Context applicationContext, String category) {

        List<Movie> moviesOut = new ArrayList<>();

        RoomMovieDatabase db;
        db = Room.databaseBuilder(applicationContext,
                RoomMovieDatabase.class, RoomMovieDatabase.DB_NAME).fallbackToDestructiveMigration().build();
        List<RoomMovie> roomMovies = Arrays.asList(db.roomMovieDao().loadMoviesByTypeList(String.valueOf(category)));
        for (RoomMovie roomMovie : roomMovies) {
            moviesOut.add(new Movie(roomMovie.getPosterPath(), roomMovie.isAdult(), roomMovie.getOverview(),
                    roomMovie.getReleaseDate(), null, roomMovie.getId(), roomMovie.getOriginalTitle(), roomMovie.getOriginalLanguage(),
                    roomMovie.getTitle(), roomMovie.getBackdropPath(), roomMovie.getPopularity(), roomMovie.getVoteCount(), roomMovie.getVideo(), roomMovie.getVoteAverage()));
        }
        db.close();

        return moviesOut;
    }

    public static List<Movie> loadRoomMoviesByCategoryAndQuery(Context applicationContext, String typeCategory, String query) {

        List<Movie> moviesOut = new ArrayList<>();

        RoomMovieDatabase db;
        db = Room.databaseBuilder(applicationContext,
                RoomMovieDatabase.class, RoomMovieDatabase.DB_NAME).fallbackToDestructiveMigration().build();
        List<RoomMovie> roomMovies = Arrays.asList(db.roomMovieDao().loadMoviesByTypeListAndQuery(String.valueOf(typeCategory), "%" + query + "%"));
        for (RoomMovie roomMovie : roomMovies) {
            moviesOut.add(new Movie(roomMovie.getPosterPath(), roomMovie.isAdult(), roomMovie.getOverview(),
                    roomMovie.getReleaseDate(), null, roomMovie.getId(), roomMovie.getOriginalTitle(), roomMovie.getOriginalLanguage(),
                    roomMovie.getTitle(), roomMovie.getBackdropPath(), roomMovie.getPopularity(), roomMovie.getVoteCount(), roomMovie.getVideo(), roomMovie.getVoteAverage()));
        }
        db.close();

        return moviesOut;
    }
}
