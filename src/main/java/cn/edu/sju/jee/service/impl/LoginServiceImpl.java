package cn.edu.sju.jee.service.impl;

import cn.edu.sju.jee.mapper.TbUserMapper;
import cn.edu.sju.jee.model.TbUser;
import cn.edu.sju.jee.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Override
    public Boolean checkLogin(String userName, String password) {
        TbUser user = tbUserMapper.queryByUsernameAndPassword(userName,password);

        if (null!=user){
            return true;
        }
        return false;
    }
}
