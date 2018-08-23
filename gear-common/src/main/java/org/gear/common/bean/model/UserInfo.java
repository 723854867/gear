package org.gear.common.bean.model;

import java.io.Serializable;

import org.jupiter.bean.enums.ClientType;
import org.jupiter.bean.enums.DeviceType;
import org.jupiter.bean.enums.OsType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo implements Maskable {

	private static final long serialVersionUID = -5788426605105869663L;

	private long uid;
	private String pwd;
	private String salt;
	private String name;
	private int updated;
	private String lockId;
	private Device device;

	@Override
	public void mask() {
		this.pwd = null;
		this.salt = null;
		this.lockId = null;
	}
	
	@Getter
	@Setter
	public static final class Device implements Serializable {
		
		private static final long serialVersionUID = -4949405123144544466L;
		
		private String id;
		private OsType os;
		private int created;
		private String username;
		private DeviceType type;
		private ClientType client;
	}
}
