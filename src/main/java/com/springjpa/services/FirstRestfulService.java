package com.springjpa.services;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springjpa.beans.FirstBean;
import com.springjpa.dao.RestfulDao;

@Path("/rest/")
@Service("firstRestfulService")
public class FirstRestfulService {
    @Resource
    private RestfulDao restfulDao;
    
    @Path("/query")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String query() {        
        return restfulDao.query();
    }
    
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void modify() {        
        restfulDao.merge(new FirstBean());
    }
    
    @Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void delete() {        
        restfulDao.remove(new FirstBean());
    }
    
    @Path("/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String queryById() {        
        return restfulDao.queryById(1);
    }
    
    @Path("/regist")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void regist() {        
        restfulDao.save(new FirstBean());
    }
}
