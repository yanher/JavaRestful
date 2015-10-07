package com.youtube.dao;
import javax.naming.*;
import javax.sql.*;
public class MySQLTube {
   
    private static DataSource mySQL = null;
    private static Context context = null;
    
    public static DataSource mySQLConn() throws Exception{
        if(mySQL!=null){
            return mySQL;
        }
        try{
            if(context==null){
                context=new InitialContext();
            }
            mySQL = (DataSource)context.lookup("java:comp/env/WFISDB");
        }catch(Exception e){
            e.printStackTrace();
        }
        return mySQL;
    }
}
