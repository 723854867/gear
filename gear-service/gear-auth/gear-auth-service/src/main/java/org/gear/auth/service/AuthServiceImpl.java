package org.gear.auth.service;

import java.util.List;

import javax.annotation.Resource;

import org.gear.auth.api.AuthService;
import org.gear.auth.bean.Api;
import org.gear.auth.bean.Modular;
import org.gear.auth.bean.Role;
import org.gear.auth.manager.AuthManager;
import org.gear.auth.param.ApiCreateParam;
import org.gear.auth.param.ApiModifyParam;
import org.gear.auth.param.AuthParam;
import org.gear.auth.param.ModularCreateParam;
import org.gear.common.bean.model.RequestMeta;
import org.gear.common.bean.model.UserInfo;
import org.gear.common.bean.param.IdParam;
import org.gear.common.bean.param.NameIdParam;
import org.gear.common.bean.param.NameParam;
import org.jupiter.bean.model.Pager;
import org.jupiter.bean.model.query.Condition;
import org.jupiter.bean.model.query.Query;
import org.jupiter.mybatis.PagerUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("authService")
public class AuthServiceImpl implements AuthService {
	
	@Resource
	private AuthManager authManager;
	
	@Override
	public Api api(String path) {
		return authManager.api(new Query().and(Condition.eq("path", path)));
	}
	
	@Override
	public Pager<Api> apis(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return PagerUtil.page(authManager.apis(query));
	}

	@Override
	public Api apiCreate(ApiCreateParam param) {
		return authManager.apiCreate(param);
	}
	
	@Override
	public void apiModify(ApiModifyParam param) {
		authManager.apiModify(param);
	}
	
	@Override
	public void apiDelete(IdParam param) {
		authManager.apiDelete(param);
	}
	
	@Override
	public Modular modular(int id) {
		return authManager.modular(id);
	}
	
	@Override
	public Pager<Modular> modulars(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return PagerUtil.page(authManager.modulars(query));
	}
	
	@Override
	public Modular modularCreate(ModularCreateParam param) {
		return authManager.modularCreate(param);
	}
	
	@Override
	public void modularModify(NameIdParam param) {
		authManager.modularModify(param);
	}
	
	@Override
	public void modularDelete(IdParam param) {
		authManager.modularDelete(param);
	}
	
	@Override
	public void modularAuth(AuthParam param) {
		authManager.modularAuth(param);
	}
	
	@Override
	public Pager<Api> modularApis(IdParam param) {
		Query query = param.getQuery();
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return PagerUtil.page(authManager.modularApis(param.getId()));
	}
	
	@Override
	public Pager<Role> roles(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return PagerUtil.page(authManager.roles(query));
	}
	
	@Override
	public Role roleCreate(NameParam param) {
		return authManager.roleCreate(param);
	}
	
	@Override
	public void roleModify(NameIdParam param) {
		authManager.roleModify(param);
	}
	
	@Override
	public void roleAuth(AuthParam param) {
		authManager.roleAuth(param);
	}
	
	@Override
	public void roleDelete(IdParam param) {
		authManager.roleDelete(param);
	}
	
	@Override
	public List<Modular> roleModulars(int roleId) {
		return authManager.roleModulars(roleId);
	}
	
	@Override
	public void userAuth(UserInfo user, AuthParam param) {
		authManager.userAuth(user, param);
	}
	
	@Override
	public void auth(Api api, RequestMeta meta) {
	}
}
