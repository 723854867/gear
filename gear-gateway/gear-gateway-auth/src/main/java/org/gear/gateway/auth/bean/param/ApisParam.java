package org.gear.gateway.auth.bean.param;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;

import org.gear.common.bean.param.Param;
import org.jupiter.bean.model.query.Condition;
import org.jupiter.bean.model.query.Query;
import org.jupiter.util.lang.CollectionUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApisParam extends Param {

	private static final long serialVersionUID = 2316284222634111839L;

	@Min(1)
	private Integer id;
	private String path;
	private String desc;
	// 是否启用用户分布式锁：同一用户 (User) 同一时间只能请求一个 lock 为 true 的接口
	private Boolean lock;
	// 是否需要登录
	private Boolean login;
	// 是否记录访问日志
	private Boolean logged;
	private Boolean ipFilter;
	// 是否参与权限管理
	private Boolean checkAuth;
	@Min(0)
	private Integer securityLevel;
	
	@Override
	public void verify() {
		super.verify();
		Query query = getQuery();
		List<Condition> list = new ArrayList<Condition>();
		if (null != id)
			list.add(Condition.eq("id", id));
		if (null != path)
			list.add(Condition.like("path", path));
		if (null != desc)
			list.add(Condition.like("desc", desc));
		if (null != lock)
			list.add(Condition.eq("lock", lock));
		if (null != login)
			list.add(Condition.eq("login", login));
		if (null != logged)
			list.add(Condition.eq("logged", logged));
		if (null != ipFilter)
			list.add(Condition.eq("ip_filter", ipFilter));
		if (null != checkAuth)
			list.add(Condition.eq("check_auth", checkAuth));
		if (null != securityLevel)
			list.add(Condition.eq("security_level", securityLevel));
		if (!CollectionUtil.isEmpty(list))
			query.and(list);
		query.orderByDesc("created");
	}
}
