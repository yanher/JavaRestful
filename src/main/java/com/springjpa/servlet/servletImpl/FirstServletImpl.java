package com.springjpa.servlet.servletImpl;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springjpa.beans.FirstBean;

@Component("firstServletImpl")
public class FirstServletImpl {
    @PersistenceContext(unitName="openjpa-unit",type=PersistenceContextType.EXTENDED)
    private EntityManager em;
    @Transactional(propagation=Propagation.REQUIRED,readOnly = true)
    public String queryAll() {  
        try {
            Query query = em.createQuery("select first from FirstBean first ");
            List<FirstBean> list = (List<FirstBean>)query.getResultList(); 
            Iterator<FirstBean> iterator = list.iterator(); //没开启事务的时候，得到iterator也没用，因为resultlist已经关上了，所以iterator返回空的
            StringBuffer bf = new StringBuffer();
            bf.append("<table>"); 
            FirstBean fb = null;
            while(iterator.hasNext()){
              fb = iterator.next();
              bf.append(" <tr> <td> " +fb.getId() + " </td> " +
                               "<td> " +fb.getType() + " </td> " +
                               "<td> " +fb.getPosition() + " </td> " +
                               "<td> " +fb.getPrice() + " </td> " +
                               "<td> " +fb.getTime() + " </td> " +
                         " </tr> "); 
            } 
            bf.append("</table>"); 
            return bf.toString();
     } catch (Exception e) {
         e.printStackTrace();

     }  
        return null;
    }
    @Transactional(propagation=Propagation.REQUIRED)
    public void insert() {
        FirstBean b = new FirstBean();
        b.setType("abcdefg"); 
        b.setName("yh");
        b.setPosition("中国");
        b.setPrice("1");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        b.setTime(df.format(new Date()));
        em.persist(b);
    }
    
}

