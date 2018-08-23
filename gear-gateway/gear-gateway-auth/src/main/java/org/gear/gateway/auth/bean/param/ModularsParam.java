package org.gear.gateway.auth.bean.param;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;

import org.gear.common.bean.param.Param;
import org.jupiter.bean.model.query.Condition;
import org.jupiter.bean.model.query.Query;
import org.jupiter.util.lang.CollectionUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModularsParam extends Param {

	private static final long serialVersionUID = -5842349058195745404L;
	
	@Min(1)
	private Integer id;
	private String name;

	@Override
	public void verify() {
		super.verify();
		Query query = getQuery();
		List<Condition> list = new ArrayList<Condition>();
		if (null != id)
			list.add(Condition.eq("id", id));
		if (null != name)
			list.add(Condition.like("name", name));
		if (!CollectionUtil.isEmpty(list))
			query.and(list);
	}
}
