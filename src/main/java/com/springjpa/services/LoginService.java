package com.springjpa.services;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springjpa.beans.Users;
import com.springjpa.dao.LoginDao;

@Path("/login/")
@Service("loginService")
public class LoginService {
    @Resource(name="loginDao")
    private LoginDao loginDao;
    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginPost(Users users) throws Exception{
        return loginDao.searchUser(users.getName(),users.getPwd());
    }
}
