package com.org.finalmvp.view;

public interface BaseView {
	/**
	 * ����id֪ͨ���ݸı�,�Ӷ����в������ݱ仯
	 * @param dataId
	 */
	void onDataChanage(int dataId);
	/**
	 * ֪ͨ��ʲô
	 * @param tag
	 */
	void onChanageUi(int tag,Object msg);
	
}