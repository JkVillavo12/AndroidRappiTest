package jkvillavo.co.com.rappiandroidtest.localdb.manager;

import android.arch.persistence.room.Room;
import android.content.Context;

import jkvillavo.co.com.rappiandroidtest.localdb.RoomMovieDatabase;
import jkvillavo.co.com.rappiandroidtest.localdb.model.RoomMovieDetail;

/**
 * Created by JkVillavo12Col on 3/03/18.
 */

public class RoomMovieDetailManager {

    /**
     * Insertamos el deatlle de una pelicula
     *
     * @param applicationContext
     * @param roomMovieDetail
     */
    public static void insertMovieDetail(Context applicationContext, RoomMovieDetail roomMovieDetail) {

        RoomMovieDatabase db;
        db = Room.databaseBuilder(applicationContext,
                RoomMovieDatabase.class, RoomMovieDatabase.DB_NAME).fallbackToDestructiveMigration().build();
        db.roomMovieDetailDao().insertMovieDetail(roomMovieDetail);
        db.close();
    }

    public static RoomMovieDetail loadRoomMovieDetail(Context applicationContext, int id) {

        RoomMovieDetail roomMovieDetailOut = null;

        RoomMovieDatabase db;
        db = Room.databaseBuilder(applicationContext,
                RoomMovieDatabase.class, RoomMovieDatabase.DB_NAME).fallbackToDestructiveMigration().build();
        roomMovieDetailOut = db.roomMovieDetailDao().loadMovieDetailById(id);
        db.close();
        return roomMovieDetailOut;

    }
}
