package org.gear.common.realm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.gear.common.Gear;
import org.gear.common.bean.GearConfig;
import org.gear.common.bean.exception.BootstrapException;
import org.jupiter.util.spring.ConfigLoader;
import org.jupiter.util.spring.ConfigMap;

public class Lang {

	public static final Map<String, String> langs = new HashMap<String, String>();
	
	static {
		GearConfig config = Gear.config();
		ConfigMap map = ConfigLoader.load("classpath*:conf/lang/lang_" + config.getLocale().mark() + ".properties");
		map.entrySet().forEach(entry -> {
			if (langs.containsKey(entry.getKey()) || Collection.class.isAssignableFrom(entry.getValue().getClass())) 
				throw new BootstrapException("lang key - " + entry.getKey() + " duplicated!");
			langs.put(entry.getKey(), entry.getValue().toString());
		});
	}
	
	public static final String get(String key) { 
		return langs.get(key);
	}
}
