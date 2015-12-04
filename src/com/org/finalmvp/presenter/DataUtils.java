package com.org.finalmvp.presenter;

import java.lang.reflect.Field;
import java.util.List;

import com.org.finalmvp.ViewData;
import com.org.finalmvp.utils.LogUtils;
import com.org.finalmvp.view.BaseView;
/**
 * 依赖注入操作类
 * @author hld
 */
class DataUtils {
	/**
	 * data数据注入到View的字段对象中
	 * @param view 注入的对象必须是你BaseView
	 * @param data 需要注入的数据
	 */
	public static void dataChanage(BaseView view,Object data){
		dataChanage(view, 0, data);
	}
	/**
	 * data数据注入到View的字段对象中
	 * @param view 注入的对象必须是你BaseView
	 * @id id 表示注解所写的id值,通过不同的id可以区别注入数据
	 * @param data 需要注入的数据
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
							LogUtils.e("请将属性设置为public  "+e.getMessage()+"  "+e.getLocalizedMessage());
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
			LogUtils.d("基本数据类型 typeName:"+type.getName());
			f.set(view, data);
		}else if(type.equals(List.class)&& data instanceof List){
			LogUtils.d("list类型 typeName:"+type.getName());
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
			LogUtils.d("bean类型 typeName:"+type.getName());
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