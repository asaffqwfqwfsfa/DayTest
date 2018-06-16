package com.example.work0616.presenter;

import com.example.work0616.bean.DeleteCartBean;
import com.example.work0616.bean.GetCartBean;
import com.example.work0616.model.GetCartModelImp;
import com.example.work0616.view.GerCartView;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by john on 2018/6/16.
 */

public class GetCartPresenterImp implements GetCartPresenter{
    private GerCartView view;
    private DisposableSubscriber subscriber1;


    public void attach(GerCartView view) {
        this.view = view;
    }

    public void detach() {
        if (view != null) {
            view = null;
        }
        if (!subscriber1.isDisposed()){
            subscriber1.dispose();
        }

    }



    public void getData(Flowable<DeleteCartBean<List<GetCartBean.DataBean>>> flowable) {
        subscriber1 = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<DeleteCartBean<List<GetCartBean.DataBean>>>() {
                    @Override
                    public void onNext(DeleteCartBean<List<GetCartBean.DataBean>> deleteCartBean) {
                        if (deleteCartBean != null) {
                            List<GetCartBean.DataBean> list = deleteCartBean.getData();
                            if (list != null) {
                                view.success(list);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getCart(String uid, String pid) {
        GetCartModelImp model = new GetCartModelImp(this);
   model.getCart(uid,pid);
    }
}





