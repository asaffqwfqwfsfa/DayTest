package com.example.work0616.view;

import com.example.work0616.bean.DeleteCartBean;

/**
 * Created by john on 2018/6/16.
 */

public interface DeleteCartView {
    void success(Object o);
    void faile(String msg);
    void delSuccess(DeleteCartBean deleteCartBean);
}
