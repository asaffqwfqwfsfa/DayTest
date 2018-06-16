package com.example.work0616;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.work0616.adapter.ElvAdapter;
import com.example.work0616.bean.DeleteCartBean;
import com.example.work0616.bean.GetCartBean;
import com.example.work0616.bean.MessageEvent;
import com.example.work0616.bean.PriceCount;
import com.example.work0616.bean.SomeId;
import com.example.work0616.presenter.DeletePresenterImp;
import com.example.work0616.presenter.GetCartPresenterImp;
import com.example.work0616.view.DeleteCartView;
import com.example.work0616.view.GerCartView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GerCartView,DeleteCartView {

    @BindView(R.id.elv)
    ExpandableListView elv;
    @BindView(R.id.checkbox2)
    CheckBox checkbox_2;
    @BindView(R.id.tv_price)
    TextView tv_Price;
    @BindView(R.id.tv_num)
    TextView tv_Num;
    private GetCartPresenterImp presenterImp;
    private DeletePresenterImp deletePresenterImp;
    private List<GetCartBean.DataBean> groupList = new ArrayList<>();
    private List<List<GetCartBean.DataBean.ListBean>> childList = new ArrayList<>();
    private String pid;
    private String uid = "71";
    private ElvAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }
    private void initView() {
        presenterImp = new GetCartPresenterImp();
        presenterImp.attach(this);
        deletePresenterImp = new DeletePresenterImp();
        deletePresenterImp.attach(this);
        adapter = new ElvAdapter(this, groupList, childList);
        elv.setAdapter(adapter);
    }
    private void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (presenterImp!=null){
            presenterImp.detach();
        }
    }

    @Override
    public void success(Object o) {
        if(o!=null){
            List<GetCartBean.DataBean> list = (List<GetCartBean.DataBean> )o;
            if(list!=null){
                groupList.addAll(list);
                for (int i = 0; i < list.size(); i++) {
                    List<GetCartBean.DataBean.ListBean> datas = list.get(i).getList();
                    childList.add(datas);
                }

                adapter.notifyDataSetChanged();
                checkbox_2.setChecked(true);

                adapter.changeAllListCbState(true);
                elv.setGroupIndicator(null);
                for (int i=0;i<groupList.size();i++){
                    elv.expandGroup(i);
                }

            }
        }
    }

    @Override
    public void faile(String msg) {

    }

    @Override
    public void delSuccess(DeleteCartBean deleteCartBean) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterImp.getCart(uid,pid);
    }
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        checkbox_2.setChecked(event.isChecked());
    }

    @Subscribe
    public void onMessageEvent(PriceCount count) {
        tv_Num.setText("结算(" + count.getCount() + ")");
        tv_Price.setText("￥"+count.getPrice() );
    }
    @Subscribe
    public void onMessageEvent(SomeId someId) {
        pid = someId.getPid();
        Log.e("zxz","我得到了pid:"+pid);
        deletePresenterImp.getDelete(uid,pid);


    }
}
