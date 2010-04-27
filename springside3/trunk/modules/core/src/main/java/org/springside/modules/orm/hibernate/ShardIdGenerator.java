package org.springside.modules.orm.hibernate;

import java.io.Serializable;

import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.UUIDHexGenerator;

/**
 * 重载Hibernate的UUIDGenerator, 将GUID长度缩短到12-16位.主要用于多数据库的情形.
 * 
 * 0或2位(最大值为255)shardId + 11位时间戳 + 0或2位InstanceId + 1位微秒内计数器.
 * 
 * 1. ShardId - 定义数据库InstanceId, 子类可重载实现getShardId()
 *              从System Properties, Spring ApplicationContext等地方获得当前运行应用的Id.
 *              如果为值0则在Id中将不含ShardId编码.
 * 2. 时间戳      - 等于之前的HiTime+LoTime
 * 3. AppId   - 使用自定义值(规则同ShardId)代替 原UUID中的IP + JVM
 * 4. Count   - 相同JVM同一毫秒内的计数器, 长度一般为1位(16000 tps).
 * 
 * @author calvin
 */
public class ShardIdGenerator extends UUIDHexGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object) {
		return new StringBuilder(14).append(formatOptionalId(getShardId())).append(formatTimestamp()).append(
				formatOptionalId(getAppId())).append(formatCount()).toString();
	}

	protected short getShardId() {
		return 0;
	}

	protected short getAppId() {
		return 0;
	}

	protected String formatTimestamp() {
		return Long.toHexString(System.currentTimeMillis());
	}

	protected String formatCount() {
		return Integer.toHexString(getCount());
	}

	/**
	 * 格式化最大值为255的数值成长度为2的字符串,如果值为0则返回空字符串.
	 */
	protected String formatOptionalId(short value) {
		if (value > 0)
			return format(value);
		else
			return "";
	}

	/**
	 * 格式化最大值为255的数值成长度为2的字符串.
	 */
	protected String format(short value) {
		String formatted = Integer.toHexString(value);
		if (formatted.length() < 2) {
			formatted = "0" + formatted;
		}
		return formatted;
	}

}
