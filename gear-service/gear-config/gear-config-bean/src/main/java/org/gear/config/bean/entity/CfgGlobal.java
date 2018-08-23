package org.gear.config.bean.entity;

import javax.persistence.Id;

import org.jupiter.bean.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CfgGlobal implements Identifiable<String> {

	private static final long serialVersionUID = -7846319827310338326L;
	
	@Id
	private String key;
	private int created;
	private int updated;
	private String desc;
	private String value;
	// 只有为 true 客户端才可以获取该参数
	private boolean visible;
	// 是否可编辑：false表示只能程序修改，后台不可编辑
	private boolean editable;
	
	@Override
	public String key() {
		return this.key;
	}
}
