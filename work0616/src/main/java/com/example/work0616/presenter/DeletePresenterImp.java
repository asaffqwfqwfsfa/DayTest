package com.example.work0616.presenter;

import com.example.work0616.bean.DeleteCartBean;
import com.example.work0616.model.DeleteModelImp;
import com.example.work0616.view.DeleteCartView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by john on 2018/6/16.
 */

public class DeletePresenterImp implements DeletePresenter{
    private DeleteCartView view;
    private DisposableSubscriber subscriber2;

    public void attach(DeleteCartView view) {
        this.view = view;
    }

    public void detach() {
        if (view != null) {
            view = null;
        }

        if (!subscriber2.isDisposed()){
            subscriber2.dispose();
        }
    }

    @Override
    public void getDelete(String uid,String pid) {
        DeleteModelImp model = new DeleteModelImp(this);
        model.getDelete(uid,pid);
    }



    public void getDel(Flowable<DeleteCartBean> delFlowable) {
        subscriber2 = delFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<DeleteCartBean>() {
                    @Override
                    public void onNext(DeleteCartBean deleteCartBean) {
                        if (deleteCartBean != null) {
                            view.delSuccess(deleteCartBean);

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
}