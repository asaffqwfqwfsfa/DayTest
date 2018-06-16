package com.example.work0616.widgt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2018/6/16.
 */

public class FlowLayout extends ViewGroup{
    private List<List<View>> childList=new ArrayList<>();
    private List<Integer> integerList=new ArrayList<>();
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth=MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight=MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight=MeasureSpec.getMode(heightMeasureSpec);
        int width=0;
        int height=0;
        int lineWidth=0;
        int lineHeight=0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            measureChild(view,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams layoutParams= (MarginLayoutParams) getLayoutParams();
            int viewWidth = view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int viewHeight=view.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
            if(lineWidth+viewWidth > sizeWidth){
                width=Math.max(width,lineWidth);
                lineWidth=viewWidth;
                height+=lineHeight;
                lineHeight=viewHeight;
            }else{
                lineWidth+=viewWidth;
                lineHeight=Math.max(width,lineWidth);

            }
            if (i==childCount-1){
                width=Math.max(width,lineWidth);
                height+=lineHeight;
            }
        }
        setMeasuredDimension(modeWidth==MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight==MeasureSpec.EXACTLY ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    childList.clear();
    integerList.clear();
    int width=getWidth();
    int lineWidth=0;
    int lineHeight=0;
    List<View> views=new ArrayList<View>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            MarginLayoutParams layoutParams= (MarginLayoutParams) view.getLayoutParams();
            int childWidth=view.getMeasuredWidth();
            int childHeight=view.getMeasuredHeight();
            if (childWidth+lineWidth+layoutParams.leftMargin+layoutParams.rightMargin>width){
                integerList.add(lineHeight);
                childList.add(views);
                lineWidth=0;
                lineHeight=childHeight+layoutParams.topMargin+layoutParams.bottomMargin;
                views=new ArrayList<>();

            }
            lineWidth+=childWidth+layoutParams.leftMargin+layoutParams.rightMargin;
            lineHeight=Math.max(lineHeight,childHeight+layoutParams.topMargin+layoutParams.bottomMargin);
            views.add(view);
        }
        integerList.add(lineHeight);
        childList.add(views);
        int left=0;
        int top=0;
        int lineCount=childList.size();
        for (int i = 0; i < lineCount; i++) {
            views=childList.get(i);
            lineHeight=integerList.get(i);
            for (int j = 0; j < views.size(); j++) {
                View view=views.get(j);
                if (view.getVisibility()==View.GONE){
                    continue;
                }
                MarginLayoutParams layoutParams= (MarginLayoutParams) view.getLayoutParams();
                int jl=left+layoutParams.leftMargin;
                int jt=top+layoutParams.topMargin;
                int jr=jl+view.getMeasuredWidth();
                int jb=jt+view.getMeasuredHeight();
                view.layout(jl,jt,jr,jb);
                left+=view.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.topMargin;

            }
            left=0;
            top+=lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
    
}
