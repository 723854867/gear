package org.gear.log.bean.entity;

import org.gear.common.bean.model.UserInfo;
import org.jupiter.bean.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogRequest implements Identifiable<String> {

	private static final long serialVersionUID = 1009833718722477439L;
	
	// 请求ip
	private String ip;
	// 请求编号：唯一
	private String _id;
	// 请求方法类型：GET、POST等
	private String type;
	// 请求路劲
	private String path;
	// 创建时间10位unix戳(用于排序)
	private int created;
	// 请求时间：yyyy-MM-dd HH:mm:ss.SSS
	private String ctime;
	// 响应时间：yyyy-MM-dd HH:mm:ss.SSS
	private String rtime;
	// 请求参数(包括了 body 和 表单等参数，只包括了可序列化且不是io流的参数)
	private Object param;
	// url查询参数
	private String query;
	private UserInfo user;
	// 响应结果
	private Object result;
	// 请求接口描述
	private String apiDesc;
	// 是否成功
	private boolean success;
	// 请求类方法：类全名.方法名
	private String apiMethod;
	
	@Override
	public String key() {
		return this._id;
	}
}
