package com.captain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.captain.entity.po.User;
import com.captain.entity.vo.RoleVo;
import com.captain.entity.vo.UserVo;
import com.captain.mapper.UserMapper;
import com.captain.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lianhong
 * @since 2020-08-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>()
                .eq("username",username));
    }

    @Override
    public Page getVoList(Page page, QueryWrapper<UserVo> wrapper) {
        List<UserVo> voList = baseMapper.getVoList(page,wrapper);
        return new Page().setRecords(voList);
    }
}
