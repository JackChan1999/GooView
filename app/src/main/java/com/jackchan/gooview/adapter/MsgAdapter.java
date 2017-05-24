package com.jackchan.gooview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jackchan.gooview.R;
import com.jackchan.gooview.bean.Msg;
import com.jackchan.gooview.widget.OnGooViewTouchListener;

import java.util.List;

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

public class MsgAdapter extends Adapter<MsgAdapter.MyViewHolder> {
	private List<Msg> msgList;

	public MsgAdapter(List<Msg> msgList,Context context) {
		listener=new OnGooViewTouchListener(context);
		this.msgList = msgList;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rlv_item, parent,false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		holder.tv_title.setText(msgList.get(position).title);
		int unReadMsgCount = msgList.get(position).unReadMsgCount;
		if (unReadMsgCount == 0) {
			holder.tv_unReadMsgCount.setVisibility(View.INVISIBLE);
		} else {
			holder.tv_unReadMsgCount.setVisibility(View.VISIBLE);
			holder.tv_unReadMsgCount.setText(unReadMsgCount+"");
		}
		//监听对应控件的触摸事件
		//和重写onTouchEvent是有区别的
		//如果一个控件重写了onTouchEvent返回true,且设置触摸监听返回true,则MotionEvent交给OnTouchListener
		holder.tv_unReadMsgCount.setOnTouchListener(listener);
	}

	private OnGooViewTouchListener listener;

	@Override
	public int getItemCount() {
		return msgList.size();
	}

	public static class MyViewHolder extends RecyclerView.ViewHolder {
		public TextView tv_title;
		public TextView tv_unReadMsgCount;

		public MyViewHolder(View itemView) {
			super(itemView);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			tv_unReadMsgCount = (TextView) itemView.findViewById(R.id.tv_unReadMsgCount);
		}
	}
}
