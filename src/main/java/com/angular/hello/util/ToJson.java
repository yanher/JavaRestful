package com.angular.hello.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.springjpa.beans.FirstBean;
import com.springjpa.common.GenericsUtils;


public class ToJson {
 public JSONArray toJSONArray(ResultSet rs) throws Exception{
     java.sql.ResultSetMetaData rsmd = rs.getMetaData();
     JSONArray json = new JSONArray();
     while(rs.next()){
         int count =rsmd.getColumnCount();
         JSONObject obj = new JSONObject();
         for(int i=1;i<count+1;i++){
             String column_name = rsmd.getColumnName(i);
             if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
                 obj.put(column_name, rs.getString(i));
             }else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                 obj.put(column_name, rs.getInt(i));
             }
         }
         json.put(obj);
     }
     return json;
 }
 
 public <T> JSONArray toJSONArray(List<T> rs) throws Exception{
     JSONArray json = new JSONArray();
     Iterator<T> it = rs.iterator();    
     while(it.hasNext()){
         JSONObject obj = new JSONObject();
         T bean = it.next();
         //所有的泛型类型在运行时都是Object类型 
         //bean.getClass() 取到的是 jpa的“class org.apache.openjpa.enhance.com$springjpa$beans$FirstBean$pcsubclass”         
         //Class clazz1 = (Class)(bean.getClass().getGenericSuperclass());     //得到泛型父类     
         //Class clazz2 = GenericsUtils.getSuperClassGenricType((Class)(bean.getClass().getGenericSuperclass())); //firstbean没有实现如果没有实现ParameterizedType接口，所以得到object.class
         Class clazz = (Class)(bean.getClass().getSuperclass());     //得到父类       
         Field[] fields = clazz.getDeclaredFields();
         //((ParameterizedType) bean.getClass().getGenericSuperclass() instanceof )
         for(Field field : fields){
             field.setAccessible(true);
             if(field.getGenericType().toString().equalsIgnoreCase("int")){
                 obj.put(field.getName(), clazz.getDeclaredMethod("get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1, field.getName().length())).invoke(bean));
             }else if(field.getGenericType().toString().equalsIgnoreCase("class java.lang.String")){
                 obj.put(field.getName(), clazz.getDeclaredMethod("get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1, field.getName().length())).invoke(bean));
             }/*else if(field.getGenericType().getClass() == Class.forName("java.lang.String")){
                 obj.put(field.getName(), clazz.getDeclaredMethod("get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1, field.getName().length())).invoke(bean));
             } */            
         }
         json.put(obj);
     }
     return json;
 }
}
