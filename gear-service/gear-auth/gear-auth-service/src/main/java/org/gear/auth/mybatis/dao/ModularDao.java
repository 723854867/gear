package org.gear.auth.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;
import org.gear.auth.bean.Modular;
import org.jupiter.mybatis.Dao;

public interface ModularDao extends Dao<Integer, Modular> {

	@MapKey("id")
	@Select("SELECT * FROM modular WHERE trunk=(SELECT trunk FROM modular WHERE id=#{id}) FOR UPDATE")
	Map<Integer, Modular> lockById(int id);
	
	@Select("SELECT * FROM modular WHERE id IN(SELECT modular_id FROM role_modular WHERE role_id=#{roleId})")
	List<Modular> roleModulars(int roleId);
}
