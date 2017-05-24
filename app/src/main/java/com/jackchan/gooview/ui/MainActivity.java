package com.jackchan.gooview.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jackchan.gooview.R;
import com.jackchan.gooview.adapter.MsgAdapter;
import com.jackchan.gooview.bean.Msg;
import com.jackchan.gooview.widget.RecycleViewDivider;

import java.util.ArrayList;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Msg> msgList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            msgList.add(new Msg("标题" + i, i));
        }
        RecyclerView rlv = (RecyclerView) findViewById(R.id.rlv);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,1, Color.DKGRAY));
        rlv.hasFixedSize();
        rlv.setAdapter(new MsgAdapter(msgList, this));
        //		GooView gooView= (GooView) findViewById(R.id.goo_view);
        //		gooView.setText("6");
        //		gooView.initGooViewPosition(300,700);
    }
}
