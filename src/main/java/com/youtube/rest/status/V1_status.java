package com.youtube.rest.status;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;
import com.youtube.dao.*;

@Path("/v1/status/")    //这里后面不能加*   route to java class
public class V1_status {
    
   private static final String api_version= "00.01.00";

   @GET
   @Produces(MediaType.TEXT_HTML)
   public String returnTitle(){
       return "<p>Java Web Service</p>";
   }
   
   @Path("/version")
   @GET
   @Produces(MediaType.TEXT_HTML)   // @Consumes return http response
   public String returnVersion(){
       return "<p>Version:</p>" + api_version;
   }
   @Path("/database")
   @GET
   @Produces(MediaType.TEXT_HTML)
   public String returnDataSourceStatus() throws Exception{
       PreparedStatement query = null;
       String myString = null;
       String returnString = null;
       Connection conn = null;
       try{
           conn=MySQLTube.mySQLConn().getConnection();
           query=conn.prepareStatement("select date_format(sysdate(),'%Y-%m-%d') DATETIME FROM DUAL");
           ResultSet rs = query.executeQuery();
           while(rs.next()){
               myString=rs.getString("DATETIME");
           }
           query.close();
           returnString="<p>Database status</p>"+
           "<p>Database Date/Time return: " +myString+"</p>";
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           if(conn!=null) conn.close();
       }
       return returnString;
   }
}
