package com.springjpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springjpa.common.GenericsUtils;
import com.springjpa.dao.IBaseDao;


/**
 * 
 * @author 武明宝
 * 
 * @param <T>泛型
 * @param <PK>指向泛型对象标识类型
 */
@Repository("baseDao")
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T, PK extends Serializable> implements IBaseDao<T, PK> {

	@PersistenceContext(unitName="openjpa-unit",type=PersistenceContextType.TRANSACTION)
	protected EntityManager em;
	protected Class<T> entityClass;
	
	public EntityManager getEm() {
		return em;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public BaseDaoImpl() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	public T save(T t) throws RuntimeException {
		try {
			t = em.contains(t) ? t : em.merge(t);
		} catch (Exception e) {
			throw new RuntimeException("保存失败，请联系管理员");
		}
		return t;
	}

	public Collection<T> save(Collection<T> ts) {
		Collection<T> collection = new HashSet<T>();
		for (T t : ts)
			collection.add(save(t));
		return collection;
	}

	public T persist(T t) {
		try {
			em.persist(t);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存新建实例时发生异常：" + e.getMessage());
		}
	}

	public boolean persist(Collection<T> ts) {
		try {
			for (T t : ts)
				em.persist(t);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public T merge(T t) throws RuntimeException {
		try {
			t = em.contains(t) ? t : em.merge(t);
		} catch (Exception e) {
			throw new RuntimeException("更新失败，请联系管理员！");
		}
		return t;
	}

	public Collection<T> merge(Collection<T> ts) {
		Collection<T> collection = new HashSet<T>();
		for (T t : ts)
			collection.add(merge(t));
		return collection;
	}

	public void remove(T t) throws RuntimeException {
		if (null == t)
			throw new RuntimeException("请求删除的对象为空");
		try {
			if (em.contains(t))
				em.remove(t);
			else {
				PK id = (PK) em.getEntityManagerFactory()
						.getPersistenceUnitUtil().getIdentifier(t);
				em.remove(em.find(t.getClass(), id));
			}
		} catch (Exception e) {
			throw new RuntimeException("删除对象时出错，请联系管理员!");
		}
	}

	public void remove(Collection<T> ts) {
		for (T t : ts)
			remove(t);
	}

	public void remove(Class<T> cls,PK id) {
		try {
			em.remove(em.find(cls, id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove( Class<T> cls,Collection<PK> ids) {
		for (PK id : ids)
			remove(cls, id);
	}

	public void clear() {
		em.clear();
	}

	public void detach(T t) {
		em.detach(t);
	}

	public boolean isManaged(T t) {
		return em.contains(t);
	}

	public void flush() {
		em.flush();
	}

	protected T refresh( T t) throws RuntimeException {
		PK id = (PK) em.getEntityManagerFactory().getPersistenceUnitUtil()
				.getIdentifier(t);
		if (null == t)
			throw new RuntimeException("请求刷新的实体为空!");
		if (id == null)
			return null;
		if (em.contains(t)) {
			em.refresh(t);
			return t;
		} else {
			return em.find(entityClass, id);
		}
	}

	@Override
	public List<T> queryAll() {
		return queryAll("");
	}

	@Override
	public List<T> queryAll(String orderStr) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" FROM ").append(entityName(entityClass));
		if (orderStr != null && !orderStr.equals(""))
			sbSql.append(" ORDER BY ").append(orderStr);
		List<T> results = em.createQuery(sbSql.toString()).getResultList();
		return results;
	}

	@Override
	public List<T> queryList(String hql, Object[] params) {
		Query query = em.createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i, params[i]);
		}
		return query.getResultList();
	}

	@Override
	public T queryById(PK id) {
		return em.find(entityClass, id);
	}

	@Override
	public List<T> queryByIds(PK[] ids) {
		List<T> results = new ArrayList<T>();
		for (PK id : ids)
			results.add(queryById(id));
		return results;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long queryTotalCount() {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT COUNT(*) FROM ").append(entityName(entityClass));
		List list = em.createQuery(sbSql.toString()).getResultList();
		if(list.isEmpty())
			return (long)0;
		return new Long(list.get(0).toString());
	}

	@Override
	public int execute(String hql, Object[] params) {
		Query query = em.createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public int executeBySql(String sql, Object[] params) {
		Query query = em.createNativeQuery(sql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.executeUpdate();
	}

	/**
	 * 得到类名
	 * 
	 * @param clazz
	 * @return
	 */
	protected static <E> String entityName(Class<E> clazz) {
		String entityName = clazz.getSimpleName();
		Entity entity = clazz.getAnnotation(Entity.class);
		if (entity != null && entity.name() != null
				&& !"".equals(entity.name())) {
			entityName = entity.name();
		}
		return entityName;
	}

	@Override
	public List<T> queryListByEqual(Map<String, Object> paramsMap) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" FROM ").append(entityName(entityClass));
		StringBuffer sbFilter = addEqualFilter(paramsMap);
		if (sbFilter != null && !sbFilter.equals(""))
			sbSql.append(" WHERE ").append(sbFilter);
		Query query = em.createQuery(sbSql.toString());
		return query.getResultList();
	}

	/**
	 * 将Map参数转化为条件
	 * 
	 * @param paramsMap
	 * @return
	 */
	private static StringBuffer addEqualFilter(Map<String, Object> paramsMap) {
		StringBuffer sbFilter = new StringBuffer();
		Set<Map.Entry<String, Object>> setMap = paramsMap.entrySet();
		for (Iterator<Map.Entry<String, Object>> it = setMap.iterator(); it
				.hasNext();) {
			Map.Entry<String, Object> entry = it.next();
			sbFilter.append(entry.getKey()).append(" = ");
			if (!entry.getValue().getClass().getSimpleName().equals("Integer"))
				sbFilter.append("'").append(entry.getValue()).append("'");
			else
				sbFilter.append(entry.getValue());
			if (it.hasNext())
				sbFilter.append(" AND ");
		}
		return sbFilter;
	}
	
	@Override
	public List<T> queryListByLike(Map<String, String> map) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" FROM ").append(entityName(entityClass));
		StringBuffer sbFilter = addLikeFilter(map);
		if (sbFilter != null && !sbFilter.equals(""))
			sbSql.append(" WHERE ").append(sbFilter);
		Query query = em.createQuery(sbSql.toString());
		return query.getResultList();
	}

	/**
	 * 将Map参数转化为条件
	 * 
	 * @param paramsMap
	 * @return
	 */
	private static StringBuffer addLikeFilter(Map<String, String> paramsMap) {
		StringBuffer sbFilter = new StringBuffer();
		Set<Map.Entry<String, String>> setMap = paramsMap.entrySet();
		for (Iterator<Map.Entry<String, String>> it = setMap.iterator(); it
				.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			sbFilter.append(entry.getKey()).append(" like ");
			// if
			// (!entry.getValue().getClass().getSimpleName().equals("Integer"))
			sbFilter.append("'%").append(entry.getValue()).append("%'");
			// else
			// sbFilter.append(entry.getValue());
			if (it.hasNext())
				sbFilter.append(" AND ");
		}
		return sbFilter;
	}
}
