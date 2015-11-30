package com.org.finalmvp.view;

public interface BaseView {
	/**
	 * 根据id通知数据改变,从而进行部分数据变化
	 * @param dataId
	 */
	void onDataChanage(int dataId);
	/**
	 * 通知做什么
	 * @param tag
	 */
	void onChanageUi(int tag,Object msg);
	
}