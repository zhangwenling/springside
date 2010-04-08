package org.springside.examples.showcase.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.orm.jdbc.SqlBuilder;

import com.google.common.collect.Maps;

/**
 * User对象的Jdbc Dao, 演示Spring JdbcTemplate的使用.
 * 
 * @author calvin
 */
public class UserJdbcDao {

	private static final String QUERY_USER_BY_ID = "select id, name, login_name from SS_USER where id=?";
	private static final String QUERY_USER_BY_IDS = "select id, name, login_name from SS_USER where id in(:ids)";
	private static final String QUERY_USER = "select id, name, login_name from SS_USER order by id";
	private static final String QUERY_USER_BY_LOGINNAME = "select id,name,login_name from SS_USER where login_name=:login_name";
	private static final String INSERT_USER = "insert into SS_USER(id, login_name, name) values(:id, :loginName, :name)";

	private SqlBuilder searchUserSqlBuilder;

	private SimpleJdbcTemplate jdbcTemplate;

	private UserMapper userMapper = new UserMapper();

	private class UserMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setLoginName(rs.getString("login_name"));
			return user;
		}
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	public void setSearchUserSql(String searchUserSql) {
		this.searchUserSqlBuilder = new SqlBuilder(searchUserSql);
	}

	/**
	 * 查询单个对象.
	 */
	public User queryObject(Long id) {
		return jdbcTemplate.queryForObject(QUERY_USER_BY_ID, userMapper, id);
	}

	/**
	 * 查询对象列表.
	 */
	public List<User> queryObjectList() {
		return jdbcTemplate.query(QUERY_USER, userMapper);
	}

	/**
	 * 查询单个结果Map.
	 */
	public Map<String, Object> queryMap(Long id) {
		return jdbcTemplate.queryForMap(QUERY_USER_BY_ID, id);
	}

	/**
	 * 查询结果Map列表.
	 */
	public List<Map<String, Object>> queryMapList() {
		return jdbcTemplate.queryForList(QUERY_USER);
	}

	/**
	 * 使用Map形式的命名参数.
	 */
	public User queryByNamedParameter(String loginName) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("login_name", loginName);

		return jdbcTemplate.queryForObject(QUERY_USER_BY_LOGINNAME, userMapper, map);
	}

	/**
	 * 使用Map形式的命名参数.
	 */
	public List<User> queryByNamedParameterWithInClause(Long... ids) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("ids", Arrays.asList(ids));

		return jdbcTemplate.query(QUERY_USER_BY_IDS, userMapper, map);
	}

	/**
	 * 使用Bean形式的命名参数, Bean的属性名称应与命名参数一致. 
	 */
	public void createObject(User user) {
		//使用BeanPropertySqlParameterSource将User的属性映射为命名参数.
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(user);
		jdbcTemplate.update(INSERT_USER, source);
	}

	/**
	 * 批量创建/更新对象,使用Bean形式的命名参数.
	 */
	public void batchCreateObject(List<User> userList) {
		int i = 0;
		BeanPropertySqlParameterSource[] sourceArray = new BeanPropertySqlParameterSource[userList.size()];

		for (User user : userList) {
			sourceArray[i++] = new BeanPropertySqlParameterSource(user);
		}

		jdbcTemplate.batchUpdate(INSERT_USER, sourceArray);
	}

	/**
	 * 使用freemarker创建动态SQL.
	 */
	public List<User> searchUserByFreemarkerSqlTemplate(Map conditions) {
		String sql = searchUserSqlBuilder.getSql(conditions);
		return jdbcTemplate.query(sql, userMapper, conditions);
	}
}
