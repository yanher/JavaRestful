/**
 * 
 */
package com.springjpa.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.stereotype.Repository;

import com.angular.hello.util.ToJson;
import com.springjpa.beans.Users;
import com.springjpa.common.GenericsUtils;
import com.springjpa.dao.impl.BaseDaoImpl;

/**
 * 
 */
@Repository("loginDao")
public class LoginDao extends BaseDaoImpl<Users,Integer> {
     
    public <T> String searchUser(String name , Integer pwd){
        //Class<T> bean = (Class<T>) super.getEntityClass(); : 得到的是object类
        //super.getClass().getSuperclass()  :   (java.lang.Class<T>) class com.springjpa.dao.impl.BaseDaoImpl
        //super.getClass().getGenericSuperclass()  :    (sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl) com.springjpa.dao.impl.BaseDaoImpl<T, java.lang.Integer>
        
        // Class<T> bean = GenericsUtils.getSuperClassGenricType((Class)(((ParameterizedType)super.getClass().getGenericSuperclass()).getActualTypeArguments()[0])); : sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl can not be cast to class
        Object[] params = {name,pwd};
        String jpql = "select users from " + super.entityClass.getName() + " users where users.name = ?1 and users.pwd = ?2 ";
        List<T> resultList = (List<T>)super.queryList(jpql, params);
        ToJson convert = new ToJson();
        JSONArray json = new JSONArray();
        try {
            json = convert.toJSONArray(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
     }
}
