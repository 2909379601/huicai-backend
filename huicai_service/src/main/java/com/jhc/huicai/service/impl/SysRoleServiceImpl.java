package com.jhc.huicai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhc.huicai.BO.AdminAuthData;
import com.jhc.huicai.DO.SysRole;
import com.jhc.huicai.DO.SysUser;
import com.jhc.huicai.DTO.SysAddUserRoleDto;
import com.jhc.huicai.DTO.SysRoleAddDto;
import com.jhc.huicai.DTO.SysRoleUpdateDto;
import com.jhc.huicai.mapper.SysRoleMapper;
import com.jhc.huicai.mapper.SysUserMapper;
import com.jhc.huicai.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * cms_role  服务实现类
 * </p>
 *
 * @author zeh
 * @since 2021-07-02
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysPermissionServiceImpl permissionService;


    @Override
    public boolean addRole(SysRoleAddDto sysRoleAddDto) {
        SysRole sysRole1 = roleMapper.selectOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getName, sysRoleAddDto.getName()));
        if (ObjectUtil.isNull(sysRole1)) {
            SysRole sysRole = new SysRole();
            BeanUtil.copyProperties(sysRoleAddDto, sysRole);
            int insert = roleMapper.insert(sysRole);
            return insert == 1 ? true : false;
        }
        return false;
    }


    /**
     * 更新角色
     *
     * @return
     */
    @Override
    public boolean updateRole(SysRoleUpdateDto sysRoleUpdateDto) {
        SysRole sysRole = roleMapper.selectOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getId, sysRoleUpdateDto.getId()));
        if (ObjectUtil.isNotNull(sysRole)) {
            if (sysRoleUpdateDto.getName().equals("")&&sysRoleUpdateDto.getRemark().equals("")) { //名称和描述为空时操作为更改权限
                AdminAuthData adminAuthData = new AdminAuthData(); //权限合集
                List<String> collect = sysRoleUpdateDto.getAuthData().stream().distinct().collect(Collectors.toList());//stream去重，生成新的list合集
                adminAuthData.setAuthList(collect);
                sysRole.setAuthData(JSONUtil.toJsonStr(adminAuthData));
            }
            if (!sysRoleUpdateDto.getName().equals("")) { //更改名称
                sysRole.setName(sysRoleUpdateDto.getName());
            }
            if (!sysRoleUpdateDto.getRemark().equals("")) { //更改描述
                sysRole.setRemark(sysRoleUpdateDto.getRemark());
            }
            int update = roleMapper.updateById(sysRole);
            return update == 1 ? true : false;
        }
        return false;
    }

    /**
     * 增加用户角色
     *
     * @return
     */
    @Override
    public boolean addUserRole(SysAddUserRoleDto addUserRoleDto) {
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getNumber, addUserRoleDto.getNumber())); //根据编号查询管理员
        if (ObjectUtil.isNotNull(user)) {
            AdminAuthData admin = JSONUtil.toBean(user.getAuthData(), AdminAuthData.class);
            if (!addUserRoleDto.getAuthData().isEmpty() && addUserRoleDto.getAuthData().get(0) != "") {
                List<AdminAuthData> authDataList = new ArrayList<>();
                AdminAuthData data = new AdminAuthData();
                for (String authDatum : addUserRoleDto.getAuthData()) {
                    //获取角色权限
                    SysRole sysRole = roleMapper.selectOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getName, authDatum));
                    //转换成对象
                    AdminAuthData adminAuthData = JSONUtil.toBean(sysRole.getAuthData(), AdminAuthData.class);
                    adminAuthData.setAuthList(Convert.toList(String.class, sysRole.getId()));
                    authDataList.add(adminAuthData);
                }
                for (AdminAuthData adminAuthData : authDataList) {
                    //将两个List合并添加
                   List<String> auth = (List<String>) CollUtil.union(data.getAuthList(), adminAuthData.getAuthList());
                   data.setAuthList(auth);
                    List<String> role = (List<String>) CollUtil.union(data.getAuthList(), adminAuthData.getAuthList());
                    data.setAuthList(role);
                }
                //去重
                admin.setAuthList(data.getAuthList().stream().distinct().collect(Collectors.toList()));
                admin.setAuthList(data.getAuthList().stream().distinct().collect(Collectors.toList()));
                user.setAuthData(JSONUtil.toJsonStr(admin));
            } else {
                //如果没有角色，那就没有权限
                admin.getAuthList().clear();
                admin.getAuthList().clear();
                user.setAuthData(JSONUtil.toJsonStr(admin));
            }
            // 清除token
            if (userMapper.updateById(user) == 1) {
                permissionService.clearToken(true, user.getId());
                return true;
            }
        }
        return false;
    }

}