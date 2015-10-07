package com.springjpa.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * DAO基类：用于增删改查及分页等
 * 创建人： Yan He
 * 版本： 1.0
 * 创建时间：2015-10-5
 * @param <T>泛型
 * @param <PK>指向泛型对象标识类型
 */
public interface IBaseDao<T, PK extends Serializable> {

	/**
	 * 不需要确定新建实体是否已经存在
	 * 
	 * @param t
	 * @return
	 */
	public T save(T t);

	public Collection<T> save(Collection<T> ts);

	/**
	 * 可确定为新建实体；返回处于托管状态的实例
	 * 
	 * @param t
	 * @return
	 */
	public T persist(T t);

	public boolean persist(Collection<T> ts);

	/**
	 * 若数据库中已有此记录，置为托管状态
	 * 
	 * @param t
	 * @return
	 */
	public T merge(T t);

	public Collection<T> merge(Collection<T> ts);

	/**
	 * 删除单个对象
	 * 
	 * @param t
	 */
	public void remove(T t);

	public void remove(Collection<T> ts);

	/**
	 * 根据id删除
	 * @param clazz
	 * @param id
	 */
	public void remove(Class<T> cls,PK id);

	public void remove(Class<T> cls ,Collection<PK> ids);

	/**
	 * 清除一级缓存
	 */
	public void clear();

	/**
	 * 将对象设置为游离状态
	 * 
	 * @param t
	 */
	public void detach(T t);

	/**
	 * 检查对象是否处于托管状态
	 * 
	 * @param t 对象
	 * @return
	 */
	public boolean isManaged(T t);

	/**
	 * 同步JPA容器与数据库
	 */
	public void flush();

	/**
	 * 查询所有信息
	 * 
	 * @return 返回所有信息
	 */
	public List<T> queryAll();

	/**
	 * 查询所有信息
	 * 
	 * @param orderStr
	 *            根据字段排序
	 * @return 返回所有信息
	 */
	public List<T> queryAll(String orderStr);

	/**
	 * 根据参数查询
	 * 
	 * @param hql
	 *            传入Sql语句
	 * @param params
	 *            参数数组
	 * @return 返回List集合
	 */
	public List<T> queryList(String hql, Object[] params);

	/**
	 * 根据主键查询
	 * 
	 * @param cls 泛型字节码文件
	 * @param id 主键id
	 * @return 返回对象
	 */
	public T queryById(PK id);

	/**
	 * 根据ids查询
	 * 
	 * @param ids 参数数组
	 * @return 查询到List集合
	 */
	public List<T> queryByIds(PK[] ids);

	/**
	 * 按字段是否相等来查询
	 * @param cls 泛型类字节码
	 * @param paramsMap 参数集合
	 * @return 返回List集合
	 */
	public List<T> queryListByEqual(Map<String, Object> paramsMap);

	/**
	 * 按字段模糊匹配查询
	 * 
	 * @param cls 泛型类字节码
	 * @param map 参数集合
	 * @return 返回List集合
	 */
	public List<T> queryListByLike(Map<String, String> map);

	/**
	 * 查询总记录数
	 * 
	 * @return 返回总记录数
	 */
	public Long queryTotalCount();

	/**
	 * 执行HQL
	 * 
	 * @param hql hibnateSql语句
	 * @param params 参数集合
	 * @return 
	 */
	int execute(String hql, Object[] params);

	/**
	 * 执行SQL
	 * 
	 * @param sql 原生SQL语句
	 * @param params 参数数组
	 * @return
	 */
	int executeBySql(String sql, Object[] params);

}
