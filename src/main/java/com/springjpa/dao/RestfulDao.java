package com.springjpa.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("restfulDao")
public class RestfulDao {
    @SuppressWarnings("rawtypes")
    @Resource
    private IBaseDao baseDao;
    
    @SuppressWarnings("unchecked")
    public List<Object> query(){
        return baseDao.queryAll("id");
    }
}
