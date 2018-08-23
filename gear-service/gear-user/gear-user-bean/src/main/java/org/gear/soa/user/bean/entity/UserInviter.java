package org.gear.soa.user.bean.entity;

import javax.persistence.Id;

import org.jupiter.bean.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInviter implements Identifiable<String> {

	private static final long serialVersionUID = -8715048613519111230L;
	
	@Id
	private String key;
	private int created;
	private long inviter;
	private long invitee;

	@Override
	public String key() {
		return this.key;
	}
}
