package org.springside.examples.miniservice.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.util.ReflectionUtils;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.Paginator;

/**
 * Mybatis分页查询工具类,为分页查询增加传递:
 * startRow,endRow : 用于oracle分页使用,从1开始
 * offset,limit : 用于mysql 分页使用,从0开始
 * 
 * @author badqiu
 *
 */
@SuppressWarnings("unchecked")
public class MybatisPageQueryUtils {
	
	public static Page pageQuery(SqlSession sqlSession, String statement,
			Object parameter, int pageNo, int pageSize) {
		String countStatement = statement + ".count";

		return pageQuery(sqlSession, statement, countStatement, parameter, pageNo,
				pageSize);
	}

	public static Page pageQuery(SqlSession sqlSession, String statement,
			String countStatement, Object parameter, int pageNo,
			int pageSize) {
		Number totalItems = (Number) sqlSession.selectOne(countStatement,parameter);

		if (totalItems != null && totalItems.longValue() > 0) {
			Paginator paginator = new Paginator(pageNo, pageSize, totalItems.longValue());
			List list = sqlSession.selectList(statement, toParameterMap(parameter,paginator));
			Page page = new Page(pageSize);
			page.setResult(list);
			page.setTotalItems(totalItems.longValue());
			page.setPageNo(pageNo);
			return page;
		}

		return new Page(pageSize);
	}

	public static Map toParameterMap(Object parameter,Paginator p) {
		Map map = toParameterMap(parameter);
		map.put("startRow", p.getStartRow());
		map.put("endRow", p.getEndRow());
		map.put("offset", p.getOffset());
		map.put("limit", p.getPageSize());
		return map;
	}
	
	public static Map toParameterMap(Object parameter) {
		if(parameter instanceof Map) {
			return (Map)parameter;
		}else {
			try {
				return BeanUtils.describe(parameter);
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
				return null;
			}
		}
	}
}
