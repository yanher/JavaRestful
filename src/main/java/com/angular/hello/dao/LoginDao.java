package com.angular.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;

import com.angular.hello.util.ToJson;

public class LoginDao extends BaseDao{
    public String searchUserPost(String username , Integer password) throws SQLException{
        PreparedStatement query = null;
        Connection conn = null;
        try{
            conn=angularData.getConnection();
            query=conn.prepareStatement("select * from angularuser where userid = ? and password = ? ;");
            query.setString(1, username);
            query.setInt(2, password);
            ResultSet rs = query.executeQuery();
         //   rs.next();
            rs.last();
            if(rs.getRow()==0){
                return "";
            }else{
                rs.beforeFirst();
                ToJson convert = new ToJson();
                JSONArray json = new JSONArray();
                json = convert.toJSONArray(rs);
                query.close();
                return json.toString();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null) conn.close();
        }
        return "";
    }
    
    public boolean searchUserGet(String username , Integer password) throws SQLException{
        PreparedStatement query = null;
        Connection conn = null;
        try{
            conn=angularData.getConnection();
            query=conn.prepareStatement("select * from angularuser where userid = ? and password = ? ;");
            query.setString(1, username);
            query.setInt(2, password);
            ResultSet rs = query.executeQuery();
            rs.next();
            if(rs.getRow()==0){
                return false;
            }else{
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null) conn.close();
        }
        return false;
    }
}
