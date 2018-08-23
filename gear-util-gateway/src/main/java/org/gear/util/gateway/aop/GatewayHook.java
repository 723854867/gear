package org.gear.util.gateway.aop;

import org.gear.common.bean.model.RequestMeta;

/**
 * 网关执行的钩子：在执行之前和之后都支持扩展
 * 
 * @author lynn
 */
public interface GatewayHook {

	/**
	 * 在controller执行之前执行
	 * 
	 * @param meta 请求元数据
	 * @param params 请求参数
	 */
	void preProcess(String path, RequestMeta meta, Object[] params);
}
