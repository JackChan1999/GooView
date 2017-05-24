package com.itheima.gooview.bean;

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

public class Msg {
	public String title;
	public int unReadMsgCount;

	public Msg(String title, int unReadMsgCount) {
		this.title = title;
		this.unReadMsgCount = unReadMsgCount;
	}
}
