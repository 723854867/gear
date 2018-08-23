package org.gear.auth.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jupiter.bean.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Modular implements Identifiable<Integer> {

	private static final long serialVersionUID = -8044033403160019307L;
	
	@Id
	@GeneratedValue
	private int id;
	private int left;
	private int right;
	private int layer;
	private int created;
	private int updated;
	private String name;
	private String trunk;
	
	@Override
	public Integer key() {
		return this.id;
	}
}
