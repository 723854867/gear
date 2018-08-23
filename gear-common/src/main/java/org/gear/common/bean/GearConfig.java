package org.gear.common.bean;

import java.io.Serializable;

import org.gear.common.bean.enums.Locale;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GearConfig implements Serializable {

	private static final long serialVersionUID = 7310352556364563773L;

	private Locale locale = Locale.ZH_CN;
}
