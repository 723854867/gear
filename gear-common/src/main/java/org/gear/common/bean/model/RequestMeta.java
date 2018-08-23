package org.gear.common.bean.model;

import java.io.Serializable;

import org.jupiter.bean.model.MultiListMap;

import lombok.Getter;
import lombok.Setter;

/**
 * 请求元数据
 * 
 * @author lynn
 */
@Getter
@Setter
public class RequestMeta implements Serializable {

	private static final long serialVersionUID = -1624110602560363089L;

	private String id;
	// 请求ip
	private String ip;
	// 请求方法类型：GET、POST等
	private String type;
	// 请求路劲
	private String path;
	// url查询参数
	private String query;
	private UserInfo user;
	// 请求时间
	private int timestamp;
	// 请求接口描述
	private String apiDesc;
	// 请求类方法：类全名.方法名
	private String apiMethod;
	// 请求头
	private MultiListMap<String, String> headers;
}
