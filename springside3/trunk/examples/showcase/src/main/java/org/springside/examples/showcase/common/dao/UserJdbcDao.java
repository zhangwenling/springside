package org.springside.examples.showcase.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springside.examples.showcase.common.entity.User;

import com.google.common.collect.Maps;

/**
 * User对象的Jdbc Dao, 演示Spring JdbcTemplate的使用.
 * 
 * @author calvin
 */
@Repository
public class UserJdbcDao {

	private SimpleJdbcTemplate jdbcTemplate;

	private RowMapper<User> userMapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setLoginName(rs.getString("login_name"));
			return user;
		}
	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/**
	 * 查询单个对象.
	 */
	public User queryObject(Long id) {
		return jdbcTemplate.queryForObject("select id, name, login_name from SS_USER where id=?", userMapper, id);
	}

	/**
	 * 查询对象列表.
	 */
	public List<User> queryObjectList() {
		return jdbcTemplate.query("select id, name, login_name from SS_USER order by id", userMapper);
	}

	/**
	 * 查询单个结果Map.
	 */
	public Map<String, Object> queryMap(Long id) {
		return jdbcTemplate.queryForMap("select id, login_name from SS_USER where id=?", id);
	}

	/**
	 * 查询结果Map列表.
	 */
	public List<Map<String, Object>> queryMapList() {
		return jdbcTemplate.queryForList("select id, login_name from SS_USER order by id");
	}


	/**
	 * 使用Map形式的命名参数.
	 */
	public User queryByMultiNamedParameter(String loginName, String name) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("login_name", loginName);
		map.put("name", name);

		return jdbcTemplate.queryForObject(
				"select id,name,login_name from SS_USER where name=:name and login_name=:login_name", userMapper, map);
	}

	/**
	 * 使用Bean形式的命名参数, Bean的属性名称应与命名参数一致. 
	 */
	public void createObject(User user) {
		//使用BeanPropertySqlParameterSource将User的属性映射为命名参数.
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(user);
		jdbcTemplate.update("insert into SS_USER(id, login_name, name) values(:id, :loginName, :name)", source);
	}

	/**
	 * 批量创建/更新对象.
	 */
	public void batchCreateObject(List<User> userList) {
		int i = 0;
		BeanPropertySqlParameterSource[] sourceArray = new BeanPropertySqlParameterSource[userList.size()];

		for (User user : userList) {
			sourceArray[i++] = new BeanPropertySqlParameterSource(user);
		}

		jdbcTemplate.batchUpdate("insert into SS_USER(id, login_name, name) values(:id, :loginName, :name)",
				sourceArray);
	}
}
