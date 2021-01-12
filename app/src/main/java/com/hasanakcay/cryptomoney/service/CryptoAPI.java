package com.hasanakcay.cryptomoney.service;

import com.hasanakcay.cryptomoney.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    @GET("prices?key=bb5dd52d601673048f565f10a7531517")
    Observable<List<CryptoModel>> getData();


    //Call<List<CryptoModel>> getData();

}
