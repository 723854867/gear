package org.gear.auth.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jupiter.bean.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModularApi implements Identifiable<Integer> {

	private static final long serialVersionUID = -631118754051884699L;
	
	@Id
	@GeneratedValue
	private int id;
	private int apiId;
	private int created;
	private int modularId;

	@Override
	public Integer key() {
		return this.id;
	}
}
