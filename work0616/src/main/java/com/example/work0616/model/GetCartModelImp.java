package com.example.work0616.model;

import com.example.work0616.bean.DeleteCartBean;
import com.example.work0616.bean.GetCartBean;
import com.example.work0616.presenter.GetCartPresenterImp;
import com.example.work0616.utils.RetrofitUtils;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by john on 2018/6/16.
 */

public class GetCartModelImp implements GetCartModel{
    private GetCartPresenterImp presenterImp;

    public GetCartModelImp(GetCartPresenterImp presenterImp) {
        this.presenterImp = presenterImp;
    }

    @Override
    public void getCart(String uid, String pid) {
        Flowable<DeleteCartBean<List<GetCartBean.DataBean>>> flowable = RetrofitUtils.getInstance().getApiService().getDatas(uid);
presenterImp.getData(flowable);
    }
}






