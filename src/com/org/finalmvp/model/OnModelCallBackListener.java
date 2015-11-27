package com.org.finalmvp.model;

public interface OnModelCallBackListener  {
	void onCallBack(int tag,Object obj);
	void onErrorBack(int tag,String msg);
}
