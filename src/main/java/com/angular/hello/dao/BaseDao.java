package com.angular.hello.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BaseDao {
    protected static DataSource angularData = null;
    protected static Context context = null;
    public BaseDao(){
        try {
            this.mySQLConn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static DataSource mySQLConn() throws Exception{
        if(angularData!=null){
            return angularData;
        }
        try{
            if(context==null){
                context=new InitialContext();
            }
            angularData = (DataSource)context.lookup("java:comp/env/WFISDB");
        }catch(Exception e){
            e.printStackTrace();
        }
        return angularData;
    }
}
