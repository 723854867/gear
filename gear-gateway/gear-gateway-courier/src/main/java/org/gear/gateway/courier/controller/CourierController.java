package org.gear.gateway.courier.controller;

import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gear.common.GearConfigs;
import org.gear.common.bean.model.Result;
import org.gear.common.bean.param.EmailParam;
import org.gear.common.bean.param.MobileParam;
import org.gear.config.api.ConfigService;
import org.gear.courier.api.CourierService;
import org.gear.gateway.courier.bean.param.EmailCaptchaObtainParam;
import org.jupiter.bean.model.Option;
import org.jupiter.bean.model.Pair;
import org.jupiter.util.lang.StringUtil;
import org.jupiter.util.spring.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CourierController {
	
	@Autowired(required = false)
	private MailSender mailSender;
	@Resource
	private ConfigService configService;
	@Resource
	private CourierService courierService;
	
	@ResponseBody
	@RequestMapping("captcha/obtain/email")
	public Object captchaObtainEmail(@RequestBody @Valid EmailCaptchaObtainParam param) {
		Pair<String, Boolean> captcha = courierService.captchaObtain(param.getEmail());
		if (captcha.getValue() && null != mailSender) {
			String msg = configService.config(StringUtil.hasText(param.getType()) ? new Option<String, String>("captcha_msg_" + param.getType(), String.class) : GearConfigs.CAPTCHA_MSG);
			msg = StringUtil.hasText(msg) ? MessageFormat.format(msg, captcha.getKey()) : captcha.getKey();
			String title = configService.config(StringUtil.hasText(param.getType()) ? new Option<String, String>("captcha_msg_title" + param.getType(), String.class) : GearConfigs.CAPTCHA_MSG_TITLE);
			mailSender.sendMail(param.getEmail(), title, msg);
			return Result.ok();
		}
		return Result.ok(captcha.getKey());
	}
	
	/**
	 * 邮箱查询验证码
	 */
	@ResponseBody
	@RequestMapping("captcha/query/email")
	public Object captchaQueryEmail(@RequestBody @Valid EmailParam param) { 
		return courierService.getCaptcha(param.getEmail());
	}
	
	/**
	 * 手机查询验证码
	 */
	@ResponseBody
	@RequestMapping("captcha/query/mobile")
	public Object captchaQueryMobile(@RequestBody @Valid MobileParam param) { 
		return courierService.getCaptcha(param.getMobile());
	}
}
