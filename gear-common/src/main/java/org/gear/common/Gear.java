package org.gear.common;

import org.gear.common.bean.GearConfig;
import org.jupiter.bean.enums.ColumnStyle;
import org.jupiter.util.spring.ConfigLoader;

public class Gear {
	
	private static GearConfig config;

	static {
		config = ConfigLoader.load("classpath*:conf/gear.properties").toBean(GearConfig.class, ColumnStyle.camel2dot);
	}
	
	public static final GearConfig config() {
		return config;
	}
}
