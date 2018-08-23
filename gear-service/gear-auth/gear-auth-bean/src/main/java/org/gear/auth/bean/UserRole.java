package org.gear.auth.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jupiter.bean.Identifiable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 
 * @author lynn
 */
@Getter
@Setter
public class UserRole implements Identifiable<Long> {

	private static final long serialVersionUID = 6180077132223706507L;
	
	@Id
	@GeneratedValue
	private long id;
	private long uid;
	private int roleId;
	private int created;

	@Override
	public Long key() {
		return this.id;
	}
}
