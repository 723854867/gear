package org.gear.auth.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jupiter.bean.Identifiable;

import lombok.Getter;
import lombok.Setter;

/**
 * Root 角色全局只有一个，而且只能分配给一个用户
 * 
 * @author lynn
 */
@Getter
@Setter
public class Role implements Identifiable<Integer> {

	private static final long serialVersionUID = 7774181422512788494L;
	
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private int created;
	private int updated;
	
	@Override
	public Integer key() {
		return this.id;
	}
}
