package com.org.finalmvp.presenter;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

import com.org.finalmvp.model.BaseModel;
import com.org.finalmvp.model.OnModelCallBackListener;
import com.org.finalmvp.view.BaseView;

/**
 * presenter基类,做了一些基本操作,如：数据注入，数据修改，请求model等
 * 所有presenter必须继承此类
 * @author hld
 */
public abstract class BasePresenter implements OnModelCallBackListener{
	private static final int WHAT_CHANAGEUI=1;
	private static final int WHAT_DATACHANAGE=2;
	private static final int WHAT_MODELCALLBACK=3;
	private static final int WHAT_MODELCALLERRBACK=4;
	
	private BaseView view;
	private BaseModel model;
	private MyHandler handler;
	public BasePresenter(BaseView view) {
		this.view=view;
		handler=new MyHandler(this);
		model=initModel();
		model.setOnModelCallBackListener(this);
	}
	
	/**
	 * 初始化model
	 * @return
	 */
	public abstract BaseModel initModel();
	
	public void loadData(int tag,Object msg){
		model.loadData(tag, msg);
	}
	
	
	@Override
	public void onCallBack(int tag,Object obj) {
		Message msg=handler.obtainMessage();
		msg.what=WHAT_MODELCALLBACK;
		msg.obj=obj;
		msg.arg1=tag;
		handler.sendMessage(msg);
	}
	@Override
	public void onErrorBack(int tag, String msgstr) {
		Message msg=handler.obtainMessage();
		msg.what=WHAT_MODELCALLERRBACK;
		msg.obj=msgstr;
		msg.arg1=tag;
		handler.sendMessage(msg);
	}
	
	/**
	 * 
	 * @param obj
	 */
	public abstract void onModelCallBack(int tag,Object obj);
	
	public abstract void onModelErrorBack(int tag,String msg);
	
	
	/**
	 * 通知View数据以及修改
	 */
	public void notifyViewDataChanage() {
		Message msg=handler.obtainMessage();
		msg.what=WHAT_DATACHANAGE;
		handler.sendMessage(msg);
	}
	
	/**
	 * 直接修改View的ui，通过tag对应(一般用的很少，特殊情况)
	 * @param tag
	 */
	public void chanageViewUi(int tag) {
		Message msg=handler.obtainMessage();
		msg.what=WHAT_CHANAGEUI;
		msg.arg1=tag;
		handler.sendMessage(msg);
	}
	
	/**
	 * 设置改变的值
	 */
	public void setDataChanage(Object data) {
		setDataChanage(0, data);
	}
	/**
	 * 通过依赖注入的方法  给view的字段赋值,然后直接通知View改变ui
	 * @param id
	 * @param data
	 */
	public void setDataChanage(int id,Object data){
		DataUtils.dataChanage(view,id, data);
		notifyViewDataChanage();
	}
	
	/**
	 * 一般情况是子类不需要直接操作BaseView<br/>
	 * 获取到BaseView
	 * @return
	 */
	public BaseView getBaseView() {
		return view;
	}
	/**
	 * 一般情况是子类不需要直接操作BaseModel<br/>
	 * 获取到BaseModel
	 * @return
	 */
	public BaseModel getBaseModel(){
		return model;
	}
	
	
	/**
	 * handler机制,如果有异步处理则自动转到主线程去操作ui
	 * @author User
	 */
	private static class MyHandler extends Handler{
		/**
		 * 通过弱引用，防止内存泄露
		 */
		private WeakReference<BasePresenter> wr;
		public MyHandler(BasePresenter bp) {
			wr=new WeakReference<BasePresenter>(bp);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			BasePresenter bp=wr.get();
			switch (msg.what) {
			case WHAT_CHANAGEUI:
				bp.view.onChanageUi(msg.arg1);
				break;
			case WHAT_DATACHANAGE:
				bp.view.onDataChanage();
				break;
			case WHAT_MODELCALLBACK:
				bp.onModelCallBack(msg.arg1,msg.obj);
				break;
			case WHAT_MODELCALLERRBACK:
				bp.onModelErrorBack(msg.arg1,msg.obj.toString());
				break;
			}
		}
	}


}