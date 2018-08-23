package org.gear.auth.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.gear.auth.AuthCode;
import org.gear.auth.bean.Api;
import org.gear.auth.bean.Modular;
import org.gear.auth.bean.Role;
import org.gear.auth.bean.RoleModular;
import org.gear.auth.mybatis.EntityGenerator;
import org.gear.auth.mybatis.dao.ApiDao;
import org.gear.auth.mybatis.dao.ModularApiDao;
import org.gear.auth.mybatis.dao.ModularDao;
import org.gear.auth.mybatis.dao.RoleDao;
import org.gear.auth.mybatis.dao.RoleModularDao;
import org.gear.auth.mybatis.dao.UserRoleDao;
import org.gear.auth.param.ApiCreateParam;
import org.gear.auth.param.ApiModifyParam;
import org.gear.auth.param.AuthParam;
import org.gear.auth.param.ModularCreateParam;
import org.gear.common.Assert;
import org.gear.common.bean.exception.CodeException;
import org.gear.common.bean.model.Code;
import org.gear.common.bean.model.UserInfo;
import org.gear.common.bean.param.IdParam;
import org.gear.common.bean.param.NameIdParam;
import org.gear.common.bean.param.NameParam;
import org.jupiter.bean.model.query.Condition;
import org.jupiter.bean.model.query.Query;
import org.jupiter.util.lang.CollectionUtil;
import org.jupiter.util.lang.DateUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthManager {

	@Resource
	private ApiDao apiDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private ModularDao modularDao;
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private ModularApiDao modularApiDao;
	@Resource
	private RoleModularDao roleModularDao;
	
	public Api apiCreate(ApiCreateParam param) {
		Api api = EntityGenerator.newApi(param);
		try {
			apiDao.insert(api);
		} catch (DuplicateKeyException e) {
			throw new CodeException(AuthCode.API_EXIST);
		}
		return api;
	}
	
	public void apiModify(ApiModifyParam param) {
		Api api = Assert.notNull(apiDao.selectByKey(param.getId()), AuthCode.API_NOT_EXIST);
		api.setLock(param.isLock());
		api.setDesc(param.getDesc());
		api.setLock(param.isLogin());
		api.setLogged(param.isLogged());
		api.setIpFilter(param.isIpFilter());
		api.setUpdated(DateUtil.current());
		api.setCheckAuth(param.isCheckAuth());
		api.setLockExpire(param.getLockExpire());
		api.setSecurityLevel(param.getSecurityLevel());
		api.setLockTimeout(param.getLockTimeout());
		apiDao.update(api);
	}
	
	@Transactional
	public void apiDelete(IdParam param) {
		Assert.equal(1, apiDao.deleteByKey(param.getId()), AuthCode.API_NOT_EXIST);
		modularApiDao.deleteByQuery(new Query().and(Condition.eq("api_id", param.getId())));
	}
	
	@Transactional
	public Modular modularCreate(ModularCreateParam param) { 
		Modular parent = null;
		int left = 1;
		if (null != param.getParent()) {
			Map<Integer, Modular> modulars = modularDao.lockById(param.getParent());
			parent = Assert.notNull(modulars.get(param.getParent()), AuthCode.MODULAR_NOT_EXIST);
			left = parent.getRight();
			Iterator<Modular> iterator = modulars.values().iterator();
			while (iterator.hasNext()) {
				Modular modular = iterator.next();
				boolean modified = false;
				if (modular.getLeft() > parent.getRight()) {
					modified = true;
					modular.setLeft(modular.getLeft() + 2);
					modular.setUpdated(DateUtil.current());
				}
				if (modular.getRight() >= parent.getRight()) {
					modified = true;
					modular.setRight(modular.getRight() + 2);
					modular.setUpdated(DateUtil.current());
				}
				if (!modified)
					iterator.remove();
			}
			if (!CollectionUtil.isEmpty(modulars))
				modularDao.replaceMap(modulars);
		}
		Modular modular = EntityGenerator.newModular(parent, left, param);
		modularDao.insert(modular);
		return modular;
	}
	
	public void modularModify(NameIdParam param) { 
		Modular modular = Assert.notNull(modularDao.selectByKey(param.getId()), AuthCode.MODULAR_NOT_EXIST);
		modular.setName(param.getName());
		modular.setUpdated(DateUtil.current());
		modularDao.update(modular);
	}
	
	@Transactional
	public void modularDelete(IdParam param) {
		Map<Integer, Modular> modulars = modularDao.lockById(param.getId());
		Modular modular = Assert.notNull(modulars.get(param.getId()), AuthCode.MODULAR_NOT_EXIST);
		int delt = modular.getRight() - modular.getLeft() + 1;
		Iterator<Modular> iterator = modulars.values().iterator();
		Set<Integer> deleted = new HashSet<Integer>();
		while (iterator.hasNext()) {
			Modular temp = iterator.next();
			if (temp.getLeft() >= modular.getLeft() && temp.getRight() <= modular.getRight()) {			// 当前模块或者子模块
				deleted.add(temp.getId());
				iterator.remove();
			} else {
				boolean modified = false;
				if (temp.getLeft() > modular.getRight()) {
					modified = true;
					temp.setUpdated(DateUtil.current());
					temp.setLeft(temp.getLeft() - delt);
				}
				if (temp.getRight() > modular.getRight()) {
					modified = true;
					temp.setUpdated(DateUtil.current());
					temp.setRight(temp.getRight() - delt);
				}
				if (!modified)
					iterator.remove();
			}
		}
		Assert.isTrue(deleted.size() == (delt / 2));
		modularDao.deleteByKeys(deleted);
		if (!CollectionUtil.isEmpty(modulars))
			modularDao.replaceMap(modulars);
		modularApiDao.deleteByQuery(new Query().and(Condition.in("modular_id", deleted)));
		roleModularDao.deleteByQuery(new Query().and(Condition.in("modular_id", deleted)));
	}
	
	@Transactional
	public void modularAuth(AuthParam param) { 
		Map<Integer, Modular> modulars = modularDao.lockById((int) param.getId());
		Assert.notNull(modulars.get((int) param.getId()), AuthCode.MODULAR_NOT_EXIST);
		if (param.isUnauth()) 
			modularApiDao.deleteByQuery(new Query().and(Condition.eq("api_id", param.getTarId()), Condition.eq("modular_id", param.getId())));
		else {
			Assert.notNull(api(new Query().and(Condition.eq("id", param.getTarId())).forUpdate()), AuthCode.API_NOT_EXIST);
			try {
				modularApiDao.insert(EntityGenerator.newModularApi(param));
			} catch (DuplicateKeyException e) {
				throw new CodeException(Code.FORBID);
			}
		}
	}
	
	public Role roleCreate(NameParam param) { 
		Role role = EntityGenerator.newRole(param);
		roleDao.insert(role);
		return role;
	}
	
	@Transactional
	public void roleModify(NameIdParam param) {
		Query query = new Query().and(Condition.eq("id", param.getId())).forUpdate();
		Role role = Assert.notNull(role(query), AuthCode.ROLE_NOT_EXIST);
		role.setName(param.getName());
		role.setUpdated(DateUtil.current());
		roleDao.update(role);
	}
	
	@Transactional
	public void roleAuth(AuthParam param) {
		Map<Integer, Modular> modulars = modularDao.lockById(param.getTarId());
		Modular target = Assert.notNull(modulars.get(param.getTarId()), AuthCode.MODULAR_NOT_EXIST);
		Query query = new Query().and(Condition.eq("id", param.getId())).forUpdate();
		Role role = Assert.notNull(role(query), AuthCode.ROLE_NOT_EXIST);
		List<RoleModular> list = new ArrayList<RoleModular>();
		modulars.values().forEach(modular -> {
			if (modular.getLeft() <= target.getLeft() && modular.getRight() >= target.getRight()) 
				list.add(EntityGenerator.newRoleModular(role, modular));
		});
		roleModularDao.replaceCollection(list);
	}
	
	@Transactional
	public void roleDelete(IdParam param) {
		Assert.isTrue(1 == roleDao.deleteByKey(param.getId()), AuthCode.ROLE_NOT_EXIST);
		roleModularDao.deleteByQuery(new Query().and(Condition.eq("role_id", param.getId())));
	}
	
	@Transactional
	public void userAuth(UserInfo user, AuthParam param) {
		if (param.isUnauth()) 
			userRoleDao.deleteByQuery(new Query().and(Condition.eq("uid", user.getUid()), Condition.eq("role_id", param.getTarId())));
		else {
			Query query = new Query().and(Condition.eq("id", param.getTarId())).forUpdate();
			Role role = Assert.notNull(role(query), AuthCode.ROLE_NOT_EXIST);
			try {
				userRoleDao.insert(EntityGenerator.newUserRole(user, role));
			} catch (DuplicateKeyException e) {
				throw new CodeException(AuthCode.USER_ROLE_EXIST);
			}
		}
	}
	
	public Api api(Query query) {
		return apiDao.queryUnique(query);
	}
	
	public Role role(Query query) {
		return roleDao.queryUnique(query);
	}
	
	public Modular modular(int id) {
		return modularDao.selectByKey(id);
	}
	
	public List<Api> apis(Query query) {
		return apiDao.queryList(query);
	}
	
	public Modular modular(Query query) {
		return modularDao.queryUnique(query);
	}

	public List<Role> roles(Query query) {
		return roleDao.queryList(query);
	}
	
	public List<Modular> modulars(Query query) {
		return modularDao.queryList(query);
	}
	
	public List<Api> modularApis(int modularId) { 
		return apiDao.modularApis(modularId);
	}
	
	public List<Modular> roleModulars(int roleId) {
		return modularDao.roleModulars(roleId);
	}
}
