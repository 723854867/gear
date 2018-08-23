package org.gear.soa.user.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jupiter.bean.Identifiable;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户实体类
 * 
 * @author lynn
 */
@Getter
@Setter
public class User implements Identifiable<Long> {

	private static final long serialVersionUID = 6713906317655062713L;
	
	@Id
	@GeneratedValue
	private long id;
	// 登录密码
	private String pwd;
	// 昵称
	private String name;
	// 密码MD加密盐值
	private String salt;
	// 创建时间
	private int created;
	// 修改时间
	private int updated;

	@Override
	public Long key() {
		return this.id;
	}
}
