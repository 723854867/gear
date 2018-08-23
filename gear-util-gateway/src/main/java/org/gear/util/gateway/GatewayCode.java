package org.gear.util.gateway;

import org.gear.common.bean.model.Code;

public interface GatewayCode {

	final Code UPLOAD_SIZE_EXCEEDED					= Code.create("code.gateway.upload.size.exceeded", "上传资源太大");
	final Code UNSUPPORT_HTTP_METHOD				= Code.create("code.gateway.unsupport.http.method", "不支持的请求方法");
	final Code UNSUPPORT_CONTENT_TYPE				= Code.create("code.gateway.unsupport.content.type", "不支持的 Content-Type");
}
