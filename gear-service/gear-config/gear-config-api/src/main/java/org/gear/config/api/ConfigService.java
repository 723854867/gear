package org.gear.config.api;

import java.util.Map;

import org.gear.config.bean.entity.CfgGlobal;
import org.jupiter.bean.model.Option;
import org.jupiter.bean.model.query.Query;

public interface ConfigService {
	
	<T> T config(Option<String, T> option);
	
	Map<String, CfgGlobal> configs(Query query);
	
	@SuppressWarnings("unchecked") 
	Map<String, String> configs(Option<String, ?>... options);
}
