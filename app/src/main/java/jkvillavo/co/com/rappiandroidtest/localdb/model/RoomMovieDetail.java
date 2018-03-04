package jkvillavo.co.com.rappiandroidtest.localdb.model;

import android.arch.persistence.room.Entity;

import jkvillavo.co.com.rappiandroidtest.commons.Utilidades;
import jkvillavo.co.com.rappiandroidtest.rest.model.moviedetail.MovieDetail;

/**
 * Created by JkVillavo12Col on 3/03/18.
 */

@Entity(primaryKeys = {"id"})
public class RoomMovieDetail {

    public boolean adult;
    public String backdrop_path;
    public int budget;
    public String genres;
    public String homepage;
    public int id;
    public String imdb_id;
    public String original_language;
    public String original_title;
    public String overview;
    public double popularity;
    public String poster_path;
    public String production_companies;
    public String production_countries;
    public String release_date;
    public int revenue;
    public int runtime;
    public String status;
    public String tagline;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;

    public RoomMovieDetail() {
    }

    public RoomMovieDetail(boolean adult, String backdrop_path, int budget, String genres, String homepage, int id, String imdb_id, String original_language, String original_title, String overview, double popularity, String poster_path, String production_companies, String production_countries, String release_date, int revenue, int runtime, String status, String tagline, String title, boolean video, double vote_average, int vote_count) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.budget = budget;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.imdb_id = imdb_id;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.production_companies = production_companies;
        this.production_countries = production_countries;
        this.release_date = release_date;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public static RoomMovieDetail createFromMovieDetail(MovieDetail movieDetail) {

        RoomMovieDetail roomMovieDetail = new RoomMovieDetail(movieDetail.isAdult(), movieDetail.getBackdrop_path(),
                movieDetail.getBudget(), Utilidades.getStringFromListGenres(movieDetail.getGenres()), movieDetail.getHomepage(), movieDetail.getId(),
                movieDetail.getImdb_id(), movieDetail.getOriginal_language(), movieDetail.getOriginal_title(), movieDetail.getOverview(),
                movieDetail.getPopularity(), movieDetail.getPoster_path(), Utilidades.getStringFromListCompanies(movieDetail.getProduction_companies()),
                "", movieDetail.getRelease_date(), movieDetail.getRevenue(),
                movieDetail.getRuntime(), movieDetail.getStatus(), movieDetail.getTagline(), movieDetail.getTitle(),
                movieDetail.isVideo(), movieDetail.getVote_average(), movieDetail.getVote_count());

        return roomMovieDetail;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(String production_companies) {
        this.production_companies = production_companies;
    }

    public String getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(String production_countries) {
        this.production_countries = production_countries;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
