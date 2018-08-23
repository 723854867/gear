package org.gear.config.bean;

import org.jupiter.bean.model.Option;

public interface GlobalConfigs {

	final Option<String, Integer> SERVER_SECURITY_LEVEL		= new Option<String, Integer>("server_security_level", 1);
}
