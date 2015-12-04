package com.org.finalmvp.presenter;

import java.lang.reflect.Field;
import java.util.List;

import com.org.finalmvp.ViewData;
import com.org.finalmvp.utils.LogUtils;
import com.org.finalmvp.view.BaseView;
/**
 * ����ע�������
 * @author hld
 */
class DataUtils {
	/**
	 * data����ע�뵽View���ֶζ�����
	 * @param view ע��Ķ����������BaseView
	 * @param data ��Ҫע�������
	 */
	public static void dataChanage(BaseView view,Object data){
		dataChanage(view, 0, data);
	}
	/**
	 * data����ע�뵽View���ֶζ�����
	 * @param view ע��Ķ����������BaseView
	 * @id id ��ʾע����д��idֵ,ͨ����ͬ��id��������ע������
	 * @param data ��Ҫע�������
	 */
	public static void dataChanage(BaseView view,int id,Object data){
		dataChanage(view, id, data, false);
	}
	public static void dataChanage(BaseView view,int id,Object data,boolean isAddList){
		Class<?> cls = view.getClass();
		Field fds[]=cls.getDeclaredFields();
		for(Field f:fds){
			ViewData an = f.getAnnotation(ViewData.class);
			if(an!=null){
				if(id==an.id()){
					try {
//						f.set(view, data);
						chanageDatas(view, f, data,isAddList);
					} catch (IllegalAccessException e) {
						if(e!=null){
							LogUtils.e("�뽫��������Ϊpublic  "+e.getMessage()+"  "+e.getLocalizedMessage());
							e.printStackTrace();
						}
					} catch (IllegalArgumentException e) {
						if(e!=null){
							LogUtils.e(e.getMessage()+"  "+e.getLocalizedMessage());
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public static void addListDatas(BaseView view,int id,Object data){
		dataChanage(view, id, data,true);
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void chanageDatas(BaseView view,Field f,Object data,boolean isAddList) throws IllegalAccessException, IllegalArgumentException {
		Class<?> type=f.getType();
		if(type.equals(String.class)||type.equals(Integer.class)||type.equals(Float.class)||type.equals(Double.class)
				||type.equals(Boolean.class)||type.equals(Long.class)||type.equals(Short.class)){
			LogUtils.d("������������ typeName:"+type.getName());
			f.set(view, data);
		}else if(type.equals(List.class)&& data instanceof List){
			LogUtils.d("list���� typeName:"+type.getName());
			List<?> list = (List<?>) f.get(view);
			if(list==null){
				f.set(view, data);
			}else{
				if(!isAddList){
					list.clear();
				}
				list.addAll((List)data);
			}
		}else if(data!=null&&type.equals(data.getClass())){
			LogUtils.d("bean���� typeName:"+type.getName());
			f.set(view, data);
		}else if(data==null){
			f.set(view, null);
		}
	}
	
//	private void setBeanData(Object obj,Object data) {
//		Method[]ms = obj.getClass().getMethods();
//		for(Method m:ms){
//			String name=m.getName();
//			if(name.startsWith("set")){
//				String endMethod="get"+name.replace("set", "");
//				try {
//					Method dm=data.getClass().getMethod(endMethod, null);
//					if(dm!=null){
//						m.invoke(obj, dm.invoke(data, null));
//					}
//				} catch (NoSuchMethodException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	
}