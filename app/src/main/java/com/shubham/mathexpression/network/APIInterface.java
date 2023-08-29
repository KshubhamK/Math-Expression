package com.shubham.mathexpression.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {
    @GET
    Call<String> callGetCharactersList(@Url String URL,
                                     @Query(value = "expr", encoded = true) String expression);
}
