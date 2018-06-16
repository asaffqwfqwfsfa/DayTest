package com.example.work0616.model;

import com.example.work0616.bean.DeleteCartBean;
import com.example.work0616.presenter.DeletePresenterImp;
import com.example.work0616.utils.RetrofitUtils;

import io.reactivex.Flowable;

/**
 * Created by john on 2018/6/16.
 */

public class DeleteModelImp implements DeleteModel{
    private DeletePresenterImp presenter;

    public DeleteModelImp(DeletePresenterImp presenter){
        this.presenter =  presenter;

    }
    @Override
    public void getDelete(String uid,String pid) {

        Flowable<DeleteCartBean> delFlowable = RetrofitUtils.getInstance().getApiService().deleteData(uid,pid);
        presenter.getDel(delFlowable);
    }
}
