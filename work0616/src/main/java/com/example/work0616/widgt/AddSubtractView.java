package com.example.work0616.widgt;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.work0616.R;

import butterknife.BindView;

/**
 * Created by john on 2018/6/16.
 */

public class AddSubtractView extends LinearLayout {

    @BindView(R.id.but_delete)
    Button butDelete;
    @BindView(R.id.et_number)
    TextView etNumber;
    @BindView(R.id.but_add)
    Button butAdd;
    private OnAddDelClickListener listener;


    public void setOnAddDelClickListener(OnAddDelClickListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }


    public interface OnAddDelClickListener {
        void onAddClick(View v);

        void onDelClick(View v);
    }

    public AddSubtractView(Context context) {
        this(context, null);
    }

    public AddSubtractView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public AddSubtractView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        View.inflate(context, R.layout.addsub_layout, this);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AddSubtraceViewStyle);
        String left_text = typedArray.getString(R.styleable.AddSubtraceViewStyle_left_text);
        String middle_text = typedArray.getString(R.styleable.AddSubtraceViewStyle_middle_text);
        String right_text = typedArray.getString(R.styleable.AddSubtraceViewStyle_right_text);
        butDelete.setText(left_text);
        butAdd.setText(right_text);
        etNumber.setText(middle_text);
        typedArray.recycle();

        butAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddClick(view);
            }
        });
        butDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelClick(view);
            }
        });
    }

    public void setNumber(int number) {
        if (number > 0) {
            etNumber.setText(number + "");
        }
    }

    public int getNumber() {
        int number = 0;
        try {
            String numberStr = etNumber.getText().toString().trim();
            number = Integer.valueOf(numberStr);
        } catch (Exception e) {
            number = 0;
        }
        return number;
    }
}