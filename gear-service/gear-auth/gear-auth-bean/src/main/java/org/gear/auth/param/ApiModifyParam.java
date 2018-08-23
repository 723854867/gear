package org.gear.auth.param;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.gear.common.Assert;
import org.gear.common.bean.model.Code;
import org.gear.common.bean.param.IdParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiModifyParam extends IdParam {

	private static final long serialVersionUID = 6863872054666658246L;

	@NotEmpty
	private String desc;
	private boolean lock;
	private boolean login;
	private boolean logged;
	@Min(0)
	@Max(180000)
	// 锁定时长不能超过180秒
	private int lockExpire;
	// 获取锁时间最长不能超过30秒
	@Min(0)
	@Max(30000)
	private int lockTimeout;
	private boolean ipFilter;
	@Min(0)
	private int securityLevel;
	private boolean checkAuth;

	@Override
	public void verify() {
		super.verify();
		if (!login) {
			setLock(false);
			setLockExpire(0);
			setCheckAuth(false);
			setLockTimeout(0);
		} else {
			if (!lock) {
				setLockExpire(0);
				setLockTimeout(0);
			} else
				Assert.isTrue(lockExpire > 0 && lockTimeout > 0, Code.PARAM_ERR);
		}
	}
}
