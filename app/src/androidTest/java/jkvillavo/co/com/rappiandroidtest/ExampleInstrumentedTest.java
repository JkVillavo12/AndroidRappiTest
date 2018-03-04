package jkvillavo.co.com.rappiandroidtest;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jkvillavo.co.com.rappiandroidtest.commons.Utilidades;
import jkvillavo.co.com.rappiandroidtest.localdb.dao.RoomMovieDao;
import jkvillavo.co.com.rappiandroidtest.localdb.RoomMovieDatabase;
import jkvillavo.co.com.rappiandroidtest.localdb.model.RoomMovie;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private RoomMovieDao roomMovieDao;
    private RoomMovieDatabase mDb;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("jkvillavo.co.com.rappiandroidtest", appContext.getPackageName());
    }

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, RoomMovieDatabase.class).build();
        roomMovieDao = mDb.roomMovieDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        RoomMovie roomMovie = new RoomMovie();
        roomMovie.setId(12);
        roomMovie.setType("12");
        roomMovieDao.insertMovies(roomMovie);
        List<RoomMovie> byName = Arrays.asList(roomMovieDao.loadMoviesByTypeList("12"));
        assertEquals(String.valueOf(byName.get(0).getId()), String.valueOf(roomMovie.getId()));
    }

    @Test
    public void networkConnection() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        boolean respuesta = Utilidades.haveNetworkConnection(context);
        assertEquals(true, respuesta);
    }

}
