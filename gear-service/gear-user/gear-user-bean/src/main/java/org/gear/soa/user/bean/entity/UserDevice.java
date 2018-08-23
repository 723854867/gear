package org.gear.soa.user.bean.entity;

import javax.persistence.Id;

import org.jupiter.bean.Identifiable;
import org.jupiter.bean.enums.ClientType;
import org.jupiter.bean.enums.DeviceType;
import org.jupiter.bean.enums.OsType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDevice implements Identifiable<String> {

	private static final long serialVersionUID = 5749516172309934954L;
	
	private long uid;
	@Id
	private String id;
	private OsType os;
	private int created;
	private String username;
	private DeviceType type;
	private ClientType client;

	@Override
	public String key() {
		return this.id;
	}
}
