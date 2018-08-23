package org.gear.auth.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.gear.auth.bean.Api;
import org.jupiter.mybatis.Dao;

public interface ApiDao extends Dao<Integer, Api> {

	@Select("SELECT * FROM api WHERE id IN(SELECT api_id FROM modular_api WHERE modular_id=#{modularId})")
	List<Api> modularApis(int modularId);
}
