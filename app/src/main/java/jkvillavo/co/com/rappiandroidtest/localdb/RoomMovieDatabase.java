package jkvillavo.co.com.rappiandroidtest.localdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import jkvillavo.co.com.rappiandroidtest.localdb.dao.RoomMovieDao;
import jkvillavo.co.com.rappiandroidtest.localdb.dao.RoomMovieDetailDao;
import jkvillavo.co.com.rappiandroidtest.localdb.model.RoomMovie;
import jkvillavo.co.com.rappiandroidtest.localdb.model.RoomMovieDetail;

/**
 * Created by JkVillavo12Col on 3/03/18.
 */

@Database(entities = {RoomMovie.class, RoomMovieDetail.class}, version = 3)
public abstract class RoomMovieDatabase extends RoomDatabase {

    public static final String DB_NAME = "rappi-test";


    public abstract RoomMovieDao roomMovieDao();

    public abstract RoomMovieDetailDao roomMovieDetailDao();

}
