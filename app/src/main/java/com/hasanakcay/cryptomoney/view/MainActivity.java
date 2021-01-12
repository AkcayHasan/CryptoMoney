package com.hasanakcay.cryptomoney.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hasanakcay.cryptomoney.R;
import com.hasanakcay.cryptomoney.adapter.CryptoRecyclerAdapter;
import com.hasanakcay.cryptomoney.model.CryptoModel;
import com.hasanakcay.cryptomoney.service.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://api.nomics.com/v1/";
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CompositeDisposable compositeDisposable;
    CryptoRecyclerAdapter cryptoRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                createRetrofit();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        createRetrofit();

    }

    public void createRetrofit(){
        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();
    }

    public void loadData() {

        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);
        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));


        /*

        Call<List<CryptoModel>> call = cryptoAPI.getData();
        //RxJava kullanılıyorsa call ile işlem yapma!! call yerine observable ile işlem yap
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()) {
                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    cryptoRecyclerAdapter = new CryptoRecyclerAdapter(cryptoModels);
                    recyclerView.setAdapter(cryptoRecyclerAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

         */
    }

    private void handleResponse(List<CryptoModel> cryptoModelList) {
        cryptoModels = new ArrayList<>(cryptoModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        cryptoRecyclerAdapter = new CryptoRecyclerAdapter(cryptoModels);
        recyclerView.setAdapter(cryptoRecyclerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}

//https://api.nomics.com/v1/prices?key=bb5dd52d601673048f565f10a7531517