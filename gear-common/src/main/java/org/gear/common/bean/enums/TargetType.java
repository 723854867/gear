package org.gear.common.bean.enums;

import org.jupiter.bean.IEnum;

public enum TargetType implements IEnum<Integer> {
	
	// 用户
	USER(1),
	// 雇员
	EMPLOYEE(2);
	
	private int mark;
	
	private TargetType(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return this.mark;
	}
}
