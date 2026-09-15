package com.captain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.captain.entity.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.captain.entity.vo.RoleVo;
import com.captain.entity.vo.UserVo;

import javax.management.Query;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lianhong
 * @since 2020-08-22
 */
public interface UserService extends IService<User> {

    User getByUsername(String username);

    Page getVoList(Page page,  QueryWrapper<UserVo> wrapper);

}
