package org.gear.config.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.gear.config.bean.entity.CfgGlobal;
import org.gear.config.mybatis.dao.CfgGlobalDao;
import org.jupiter.bean.model.query.Query;
import org.springframework.stereotype.Component;

@Component
public class ConfigManager {

	@Resource
	private CfgGlobalDao cfgGlobalDao;
	
	public CfgGlobal cfgGlobal(String key) {
		return cfgGlobalDao.selectByKey(key);
	}
	
	public Map<String, CfgGlobal> cfgGlobals(Query query) {
		return cfgGlobalDao.queryMap(query);
	}
	
	public Map<String, CfgGlobal> cfgGlobals(Collection<String> keys) {
		return cfgGlobalDao.selectByKeys(keys);
	}
}
