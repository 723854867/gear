package org.gear.courier.service;

import java.util.Map;

import javax.annotation.Resource;

import org.gear.common.Assert;
import org.gear.common.GearConfigs;
import org.gear.common.bean.exception.CodeException;
import org.gear.common.bean.model.Code;
import org.gear.config.api.ConfigService;
import org.gear.courier.KeyGenerator;
import org.gear.courier.api.CourierService;
import org.gear.courier.bean.CourierCode;
import org.gear.courier.internal.CaptchaConfig;
import org.jupiter.bean.model.Pair;
import org.jupiter.redis.ops.LuaOps;
import org.jupiter.redis.ops.StringOps;
import org.jupiter.util.KeyUtil;
import org.jupiter.util.serializer.GsonSerializer;
import org.springframework.stereotype.Service;

@Service("courierService")
public class CourierServiceImpl implements CourierService {

	@Resource
	private LuaOps luaOps;
	@Resource
	private StringOps stringOps;
	@Resource
	private ConfigService configService;
	
	@Override
	public String getCaptcha(String identity) {
		byte[] buffer = stringOps.get(KeyGenerator.captchaKey(identity));
		return null == buffer ? null : new String(buffer);
	}

	@Override
	public void captchaVerify(String... args) {
		Assert.isTrue(args.length > 1 && args.length % 2 == 0, Code.SYS_ERR);
		Object[] params = new Object[args.length];
		for (int index = 0, len = args.length / 2; index < len; index++) {
			params[index] = KeyGenerator.captchaKey(args[index]);
			params[len + index] = args[len + index];
		}
		Assert.isTrue(luaOps.captchaVerify(params), CourierCode.CAPTCHA_ERR);
	}
	
	@Override
	public Pair<String, Boolean> captchaObtain(String identity) {
		Map<String, String> configs = configService.configs(GearConfigs.CAPTCHA_CONFIG_OPTIONS);
		CaptchaConfig config = GsonSerializer.mapToBean(configs, CaptchaConfig.class);
		String captcha = KeyUtil.randomCode(config.getBitNum(), true);
		long flag = luaOps.captchaObtain(KeyGenerator.captchaKey(identity), KeyGenerator.captchaKeyCount(identity), captcha, config.getLifeTime(), config.getCountMaximum(), config.getCountLifeTime(), config.getInterval());
		if (flag == 1)
			throw new CodeException(CourierCode.CAPTCHA_OBTAIN_FREQ);
		if (flag == 2)
			throw new CodeException(CourierCode.CAPTCHA_OBTAIN_COUNT_LIMIT);
		return new Pair<String, Boolean>(captcha, config.isSend());
	}
}
