package com.hwqgooo.jetpack.utils.http;

import com.google.gson.Gson;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private Retrofit retrofit;
    private OkHttpManager okHttpManager;

    private RetrofitManager() {
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public OkHttpManager getOkHttpManager() {
        return okHttpManager;
    }

    public <T> T create(Class<T> apiClass) {
        return this.retrofit.create(apiClass);
    }

    private RetrofitManager(Builder builder) {
        Retrofit.Builder retBuilder = new Retrofit.Builder();
        okHttpManager = builder.okHttpManager;
        retBuilder.client(okHttpManager.getHttpClient());
        retBuilder.addCallAdapterFactory(builder.rxFactory);
        retBuilder.addConverterFactory(builder.jsonFactory);
        retBuilder.baseUrl(builder.baseUrl);
        retrofit = retBuilder.build();
    }

    public static final class Builder {
        OkHttpManager okHttpManager;
        String baseUrl;
        Converter.Factory jsonFactory;
        CallAdapter.Factory rxFactory;

        public Builder() {
            jsonFactory = GsonConverterFactory.create(new Gson());
            rxFactory = RxJava2CallAdapterFactory.create();
            okHttpManager = new OkHttpManager.Builder().build();
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setJsonFactory(Converter.Factory jsonFactory) {
            this.jsonFactory = jsonFactory;
            return this;
        }

        public Builder setRxFactory(CallAdapter.Factory rxFactory) {
            this.rxFactory = rxFactory;
            return this;
        }

        public Builder setOkHttpManager(OkHttpManager okHttpManager) {
            this.okHttpManager = okHttpManager;
            return this;
        }

        public RetrofitManager build() {
            if (okHttpManager == null) {
                throw new RuntimeException("OkHttpManager不能为空");
            }
            return new RetrofitManager(this);
        }
    }
}
