package cn.edu.sju.jee.mapper;

import cn.edu.sju.jee.model.TbUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TbUserMapper {
    TbUser queryByUsernameAndPassword(@Param("userName") String userName,
                                      @Param("password") String password);
}
