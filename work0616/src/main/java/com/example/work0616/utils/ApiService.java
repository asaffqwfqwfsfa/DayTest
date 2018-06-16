package com.example.work0616.utils;

import com.example.work0616.bean.DeleteCartBean;
import com.example.work0616.bean.GetCartBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by john on 2018/6/16.
 */

public interface ApiService {
    @GET("product/getCarts")
    Flowable<DeleteCartBean<List<GetCartBean.DataBean>>> getDatas(@Query("uid") String uid);
    @GET("product/deleteCart")
    Flowable<DeleteCartBean> deleteData(@Query("uid") String uid, @Query("pid") String pid);
}











