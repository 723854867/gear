package org.gear.user.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.gear.soa.user.bean.entity.User;
import org.jupiter.mybatis.Dao;

public interface UserDao extends Dao<Long, User> {

	@Update("UPDATE `user` SET `pwd`=#{pwd}, `name`=#{name}, `updated`=#{time} WHERE `id`=#{uid} AND `updated`=#{otime}")
	int updateByInfo(@Param("uid") long uid, @Param("name") String name, @Param("pwd") String pwd, @Param("otime") int otime, @Param("time") int time);
}
