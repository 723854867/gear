package org.gear.user;

import javax.annotation.Resource;

import org.gear.common.Assert;
import org.gear.common.bean.model.Code;
import org.jupiter.callback.NullCallback;
import org.jupiter.callback.NullParamCallback;
import org.jupiter.redis.DistributeLock;
import org.springframework.stereotype.Component;

@Component
public class ThreadSafeInvoker {

	@Resource
	private DistributeLock distributeLock;
	
	public void userInvoke(long uid, NullCallback callback) { 
		String lockId = userLock(uid, 30000, 60000);
		Assert.hasText(lockId, Code.SERVER_BUSY);
		try {
			callback.invoke();
		} finally {
			userReleaseLock(uid, lockId);
		}
	}
	
	public <V> V userInvoke(long uid, NullParamCallback<V> callback) { 
		String lockId = userLock(uid, 30000, 60000);
		Assert.hasText(lockId, Code.SERVER_BUSY);
		try {
			return callback.invoke();
		} finally {
			userReleaseLock(uid, lockId);
		}
	}
	
	public String userLock(long uid, int wait, int expire) { 
		return distributeLock.lock(KeyGenerator.userKey(uid), wait, expire);
	}
	
	public void userReleaseLock(long uid, String lockId) { 
		Assert.isTrue(distributeLock.releaseLock(KeyGenerator.userKey(uid), lockId), Code.RESOURCE_RELEASE_FAIL);
	}
}
