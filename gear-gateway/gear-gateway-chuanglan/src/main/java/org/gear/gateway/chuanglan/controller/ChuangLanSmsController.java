package org.gear.gateway.chuanglan.controller;

import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gear.common.GearConfigs;
import org.gear.common.bean.model.Result;
import org.gear.config.api.ConfigService;
import org.gear.courier.api.CourierService;
import org.gear.gateway.chuanglan.bean.param.MobileCaptchaObtainParam;
import org.jupiter.bean.model.Option;
import org.jupiter.bean.model.Pair;
import org.jupiter.sdk.chuanglan.api.ChuangLanService;
import org.jupiter.sdk.chuanglan.bean.request.SmsRequest;
import org.jupiter.util.lang.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("sms/chuanglan")
public class ChuangLanSmsController {
	
	@Resource
	private ConfigService configService;
	@Resource
	private CourierService courierService;
	@Resource
	private ChuangLanService chuangLanService;

	/**
	 * 获取验证码：如果应用有多个验证码渠道则无需调用该方法
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("captcha/obtain")
	public Object captchaObtain(@RequestBody @Valid MobileCaptchaObtainParam param) {
		Pair<String, Boolean> captcha = courierService.captchaObtain(param.getMobile());
		if (captcha.getValue()) {
			SmsRequest request = new SmsRequest();
			String msg = configService.config(StringUtil.hasText(param.getType()) ? new Option<String, String>("captcha_msg_" + param.getType(), String.class) : GearConfigs.CAPTCHA_MSG);
			msg = StringUtil.hasText(msg) ? MessageFormat.format(msg, captcha.getKey()) : captcha.getKey();
			request.msg(msg);
			request.appendPhone(param.getMobile());
			chuangLanService.sendSms(request);
			return Result.ok();
		}
		return Result.ok(captcha.getKey());
	}
}
