package com.org.finalmvp.presenter;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

import com.org.finalmvp.model.BaseModel;
import com.org.finalmvp.model.OnModelCallBackListener;
import com.org.finalmvp.view.BaseView;

/**
 * presenter����,����һЩ��������,�磺����ע�룬�����޸ģ�����model��
 * ����presenter����̳д���
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
	 * ��ʼ��model
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
	 * ֪ͨView�����Լ��޸�
	 */
	public void notifyViewDataChanage() {
		Message msg=handler.obtainMessage();
		msg.what=WHAT_DATACHANAGE;
		handler.sendMessage(msg);
	}
	
	/**
	 * ֱ���޸�View��ui��ͨ��tag��Ӧ(һ���õĺ��٣��������)
	 * @param tag
	 */
	public void chanageViewUi(int tag) {
		Message msg=handler.obtainMessage();
		msg.what=WHAT_CHANAGEUI;
		msg.arg1=tag;
		handler.sendMessage(msg);
	}
	
	/**
	 * ���øı��ֵ
	 */
	public void setDataChanage(Object data) {
		setDataChanage(0, data);
	}
	/**
	 * ͨ������ע��ķ���  ��view���ֶθ�ֵ,Ȼ��ֱ��֪ͨView�ı�ui
	 * @param id
	 * @param data
	 */
	public void setDataChanage(int id,Object data){
		DataUtils.dataChanage(view,id, data);
		notifyViewDataChanage();
	}
	
	/**
	 * һ����������಻��Ҫֱ�Ӳ���BaseView<br/>
	 * ��ȡ��BaseView
	 * @return
	 */
	public BaseView getBaseView() {
		return view;
	}
	/**
	 * һ����������಻��Ҫֱ�Ӳ���BaseModel<br/>
	 * ��ȡ��BaseModel
	 * @return
	 */
	public BaseModel getBaseModel(){
		return model;
	}
	
	
	/**
	 * handler����,������첽�������Զ�ת�����߳�ȥ����ui
	 * @author User
	 */
	private static class MyHandler extends Handler{
		/**
		 * ͨ�������ã���ֹ�ڴ�й¶
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