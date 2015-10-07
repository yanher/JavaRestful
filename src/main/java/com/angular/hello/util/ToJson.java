package com.angular.hello.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

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
         Field[] fields = bean.getClass().getDeclaredFields();
         for(Field field : fields){
             if(field.getGenericType().getClass().getName().equalsIgnoreCase("String.class")){
                 obj.put(field.getName(), bean.getClass().getDeclaredMethod("get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1, field.getName().length())+"()", null).invoke(bean, null));
             }else if(field.getGenericType().getClass() == Class.forName("String.class")){
                 obj.put(field.getName(),bean.getClass().getDeclaredMethod("get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1, field.getName().length())+"()", null).invoke(bean, null));
             }             
         }
         json.put(obj);
     }
     return json;
 }
}
