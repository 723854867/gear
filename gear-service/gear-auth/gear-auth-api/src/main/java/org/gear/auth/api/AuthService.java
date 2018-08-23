package org.gear.auth.api;

import java.util.List;

import org.gear.auth.bean.Api;
import org.gear.auth.bean.Modular;
import org.gear.auth.bean.Role;
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
import org.jupiter.bean.model.query.Query;

public interface AuthService {
	
	Api api(String path);
	
	Pager<Api> apis(Query query);
	
	Api apiCreate(ApiCreateParam param);
	
	void apiModify(ApiModifyParam param);
	
	void apiDelete(IdParam param);
	
	Modular modular(int id);
	
	Pager<Modular> modulars(Query query);
	
	Modular modularCreate(ModularCreateParam param);
	
	void modularModify(NameIdParam param);
	
	void modularDelete(IdParam param);
	
	void modularAuth(AuthParam param);
	
	Pager<Api> modularApis(IdParam param);
	
	Pager<Role> roles(Query query);
	
	Role roleCreate(NameParam param);
	
	void roleModify(NameIdParam param);
	
	void roleAuth(AuthParam param);
	
	void roleDelete(IdParam param);
	
	List<Modular> roleModulars(int roleId);
	
	void userAuth(UserInfo user, AuthParam param);
	
	void auth(Api api, RequestMeta meta);
}
