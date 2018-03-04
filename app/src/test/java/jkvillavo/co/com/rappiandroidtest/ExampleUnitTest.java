package jkvillavo.co.com.rappiandroidtest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import jkvillavo.co.com.rappiandroidtest.commons.Utilidades;
import jkvillavo.co.com.rappiandroidtest.rest.model.moviedetail.Genre;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void test_genres() throws Exception {

        List<Genre> genreList = new ArrayList<>();
        Genre genre = new Genre();
        genre.setName("Uno");
        genreList.add(genre);
        Genre genre2 = new Genre();
        genre2.setName("Dos");
        genreList.add(genre2);

        assertEquals("Uno, Dos", Utilidades.getStringFromListGenres(genreList));
    }

}