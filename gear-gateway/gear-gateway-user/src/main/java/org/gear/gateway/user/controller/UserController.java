package org.gear.gateway.user.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.gear.common.bean.model.Result;
import org.gear.common.bean.model.UserInfo;
import org.gear.gateway.user.bean.param.UserModifyParam;
import org.gear.soa.user.api.UserService;
import org.jupiter.util.lang.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Resource
	private UserService userService;

	@RequestMapping("modify")
	public Object modify(@RequestBody @Valid UserModifyParam param) { 
		UserInfo user = param.getUser();
		boolean modify = false;
		if (StringUtil.hasText(param.getPwd())) {
			modify = true;
			user.setPwd(DigestUtils.md5Hex(param.getPwd() + "_" + user.getSalt()));
		}
		if (StringUtil.hasText(param.getName())) {
			modify = true;
			user.setName(param.getName());
		}
		if (modify)
			userService.update(user);
		return Result.ok();
	}
}
