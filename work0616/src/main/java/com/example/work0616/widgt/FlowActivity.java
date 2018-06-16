package com.example.work0616.widgt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.work0616.MainActivity;
import com.example.work0616.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlowActivity extends AppCompatActivity {


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.fl)
    FlowLayout fl;
    @BindView(R.id.jilu)
    TextView jilu;
    @BindView(R.id.lv)
    ListView lv;
    String names[]={
    "iPhone10手机","门后衣挂","浴室收纳架","调味料收纳盒","化妆品收纳盒"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        ButterKnife.bind(this);
        flow();
    }

    private void flow() {
        ViewGroup.MarginLayoutParams layoutParams=new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.leftMargin=5;
        layoutParams.rightMargin=5;
        layoutParams.topMargin=5;
        layoutParams.bottomMargin=5;
        for (int i = 0; i < names.length; i++) {
            TextView tv=new TextView(this);
            tv.setText(names[i]);
            tv.setTextColor(Color.BLACK);
            fl.addView(tv,layoutParams);
        }
    }

    public void delete(View view) {
    }

    public void serach(View view) {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
