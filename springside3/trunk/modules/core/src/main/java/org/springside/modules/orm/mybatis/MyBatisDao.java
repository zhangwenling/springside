package org.springside.modules.orm.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.ReflectionUtils;
import org.springside.modules.orm.Page;

/**
 * Mybatis分页查询工具类,为分页查询增加传递:
 * startRow,endRow : 用于oracle分页使用,从1开始
 * offset,limit : 用于mysql 分页使用,从0开始
 * 
 * @author badqiu
 *
 */
@SuppressWarnings("unchecked")
public class MyBatisDao extends SqlSessionDaoSupport {

	public <T> Page<T> selectPage(Page<T> page, String statementName, Object parameter) {
		String countStatementName = statementName + ".count";
		return selectPage(page, statementName, countStatementName, parameter);
	}

	public <T> Page<T> selectPage(Page<T> page, String statementName, String countStatementName, Object parameter) {

		Number totalItems = (Number) getSqlSession().selectOne(countStatementName, parameter);

		if (totalItems != null && totalItems.longValue() > 0) {
			List list = getSqlSession().selectList(statementName, toParameterMap(parameter, page));
			page.setResult(list);
			page.setTotalItems(totalItems.longValue());
		}
		return page;
	}

	protected static Map toParameterMap(Object parameter, Page p) {
		Map map = toParameterMap(parameter);
		map.put("startRow", p.getStartRow());
		map.put("endRow", p.getEndRow());
		map.put("offset", p.getOffset());
		map.put("limit", p.getPageSize());
		return map;
	}

	protected static Map toParameterMap(Object parameter) {
		if (parameter instanceof Map) {
			return (Map) parameter;
		} else {
			try {
				return PropertyUtils.describe(parameter);
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
				return null;
			}
		}
	}
}
