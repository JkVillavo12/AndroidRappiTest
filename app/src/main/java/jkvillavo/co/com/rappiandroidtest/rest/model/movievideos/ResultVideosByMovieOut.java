package jkvillavo.co.com.rappiandroidtest.rest.model.movievideos;

import java.util.List;

/**
 * Created by JkVillavo12Col on 2/03/18.
 */

public class ResultVideosByMovieOut {

    public int id;
    public List<VideoByMovieOut> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<VideoByMovieOut> getResults() {
        return results;
    }

    public void setResults(List<VideoByMovieOut> results) {
        this.results = results;
    }
}
