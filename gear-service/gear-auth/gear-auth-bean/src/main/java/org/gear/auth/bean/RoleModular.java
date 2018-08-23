package org.gear.auth.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jupiter.bean.Identifiable;

import lombok.Getter;
import lombok.Setter;

/**
 * roleId和modularId唯一索引
 * 
 * @author lynn
 */
@Getter
@Setter
public class RoleModular implements Identifiable<Long> {

	private static final long serialVersionUID = -1360488406112083756L;
	
	@Id
	@GeneratedValue
	private long id;
	private int roleId;
	private int created;
	// 就是trunk
	private int modularId;

	@Override
	public Long key() {
		return this.id;
	}
}
