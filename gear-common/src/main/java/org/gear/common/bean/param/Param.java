package org.gear.common.bean.param;

import java.io.Serializable;

import javax.validation.constraints.Null;

import org.gear.common.Assert;
import org.gear.common.GearCode;
import org.gear.common.bean.model.Code;
import org.gear.common.bean.model.RequestMeta;
import org.gear.common.bean.model.UserInfo;
import org.jupiter.bean.model.query.Query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Param implements Serializable {

	private static final long serialVersionUID = 6835957300544848987L;

	@Null
	private Query query;
	private Integer page;
	private Integer pageSize;
	@Null
	private RequestMeta meta;
	
	public UserInfo getUser() { 
		return Assert.notNull(meta.getUser(), GearCode.UNLOGIN);
	}
	
	public Query getQuery() {
		if (null == query) 
			this.query = new Query();
		if (null != page) {
			this.query.page(page);
			this.query.pageSize(pageSize);
		}
		return this.query;
	}
	
	public void verify() { 
		if (null != page)
			Assert.notNull(this.pageSize, Code.PARAM_ERR);
	}
}
