package jkvillavo.co.com.rappiandroidtest.rest;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JkVillavo12Col on 2/03/18.
 */

public class ApiClient {

    public static final String APIV3 = "f6f22d6270ecc338f31fb3771cc6b13e";
    public static final String APIV4 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNmYyMmQ2MjcwZWNjMzM4ZjMxZmIzNzcxY2M2YjEzZSIsInN1YiI6IjVhOTg3ZGVhYzNhMzY4MDcxZTAwNzEyMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.p1Z99a73_7oZIfSK4ht4z0wAIjD0v_fc7tZqJlZbEoM";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    public static final String BASE_URL = "http://api.themoviedb.org/3/";

    private static Retrofit retrofit = null;
    private static File httpCacheDirectory;
    private static int cacheSize = 10 * 1024 * 1024; // 10 MB
    private static Cache cache;

    private static OkHttpClient okHttpClient;

    public static Retrofit getClient(Context context) {

        httpCacheDirectory = new File(context.getCacheDir(), "responses");
        cache = new Cache(context.getCacheDir(), cacheSize);
        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
