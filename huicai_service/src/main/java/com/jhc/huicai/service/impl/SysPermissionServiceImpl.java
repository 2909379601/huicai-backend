package com.jhc.huicai.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhc.huicai.BO.AdminAuthData;
import com.jhc.huicai.DO.SysPermission;
import com.jhc.huicai.DO.SysRole;
import com.jhc.huicai.DO.SysRoleChange;
import com.jhc.huicai.DO.SysUser;
import com.jhc.huicai.DTO.SysPermissionAdd;
import com.jhc.huicai.DTO.SysUserPermissionAdd;
import com.jhc.huicai.VO.SysMenuVo;
import com.jhc.huicai.VO.SysTreePermissionVo;
import com.jhc.huicai.enums.PermissionType;
import com.jhc.huicai.mapper.SysPermissionMapper;
import com.jhc.huicai.mapper.SysRoleMapper;
import com.jhc.huicai.mapper.SysUserMapper;
import com.jhc.huicai.service.ISysPermissionService;
import com.jhc.huicai.service.RedisService;
import com.jhc.huicai.utils.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * cms_permission  服务实现类
 * </p>
 *
 * @author zhengyue
 * @since 2020-05-18
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysPermissionMapper permissionMapper;
    @Autowired
    private RedisService redisService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUser.class);

    @Override
    public List<String> getPermission(String number,String type) {
        List<String> result = new ArrayList<>();
        // 查询用户权限值
        SysUser cmsAdmin = sysUserMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getNumber, number));
        if (cmsAdmin == null||ObjectUtil.isEmpty(cmsAdmin.getAuthData())) {
            return new ArrayList<>();
        }
        // 字符串生成对象
        AdminAuthData sysUserAuthData = JSONUtil.toBean(cmsAdmin.getAuthData(), AdminAuthData.class);
        AdminAuthData permissionData = new AdminAuthData(); // 结果权限
        // 角色列表
        List<String> roleList = sysUserAuthData.getAuthList();
        if (roleList.size() != 0) {
            //获取角色权限
            List<SysRole> authRoleList = roleMapper.selectList(new QueryWrapper<SysRole>().lambda()
                    .select(SysRole::getAuthData)
                    .in(SysRole::getId, roleList));    //拿到角色中的权限值并保存
            List<String> result1 = new ArrayList<>();
            for (SysRole sysUserRole : authRoleList) {
                if (ObjectUtil.isEmpty(sysUserRole)){
                    break;
                }
                AdminAuthData roleAuth = JSONUtil.toBean(sysUserRole.getAuthData(), AdminAuthData.class);
                if (roleAuth.getAuthList().size()!=0) {
                    roleAuth.getAuthList().stream().forEach(
                            roleAuth1->{
                                result1.add(roleAuth1); //整合角色中的权限id值
                            }
                    );
                }
            }
            permissionData.setAuthList(result1); //取得权限值
        }
        List<String> resultPermissionList = new ArrayList<>();
        if(permissionData.getAuthList()!=null){
            resultPermissionList = permissionData.getAuthList().stream().distinct().collect(Collectors.toList()); // 去重
        }
        // 查出权限列表
        if (resultPermissionList.size() != 0) {
            List<SysPermission> sysPermissions = permissionMapper.selectList(new QueryWrapper<SysPermission>().lambda()
                    .in(SysPermission::getId, resultPermissionList));
            for (SysPermission sy : sysPermissions) {
                if (sy.getType().equals(PermissionType.BUTTON.getCode())) {
                    result.add(sy.getValue());
                }
            }
        }
        if(type.equals("0")){
            return result;
        }else if(type.equals("1")){//权限个数
            List<String> buttonCount = new ArrayList<>();
            buttonCount.add(ObjectUtil.toString(result.size()));
            return buttonCount;
        }
        return result;
    }

    @Override
    public List<SysMenuVo> sysTreePermissions(String number) {
        // 查询用户权限值
        SysUser cmsAdmin = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda()
                .eq(SysUser::getNumber, number));
        if (cmsAdmin == null) {
            return new ArrayList<>();
        }
        // 字符串生成对象
        AdminAuthData sysUserAuthData = JSONUtil.toBean(cmsAdmin.getAuthData(), AdminAuthData.class);
        // 权限列表
        AdminAuthData permissionData = new AdminAuthData(); // 结果权限

        // 角色列表
        List<String> roleList = sysUserAuthData.getAuthList();
        // 查询角色 拿到角色表中的权限值
        if (roleList.size() != 0) {
            //获取角色权限
            List<SysRole> authRoleList = roleMapper.selectList(new QueryWrapper<SysRole>().lambda()
                    .select(SysRole::getAuthData)
                    .in(SysRole::getId, roleList));    //拿到角色中的权限值并保存
            List<String> result1 = new ArrayList<>();
            for (SysRole sysUserRole : authRoleList) {
                AdminAuthData roleAuth = JSONUtil.toBean(sysUserRole.getAuthData(), AdminAuthData.class);
                if (roleAuth.getAuthList().size()!=0) {
                    roleAuth.getAuthList().stream().forEach(
                            roleAuth1->{
                                result1.add(roleAuth1); //整合角色中的权限id值
                            }
                    );

                }
            }
            permissionData.setAuthList(result1); //取得权限值
        }
        // 去重
        List<String> resultPermissionList = permissionData.getAuthList().stream().distinct().collect(Collectors.toList()); //结果权限列表
        // 查出权限列表
        if (resultPermissionList.size() != 0) {
            List<SysPermission> sysPermissions = permissionMapper.selectList(new QueryWrapper<SysPermission>().lambda().in(SysPermission::getId, resultPermissionList)); //权限列表
            List<SysMenuVo> sysTreePermissionList = new ArrayList<>();
            List<SysMenuVo> originData = new ArrayList<>();
            for (SysPermission sysPermission : sysPermissions) {//遍历菜单权限
                if (!sysPermission.getType().equals(PermissionType.BUTTON.getCode())) {
                    SysMenuVo sysTreePermissionVo = new SysMenuVo();
                    BeanUtils.copyProperties(sysPermission, sysTreePermissionVo);
                    sysTreePermissionVo.setLabel(sysPermission.getName());
                    originData.add(sysTreePermissionVo);
                }
            }
            originData.sort(new Comparator<SysMenuVo>() {
                @Override
                public int compare(SysMenuVo o1, SysMenuVo o2) {
                    return o2.getSort() - o1.getSort();
                }
            });
            for (int i = 0; i < originData.size(); i++) {
                if (originData.get(i).getParentId() == 0) {
                    sysTreePermissionList.add(originData.get(i));
                    continue;
                }
                for (SysMenuVo originDatum : originData) {
                    if (originData.get(i).getParentId().equals(originDatum.getId())) {
                        if (originDatum.getChildren() == null) {
                            originDatum.setChildren(new ArrayList<>());
                        }
                        originDatum.getChildren().add(originData.get(i));
                    }
                }
            }
            return sysTreePermissionList;
        }
        return null;
    }


    /**
     * 根据ID查找用户id和身份信息
     *
     * @param id 用户ID
     * @return 带有id和身份信息的实体对象
     */
    private SysUser getUserAuth(Long id) {
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>()
                .select("auth_data", "id").lambda()
                .eq(SysUser::getId, id);
        return sysUserMapper.selectOne(queryWrapper);
    }

    public void clearToken(boolean ifFast, Long id) {
        // 判断是否马上登出
        SysUser cmsUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda()
                .select(SysUser::getNumber)
                .eq(SysUser::getId, id));
        if (ifFast && cmsUser != null) {
            redisService.remove(cmsUser.getNumber());
        } else if (!ifFast && cmsUser != null) {
            redisService.expire(cmsUser.getNumber(), 60 * 60);
        }
    }

    @Override
    public boolean userAddPermission(SysUserPermissionAdd permissionAdd) {
        SysUser cmsUser = sysUserMapper.selectById(permissionAdd.getId());
        if (cmsUser == null) {
            return false;
        }
        // 拿出原本的角色 + 权限 字符串
        String authStr = cmsUser.getAuthData();
        // 把字符串转对象
        AdminAuthData sysUserAuthData = JSONUtil.toBean(authStr, AdminAuthData.class);
        // 把新的权限列表设置到对象中
        sysUserAuthData.setAuthList(permissionAdd.getAuthList());
        // 把对象转化成json设置到对象中
        cmsUser.setAuthData(JSONUtil.toJsonStr(sysUserAuthData));
        // 清除token
        if (sysUserMapper.updateById(cmsUser) == 1) {
            clearToken(permissionAdd.getIfFast(), permissionAdd.getId());
            return true;
        }
        return false;
    }

    @Override
    public CommonResult treePermission() {
        List<SysPermission> permissionList = baseMapper.selectList(new QueryWrapper<SysPermission>().lambda()
                .orderByDesc(SysPermission::getSort)); //得到所有的权限
        List<SysTreePermissionVo> treeVoList = getPermissionTree(permissionList);
        return CommonResult.success(treeVoList);

    }

    @Override
    @Transactional
    public Boolean deleteByIds(List<Long> permissionId) {
        for (Long premission : permissionId) {
            SysPermission sysPermission = baseMapper.selectOne(new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getId, premission)); //得到需要删除的权限id
            if (sysPermission == null) {//非空判断
                return false;
            }
            if (sysPermission.getType() == 0) { //目录
                List<SysPermission> menuList = baseMapper.selectList(new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getParentId, sysPermission.getId())); //拿到菜单
                menuList.stream().forEach(
                        menu -> {
                            List<SysPermission> buttonList = baseMapper.selectList(new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getParentId, menu.getId())); //拿到按钮列表
                            buttonList.stream().forEach(
                                    buttonId -> {
                                        baseMapper.deleteById(buttonId.getId()); //删除菜单下的按钮
                                    }
                            );
                            baseMapper.deleteById(menu.getId()); //删除菜单
                        }
                );
                baseMapper.deleteById(sysPermission.getId());//删除目录
            }
            if (sysPermission.getType() == 1) { //菜单
                List<SysPermission> buttonList = baseMapper.selectList(new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getParentId, sysPermission.getId()));
                buttonList.stream().forEach(
                        button -> {
                            baseMapper.deleteById(button.getId()); //删除菜单下的按钮
                        }
                );
                baseMapper.deleteById(premission);//删除菜单
            }

            if (sysPermission.getType() == 2) { //按钮
                baseMapper.delete(new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getId, sysPermission.getId()));//删除按钮
            }
        }
        return true;
    }


    @Override
    public CommonResult getByRolePermission(Long number) {
        SysRole sysRole = roleMapper.selectPermission(number);
        SysRoleChange sysRoleChange = JSONUtil.toBean(sysRole.getAuthData(), SysRoleChange.class);
        List<String> resultId = new ArrayList<>();
        List<SysPermission> permissionList = baseMapper.selectList(new QueryWrapper<SysPermission>().lambda()
                .in(SysPermission::getId, sysRoleChange.getAuthList())); //通过角色权限id   获取权限列表
        List<SysTreePermissionVo> permissionTreeVoList = getPermissionTree(permissionList); //生成树状结构
        getBelow(permissionTreeVoList, resultId);
        return CommonResult.success(resultId);
    }

    @Override
    public CommonResult addPermission(SysPermissionAdd permissionAdd) {
        if (baseMapper.selectOne(new QueryWrapper<SysPermission>().lambda()
                .eq(SysPermission::getName, permissionAdd.getName())) != null) { //查重
            return CommonResult.failed("名称重复，权限已存在");
        }
        ;
        SysPermission cmsPermission = new SysPermission();
        BeanUtils.copyProperties(permissionAdd, cmsPermission);
        int insert = baseMapper.insert(cmsPermission);//插入权限
        if (insert > 0) {
            SysPermission sysPermission = baseMapper.selectOne(new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getName, permissionAdd.getName()));
            String id = sysPermission.getId().toString();  //得到新增权限的id
            SysRole admin = roleMapper.selectOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getName, "管理员"));
            String authData = admin.getAuthData();
            AdminAuthData adminAuthData = JSONUtil.toBean(authData, AdminAuthData.class);//原来的权限
            List<String> authList = adminAuthData.getAuthList();
            authList.add(id);
            adminAuthData.setAuthList(authList);//加入新的权限
            admin.setAuthData(JSONUtil.toJsonStr(adminAuthData));
            int i = roleMapper.updateById(admin);
            if (i > 0) {
                return CommonResult.success("新增权限成功！");
            }
            return CommonResult.failed("新增权限成功，动态添加菜单失败！");
        }
        return CommonResult.failed("新增失败！");
    }


    private void getBelow(List<SysTreePermissionVo> permissionTreeVoList, List<String> belowId) {
        for (SysTreePermissionVo permissionTreeVo : permissionTreeVoList) { //遍历树状结构权限列表
            if (permissionTreeVo.getChildren() == null) { //如果没有子权限
                belowId.add(String.valueOf(permissionTreeVo.getId())); //结果中添加树状Id
            } else {
                getBelow(permissionTreeVo.getChildren(), belowId); //有，递归再次判断，转换为平面结构
            }
        }
    }


    private List<SysTreePermissionVo> getPermissionTree(List<SysPermission> permissionList) {
        List<SysTreePermissionVo> treeVoList = new ArrayList<>();
        List<SysTreePermissionVo> permissionTreeVoListOri = new ArrayList<>();
        for (SysPermission permission : permissionList) {
            SysTreePermissionVo permissionTreeVo = new SysTreePermissionVo();
            BeanUtils.copyProperties(permission, permissionTreeVo);
            permissionTreeVoListOri.add(permissionTreeVo);
        }
        // match parents and children
        for (SysTreePermissionVo permissionTreeVo : permissionTreeVoListOri) {
            if (permissionTreeVo.getParentId() == 0) {  //加入菜单列表
                treeVoList.add(permissionTreeVo);
                continue;
            }
            for (SysTreePermissionVo treeVo : permissionTreeVoListOri) { //按钮列表的父id
                if (treeVo.getId().equals(permissionTreeVo.getParentId())) { //菜单的子菜单与按钮匹配
                    if (treeVo.getChildren() == null) {
                        treeVo.setChildren(new ArrayList<>()); //按钮加入菜单的子菜单
                    }
                    treeVo.getChildren().add(permissionTreeVo);  //菜单的子孩子再加入一个按钮
                }
            }
        }
        return treeVoList;
    }
}