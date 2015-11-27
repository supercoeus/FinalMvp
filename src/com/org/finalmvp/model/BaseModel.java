package com.org.finalmvp.model;

public abstract class BaseModel {
	private OnModelCallBackListener listener;
	public void setOnModelCallBackListener(OnModelCallBackListener listener) {
		this.listener=listener;
	}
	
	
	public void chanageData(int tag,Object data) {
		if(listener!=null){
			listener.onCallBack(tag,data);
		}
	}
	public void error(int tag,String msg){
		if(listener!=null){
			listener.onErrorBack(tag, msg);
		}
	}
	
	public abstract void loadData(int tag,Object msg);
	
}