package org.gear.soa.user.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jupiter.bean.Identifiable;
import org.jupiter.bean.enums.UsernameType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Username implements Identifiable<Long> {

	private static final long serialVersionUID = -3199742953080042101L;
	
	@Id
	@GeneratedValue
	private long id;
	private long uid;
	private int created;
	private int updated;
	private String username;
	private UsernameType type;
	
	@Override
	public Long key() {
		return this.id;
	}
}
