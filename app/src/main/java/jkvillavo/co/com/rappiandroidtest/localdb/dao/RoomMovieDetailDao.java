package jkvillavo.co.com.rappiandroidtest.localdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import jkvillavo.co.com.rappiandroidtest.localdb.model.RoomMovieDetail;

/**
 * Created by JkVillavo12Col on 3/03/18.
 */

@Dao
public interface RoomMovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMovieDetail(RoomMovieDetail movie);

    @Query("SELECT * FROM RoomMovieDetail WHERE id = :id")
    public RoomMovieDetail loadMovieDetailById(int id);

}
