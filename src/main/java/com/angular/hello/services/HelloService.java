package com.angular.hello.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONArray;

import com.angular.hello.beans.User;
import com.angular.hello.dao.LoginDao;
import com.sun.jersey.api.representation.Form;
import com.youtube.dao.MySQLTube;
import com.youtube.util.ToJson;

@Path("/an/")
public class HelloService {
    @Path("/loginGet")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public boolean loginGet(@QueryParam("name") String username,@QueryParam("pwd") Integer password) throws Exception{
        LoginDao dao = new LoginDao();
        return dao.searchUserGet(username, password);
    }
    
    /*@Path("/loginPost")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public boolean loginPost(MultivaluedMap<String, String> formParams) throws Exception{
        Integer name = Integer.valueOf(formParams.get("name").get(0));
        String pwd = formParams.get("pwd").get(0);
        LoginDao dao = new LoginDao();       
        return dao.searchUser(name, pwd);
    }*/
    /*@Path("/loginPost")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public boolean loginPost(@FormParam(value = "name") Integer name , @FormParam(value = "pwd") String pwd) throws Exception{
        LoginDao dao = new LoginDao();       
        return dao.searchUser(name, pwd);
    }*/
    /*@Path("/loginPost")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)  //这种方法得到的是以{"name":"111","password":"111"}为key ，以[]为value ， 的map ， map的名字叫form ； 
 //   @Consumes(MediaType.APPLICATION_JSON)           //用这种格式接收，如果接收参数定义为一个字符串，就会接收到一个 {"name":"111","password":"111"} 字符串
    @Produces(MediaType.APPLICATION_JSON)
  //  public String loginPost(@QueryParam(value = "name") String name , @QueryParam(value = "pwd") String pwd) throws Exception{
    public String loginPost(Form form) throws Exception{ 
        form.keySet().toArray()[0];
        form.get("pwd");
        LoginDao dao = new LoginDao();       
        return dao.searchUserPost(Integer.valueOf(form.get("name").get(0)), form.get("pwd").get(1));
    }*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/loginPost")
    public String loginPost(User user) throws Exception{
        LoginDao dao = new LoginDao();
        return dao.searchUserPost(user.getName(),user.getPwd());
    }
}
