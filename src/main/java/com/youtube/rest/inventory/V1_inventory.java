package com.youtube.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.MySQLTube;
import com.youtube.util.ToJson;


@Path("/v1/inv/")
public class V1_inventory {
    @Path("/inventory")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String returnNewRestTable() throws Exception{
        PreparedStatement query = null;
        String myString = null;
        String returnString = null;
        Connection conn = null;
        try{
            conn=MySQLTube.mySQLConn().getConnection();
            query=conn.prepareStatement("select * from resttest ");
            ResultSet rs = query.executeQuery();
            
            ToJson convert = new ToJson();
            JSONArray json = new JSONArray();
            json = convert.toJSONArray(rs);
            query.close();
            returnString = json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null) conn.close();
        }
        return returnString;
    }
    
    @Path("/inventory2")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnNewRestTable2() throws Exception{
        PreparedStatement query = null;
        String myString = null;
        String returnString = null;
        Connection conn = null;
        Response rb = null;
        try{
            conn=MySQLTube.mySQLConn().getConnection();
            query=conn.prepareStatement("select * from resttest ");
            ResultSet rs = query.executeQuery();
            
            ToJson convert = new ToJson();
            JSONArray json = new JSONArray();
            json = convert.toJSONArray(rs);
            query.close();
            returnString = json.toString();
            rb = Response.ok(returnString).build();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null) conn.close();
        }
        return rb;
    }
}
