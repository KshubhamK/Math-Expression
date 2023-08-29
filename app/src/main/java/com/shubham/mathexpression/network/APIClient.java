package com.shubham.mathexpression.network;

import static com.shubham.mathexpression.network.Urls.APP_BASE_URL;

import android.app.Activity;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Activity activity) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.callTimeout(120, TimeUnit.SECONDS);
        httpClient.readTimeout(120, TimeUnit.SECONDS);
        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Response response = null;
                Request request = chain.request();
                try {
                    Request original = chain.request();

                    request = original.newBuilder()
//                            .header("X-RapidAPI-Key", RAPID_API_KEY)
//                            .header("X-RapidAPI-Host", RAPID_HOST)
                            .method(original.method(), original.body())
                            .build();
                    if (chain.request() != null) {
                        response = chain.proceed(chain.request());
                        if (response.code() == 401) {
                        }
                    }

                } catch (Exception e) {
                }
                try {
                    return chain.proceed(request);
                } catch (Exception e) {
                    if(response!=null) {
                        response.close();
                    }
                    return chain.proceed(request);
                }
            }
        });

        OkHttpClient client = httpClient.build();
        try {
            if (client != null && client.cache() != null) {
                client.cache().flush();
                client.cache().delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(APP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
