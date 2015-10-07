/*package com.springjpa.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angular.hello.util.ToJson;
import com.springjpa.beans.FirstBean;
import com.springjpa.dao.IBaseDao;
import com.springjpa.dao.RestfulDao;
import com.springjpa.services.FirstRestfulService;
@Service("firstRestfulService")
@Transactional
public class FirstRestfulServiceImpl implements FirstRestfulService {
    
    
    @Resource
    private RestfulDao restfulDao;
    
    @Override
    @Path("/query")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public String query() {        
        return restfulDao.query();
    }


}
*/