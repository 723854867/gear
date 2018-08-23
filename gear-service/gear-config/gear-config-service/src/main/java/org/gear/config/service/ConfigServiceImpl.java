package org.gear.config.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.gear.config.api.ConfigService;
import org.gear.config.bean.entity.CfgGlobal;
import org.gear.config.manager.ConfigManager;
import org.jupiter.bean.model.Option;
import org.jupiter.bean.model.query.Query;
import org.jupiter.util.spring.ConfigLoader;
import org.springframework.stereotype.Service;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {
	
	@Resource
	private ConfigManager configManager;

	@Override
	public <T> T config(Option<String, T> option) {
		CfgGlobal cfgGlobal = configManager.cfgGlobal(option.key());
		return null == cfgGlobal ? option.value() : ConfigLoader.CONVERTER.convert(cfgGlobal.getValue(), option.clazz());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> configs(Option<String, ?>... options) {
		Set<String> set = new HashSet<String>();
		for (Option<String, ?> option : options)
			set.add(option.key());
		Map<String, String> map = new HashMap<String, String>();
		Map<String, CfgGlobal> configs = configManager.cfgGlobals(set);
		for (Option<String, ?> option : options) {
			CfgGlobal config = configs.get(option.key());
			Object value = null == config ? option.value() : config.getValue();
			if (null != value)
				map.put(option.key(), value.toString());
		}
		return map;
	}
	
	@Override
	public Map<String, CfgGlobal> configs(Query query) {
		return configManager.cfgGlobals(query);
	}
}
