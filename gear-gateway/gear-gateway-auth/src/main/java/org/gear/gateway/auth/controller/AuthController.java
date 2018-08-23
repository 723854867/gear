package org.gear.gateway.auth.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gear.auth.AuthCode;
import org.gear.auth.api.AuthService;
import org.gear.auth.bean.Modular;
import org.gear.auth.param.ApiCreateParam;
import org.gear.auth.param.ApiModifyParam;
import org.gear.auth.param.AuthParam;
import org.gear.auth.param.ModularCreateParam;
import org.gear.common.Assert;
import org.gear.common.bean.model.Result;
import org.gear.common.bean.model.UserInfo;
import org.gear.common.bean.param.IdParam;
import org.gear.common.bean.param.NameIdParam;
import org.gear.common.bean.param.NameParam;
import org.gear.common.bean.param.OIdParam;
import org.gear.common.bean.param.Param;
import org.gear.gateway.auth.AuthUtil;
import org.gear.gateway.auth.bean.param.ApisParam;
import org.gear.gateway.auth.bean.param.ModularsParam;
import org.gear.soa.user.api.UserService;
import org.gear.soa.user.bean.UserCode;
import org.jupiter.bean.model.query.Condition;
import org.jupiter.bean.model.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {
	
	@Resource
	private AuthService authService;
	@Resource
	private UserService userService;

	/**
	 * 接口分页
	 */
	@ResponseBody
	@RequestMapping("api/list")
	public Object apis(@RequestBody @Valid ApisParam param) {
		return authService.apis(param.getQuery());
	}
	
	/**
	 * 新增接口
	 */
	@ResponseBody
	@RequestMapping("api/create")
	public Object apiCreate(@RequestBody @Valid ApiCreateParam param) {
		return authService.apiCreate(param);
	}
	
	/**
	 * 接口编辑
	 */
	@ResponseBody
	@RequestMapping("api/modify")
	public Object apiModify(@RequestBody @Valid ApiModifyParam param) {
		authService.apiModify(param);
		return Result.ok();
	}
	
	/**
	 * 接口删除
	 */
	@ResponseBody
	@RequestMapping("api/delete")
	public Object apiDelete(@RequestBody @Valid IdParam param) {
		authService.apiDelete(param);
		return Result.ok();
	}
	
	/**
	 * 模块分页
	 */
	@ResponseBody
	@RequestMapping("modular/list")
	public Object modulars(@RequestBody @Valid ModularsParam param) { 
		return authService.modulars(param.getQuery());
	}
	
	/**
	 * 新增模块
	 */
	@ResponseBody
	@RequestMapping("modular/create")
	public Object modularCreate(@RequestBody @Valid ModularCreateParam param) { 
		return authService.modularCreate(param);
	}
	
	/**
	 * 模块编辑
	 */
	@ResponseBody
	@RequestMapping("modular/modify")
	public Object modularModify(@RequestBody @Valid NameIdParam param) { 
		authService.modularModify(param);
		return Result.ok();
	}
	
	/**
	 * 模块删除
	 */
	@ResponseBody
	@RequestMapping("modular/delete")
	public Object modularDelete(@RequestBody @Valid IdParam param) { 
		authService.modularDelete(param);
		return Result.ok();
	}
	
	/**
	 * 模块授权(模块新增接口)
	 */
	@ResponseBody
	@RequestMapping("modular/auth")
	public Object modularAuth(@RequestBody @Valid AuthParam param) { 
		authService.modularAuth(param);
		return Result.ok();
	}
	
	/**
	 * 模块接口列表
	 */
	@ResponseBody
	@RequestMapping("modular/api/list")
	public Object modularApis(@RequestBody @Valid IdParam param) { 
		return authService.modularApis(param);
	}
	
	/**
	 * 模块树
	 */
	@ResponseBody
	@RequestMapping("modular/tree")
	public Object modularTree(@RequestBody @Valid OIdParam param) {
		Query query = new Query();
		if (null != param.getId()) {
			Modular modular = Assert.notNull(authService.modular(param.getId()), AuthCode.MODULAR_NOT_EXIST);
			query.and(Condition.eq("trunk", modular.getTrunk()), Condition.gte("left", modular.getLeft()), Condition.lte("right", modular.getRight()));
		}
		List<Modular> modulars = authService.modulars(query).getList();
		return AuthUtil.tree(modulars);
	}
	
	/**
	 * 模块链
	 */
	@ResponseBody
	@RequestMapping("modular/chain")
	public Object modularChain(@RequestBody @Valid OIdParam param) {
		Query query = new Query();
		if (null != param.getId()) {
			Modular modular = Assert.notNull(authService.modular(param.getId()), AuthCode.MODULAR_NOT_EXIST);
			query.and(Condition.eq("trunk", modular.getTrunk()), Condition.gte("left", modular.getLeft()), Condition.lte("right", modular.getRight()));
		}
		List<Modular> modulars = authService.modulars(query).getList();
		return AuthUtil.chain(modulars);
	}
	
	/**
	 * 角色列表
	 */
	@ResponseBody
	@RequestMapping("role/list")
	public Object roles(@RequestBody @Valid Param param) {
		return authService.roles(param.getQuery());
	}
	
	/**
	 * 创建角色
	 */
	@ResponseBody
	@RequestMapping("role/create")
	public Object roleCreate(@RequestBody @Valid NameParam param) {
		return authService.roleCreate(param);
	}
	
	/**
	 * 角色编辑
	 */
	@ResponseBody
	@RequestMapping("role/modify")
	public Object roleModify(@RequestBody @Valid NameIdParam param) {
		authService.roleModify(param);
		return Result.ok();
	}
	
	/**
	 * 角色删除
	 */
	@ResponseBody
	@RequestMapping("role/delete")
	public Object roleDelete(@RequestBody @Valid IdParam param) {
		authService.roleDelete(param);
		return Result.ok();
	}
	
	/**
	 * 角色授权(角色授予模块)
	 */
	@ResponseBody
	@RequestMapping("role/auth")
	public Object roleAuth(@RequestBody @Valid AuthParam param) {
		authService.roleAuth(param);
		return Result.ok();
	}
	
	/**
	 * 角色模块列表
	 */
	@ResponseBody
	@RequestMapping("role/modular/list")
	public Object roleModulars(@RequestBody @Valid IdParam param) {
		return authService.roleModulars(param.getId());
	}
	
	/**
	 * 角色模块树
	 */
	@ResponseBody
	@RequestMapping("role/modular/tree")
	public Object roleModularTree(@RequestBody @Valid IdParam param) {
		List<Modular> modulars = authService.roleModulars(param.getId());
		return AuthUtil.tree(modulars);
	}
	
	/**
	 * 角色模块链
	 */
	@ResponseBody
	@RequestMapping("role/modular/chain")
	public Object roleModularChain(@RequestBody @Valid IdParam param) { 
		List<Modular> modulars = authService.roleModulars(param.getId());
		return AuthUtil.chain(modulars);
	}
	
	/**
	 * 角色授权
	 */
	@ResponseBody
	@RequestMapping("user/auth")
	public Object userAuth(@RequestBody @Valid AuthParam param) { 
		UserInfo user = Assert.notNull(userService.user(param.getId()), UserCode.USER_NOT_EXIST);
		authService.userAuth(user, param);
		return Result.ok();
	}
}
