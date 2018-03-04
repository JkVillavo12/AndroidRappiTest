package jkvillavo.co.com.rappiandroidtest.localdb.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.ArrayList;
import java.util.List;

import jkvillavo.co.com.rappiandroidtest.rest.model.movielist.Movie;

/**
 * Created by JkVillavo12Col on 3/03/18.
 */

@Entity(primaryKeys = {"id"})
public class RoomMovie {

    public int id;
    public String posterPath;
    public boolean adult;
    public String overview;
    public String releaseDate;
    public String originalTitle;
    public String originalLanguage;
    public String title;
    public String backdropPath;
    public Double popularity;
    public int voteCount;
    public Boolean video;
    public Double voteAverage;
    public String type;

    @Ignore
    public List<Integer> genreIds = new ArrayList<Integer>();

    public RoomMovie() {

    }

    public RoomMovie(int id, String posterPath, boolean adult, String overview, String releaseDate, String originalTitle, String originalLanguage, String title, String backdropPath, Double popularity, int voteCount, Boolean video, Double voteAverage, String type, List<Integer> genreIds) {
        this.id = id;
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.type = type;
        this.genreIds = genreIds;
    }

    public static List<RoomMovie> creatRoomMovieFromMovie(List<Movie> movies, int typeCategory) {

        List<RoomMovie> roomMovies = new ArrayList<>();

        RoomMovie roomMovie;
        for (Movie movie : movies) {
            roomMovie = new RoomMovie(movie.getId(), movie.getPosterPath(), movie.isAdult(), movie.getOverview(), movie.getReleaseDate(), movie.getOriginalTitle(), movie.getOriginalLanguage(),
                    movie.getTitle(), movie.getBackdropPath(), movie.getPopularity(), movie.getVoteCount(), movie.getVideo(), movie.getVoteAverage(), String.valueOf(typeCategory), null);
            roomMovies.add(roomMovie);
        }

        return roomMovies;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }
}
