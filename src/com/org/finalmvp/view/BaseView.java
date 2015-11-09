package com.org.finalmvp.view;

public interface BaseView {
	void onDataChanage();//通知数据改变
	/**
	 * 通知做什么
	 * @param tag
	 */
	void onChanageUi(int tag);
	
}