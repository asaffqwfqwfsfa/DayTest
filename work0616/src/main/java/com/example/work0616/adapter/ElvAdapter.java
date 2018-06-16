package com.example.work0616.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.work0616.R;
import com.example.work0616.bean.GetCartBean;
import com.example.work0616.bean.MessageEvent;
import com.example.work0616.bean.PriceCount;
import com.example.work0616.bean.SomeId;
import com.example.work0616.widgt.AddSubtractView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by john on 2018/6/16.
 */

public class ElvAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<GetCartBean.DataBean> groupList;
    private List<List<GetCartBean.DataBean.ListBean>> childList;
    public ElvAdapter(Context context, List<GetCartBean.DataBean> groupList, List<List<GetCartBean.DataBean.ListBean>> childList) {
        this.context =context;
        this.groupList = groupList;
        this.childList = childList;
    }
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder holder;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = convertView.inflate(context, R.layout.group_item, null);
            holder.cbGroup = convertView.findViewById(R.id.cb_parent);
            holder.tv_number = convertView.findViewById(R.id.tv_number);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        holder.cbGroup.setChecked(groupList.get(groupPosition).isChecked());
        holder.tv_number.setText(groupList.get(groupPosition).getSellerName());
        //一级checkbox
        holder.cbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetCartBean.DataBean dataBean = groupList.get(groupPosition);
                dataBean.setChecked(holder.cbGroup.isChecked());
                changeChildCbState(groupPosition, holder.cbGroup.isChecked());
                EventBus.getDefault().post(compute());
                changeAllCbState(isAllGroupCbSelected());
                notifyDataSetChanged();
            }


        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        final ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = convertView.inflate(context,R.layout.child_item, null);
            holder.cbChild = convertView.findViewById(R.id.cb_child);
            holder.tv_tel = convertView.findViewById(R.id.tv_tel);

            holder.draweeView = (SimpleDraweeView) convertView.findViewById(R.id.my_image_view);

            holder.tv_price = convertView.findViewById(R.id.tv_pri);
            holder.tv_del = convertView.findViewById(R.id.tv_del);

            holder.asv = convertView.findViewById(R.id.adv_main);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        final GetCartBean.DataBean.ListBean listBean = childList.get(groupPosition).get(childPosition);

        holder.cbChild.setChecked(listBean.isChecked());
        holder.tv_tel.setText(listBean.getTitle());

        holder.tv_price.setText("￥"+listBean.getPrice() );
        holder.asv.setNumber(listBean.getNum());
        String images = listBean.getImages().trim();
        String[] split = images.split("[|]");
        holder.draweeView.setImageURI(split[0]);

        holder.cbChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean.setChecked(holder.cbChild.isChecked());
                PriceCount priceCount = compute();
                EventBus.getDefault().post(priceCount);

                if (holder.cbChild.isChecked()) {
                    if (isAllChildCbSelected(groupPosition)) {
                        changGroupCbState(groupPosition, true);
                        changeAllCbState(isAllGroupCbSelected());
                    }
                } else {
                    changGroupCbState(groupPosition, false);
                    changeAllCbState(isAllGroupCbSelected());
                }
                notifyDataSetChanged();
            }


        });
        holder.asv.setOnAddDelClickListener(new AddSubtractView.OnAddDelClickListener() {
            @Override
            public void onAddClick(View v) {

                int origin = holder.asv.getNumber();
                origin++;
                int num = listBean.getNum();
                num++;
                holder.asv.setNumber(num);
                listBean.setNum(num);
                if (holder.cbChild.isChecked()) {
                    EventBus.getDefault().post(compute());
                }
            }

            @Override
            public void onDelClick(View v) {
                int origin = holder.asv.getNumber();
                origin--;
                if (origin == 0) {
                    Toast.makeText(context,"最小数量为1",Toast.LENGTH_SHORT).show();
                    return ;
                }
                holder.asv.setNumber(origin);
                listBean.setNum(origin);
                if (holder.cbChild.isChecked()) {

                    EventBus.getDefault().post(compute());
                }

            }
        });

        holder.tv_del.setOnClickListener(new View.OnClickListener() {

            private AlertDialog dialog;

            @Override
            public void onClick(View v) {
                final List<GetCartBean.DataBean.ListBean> listBeans = childList.get(groupPosition);


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("确认是否删除？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ii) {
                        GetCartBean.DataBean.ListBean remove = listBeans.remove(childPosition);
                        if (listBeans.size() == 0) {
                            childList.remove(groupPosition);
                            groupList.remove(groupPosition);
                            int pid = listBeans.get(groupPosition).getPid();
                            SomeId someId = new SomeId();
                            someId.setPid(pid+"");
                            EventBus.getDefault().post(someId);
                        }
                        EventBus.getDefault().post(compute());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();

            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    class GroupViewHolder {
        CheckBox cbGroup;
        TextView tv_number;
    }

    class ChildViewHolder {
        CheckBox cbChild;
        TextView tv_tel;
        TextView tv_content;
        TextView tv_time;
        SimpleDraweeView draweeView;
        TextView tv_price;
        TextView tv_del;
        ImageView iv_del;
        ImageView iv_add;
        TextView tv_num;
        AddSubtractView asv;
    }
    private void changeAllCbState(boolean flag) {
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setChecked(flag);
        EventBus.getDefault().post(messageEvent);
    }

    private void changGroupCbState(int groupPosition, boolean flag) {
        GetCartBean.DataBean dataBean = groupList.get(groupPosition);

        dataBean.setChecked(flag);
    }


    private void changeChildCbState(int groupPosition, boolean flag) {
        List<GetCartBean.DataBean.ListBean> listBeans = childList.get(groupPosition);

        for (int i = 0; i < listBeans.size(); i++) {
            GetCartBean.DataBean.ListBean listBean = listBeans.get(i);
            listBean.setChecked(flag);
        }
    }

    private boolean isAllGroupCbSelected() {
        for (int i = 0; i < groupList.size(); i++) {
            GetCartBean.DataBean dataBean = groupList.get(i);

            if (!dataBean.isChecked()) {
                return false;
            }
        }
        return true;
    }


    private boolean isAllChildCbSelected(int groupPosition) {
        List<GetCartBean.DataBean.ListBean> listBeans = childList.get(groupPosition);

        for (int i = 0; i < listBeans.size(); i++) {
            GetCartBean.DataBean.ListBean listBean = listBeans.get(i);

            if (!listBean.isChecked()) {
                return false;
            }
        }
        return true;
    }

    private PriceCount compute() {
        int count = 0;
        int price = 0;
        for (int i = 0; i < childList.size(); i++) {
            List<GetCartBean.DataBean.ListBean> listBeans = childList.get(i);


            for (int j = 0; j < listBeans.size(); j++) {
                GetCartBean.DataBean.ListBean listBean = listBeans.get(j);

                if (listBean.isChecked()) {
                    price += listBean.getNum() * listBean.getPrice();
                    count += listBean.getNum();
                }
            }
        }
        PriceCount priceCounti = new PriceCount();
        priceCounti.setCount(count);
        priceCounti.setPrice(price);
        return priceCounti;
    }

    public void changeAllListCbState(boolean flag) {
        for (int i = 0; i < groupList.size(); i++) {
            changGroupCbState(i, flag);
            changeChildCbState(i, flag);
        }
        EventBus.getDefault().post(compute());
        notifyDataSetChanged();
    }
}

