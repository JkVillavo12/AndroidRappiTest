package jkvillavo.co.com.rappiandroidtest.localdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import jkvillavo.co.com.rappiandroidtest.localdb.model.RoomMovie;

/**
 * Created by JkVillavo12Col on 3/03/18.
 */

@Dao
public interface RoomMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMovies(RoomMovie... movies);

    @Query("SELECT * FROM RoomMovie WHERE type = :type")
    public RoomMovie[] loadMoviesByTypeList(String type);

    @Query("SELECT * FROM RoomMovie WHERE type = :type and title LIKE :query")
    public RoomMovie[] loadMoviesByTypeListAndQuery(String type, String query);

}
