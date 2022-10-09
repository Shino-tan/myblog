package com.tanzheng.myblog.service.impl;

import com.tanzheng.myblog.entity.Admin;
import com.tanzheng.myblog.mapper.AdminMapper;
import com.tanzheng.myblog.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 诗乃
 * @since 2022-09-03
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
