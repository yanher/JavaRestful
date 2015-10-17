package com.springjpa.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.angular.hello.util.ToJson;
import com.springjpa.beans.FirstBean;
import com.springjpa.dao.impl.BaseDaoImpl;

@Repository("restfulDao")
public class RestfulDao extends BaseDaoImpl<FirstBean,Integer> {

    public <T> String query(){
        
        List<T> resultList = (List<T>)super.queryAll("id");

        ToJson convert = new ToJson();
        JSONArray json = new JSONArray();
        try {
            json = convert.toJSONArray(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
     }

     public <T> String queryById(Integer id){
        
        T bean = (T) super.queryById(id);
        ToJson convert = new ToJson();
        JSONArray json = new JSONArray();
        ArrayList<T> list = new ArrayList<T>();
        list.add(bean);
        try {
            json = convert.toJSONArray(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
