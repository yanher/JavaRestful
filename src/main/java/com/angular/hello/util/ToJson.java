package com.angular.hello.util;

import java.sql.ResultSet;

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
}
