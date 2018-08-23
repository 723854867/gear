package org.gear.common.bean.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.gear.common.Assert;
import org.gear.common.realm.Lang;
import org.jupiter.bean.model.Option;
import org.jupiter.util.lang.StringUtil;

public class Code extends Option<String, String> {

	private static final long serialVersionUID = 565566251340718556L;
	
	private static final Map<String, Code> codes = new ConcurrentHashMap<String, Code>();
	
	public static final Code OK							= Code.create("code.ok", "成功");
	public static final Code FORBID						= Code.create("code.forbid", "非法访问");
	public static final Code SYS_ERR					= Code.create("code.sys.err", "系统错误");
	public static final Code PARAM_ERR					= Code.create("code.param.err", "请求参数错误");
	public static final Code SERVER_BUSY				= Code.create("code.server.busy", "服务器繁忙");
	public static final Code RESOURCE_RELEASE_FAIL		= Code.create("code.resource.release.fail", "资源释放失败");
	
	private Code(String key, String value) {
		super(key, Assert.hasText(value));
	}
	
	public static final Code create(String key, String value) {  
		Code code = new Code(key, value);
		Assert.isNull(codes.putIfAbsent(key, code), "code [" + key + "] has duplicated!");
		String lang = Lang.get(key);
		if (StringUtil.hasText(lang))
			code.value = lang;
		return code;
	}
}
