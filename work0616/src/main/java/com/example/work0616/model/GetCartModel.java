package com.example.work0616.model;

import com.example.work0616.bean.DeleteCartBean;
import com.example.work0616.bean.GetCartBean;
import com.example.work0616.utils.RetrofitUtils;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by john on 2018/6/16.
 */

public interface GetCartModel {
    void getCart(String uid,String pid);

}
