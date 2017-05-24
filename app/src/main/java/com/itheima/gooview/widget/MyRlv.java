package com.itheima.gooview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ============================================================
 * Copyright：JackChan和他的朋友们有限公司版权所有 (c) 2017
 * Author：   JackChan
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChan1999
 * GitBook：  https://www.gitbook.com/@alleniverson
 * CSDN博客： http://blog.csdn.net/axi295309066
 * 个人博客： https://jackchan1999.github.io/
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：GooView
 * Package_Name：com.itheima.gooview
 * Version：1.0
 * time：2017/5/24 23:02
 * des ：自定义粘性控件
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/24 23:02
 * updateDes：${TODO}
 * ============================================================
 */

public class MyRlv extends RecyclerView {
	public MyRlv(Context context) {
		super(context);
	}

	public MyRlv(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public MyRlv(Context context, @Nullable AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		boolean intercept = super.onInterceptTouchEvent(e);
		//Log.i("test","intercept:"+intercept);
		return intercept;
	}
}
