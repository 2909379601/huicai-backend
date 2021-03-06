package com.jhc.huicai.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhc.huicai.BO.AdminAuthData;
import com.jhc.huicai.DO.BasStudent;
import com.jhc.huicai.DO.BasTeacher;
import com.jhc.huicai.DO.SysRole;
import com.jhc.huicai.DO.SysUser;
import com.jhc.huicai.DTO.*;
import com.jhc.huicai.VO.RedisUserInfo;
import com.jhc.huicai.VO.SysUserSearchVO;
import com.jhc.huicai.VO.SysUserVO;
import com.jhc.huicai.mapper.BasStudentMapper;
import com.jhc.huicai.mapper.BasTeacherMapper;
import com.jhc.huicai.mapper.SysRoleMapper;
import com.jhc.huicai.mapper.SysUserMapper;
import com.jhc.huicai.service.ISysPermissionService;
import com.jhc.huicai.service.ISysUserService;
import com.jhc.huicai.service.RedisService;
import com.jhc.huicai.utils.CommonResult;
import com.jhc.huicai.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:38
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.expiration}")
    private Long redisTime;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ISysPermissionService permissionService;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BasStudentMapper studentMapper;
    @Autowired
    private BasTeacherMapper teacherMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUser.class);
    private static final String CACHE_PUNCH_REGION = "USER";

    @Override
    public RedisUserInfo login(String number, String password) {
        String token = null;
        RedisUserInfo redisUserInfo = new RedisUserInfo();
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(number);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("???????????????");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtUtils.generateToken(userDetails);

            // ????????????????????????????????????  0?????????????????? 1????????????????????????

            List<String> permissionValue = permissionService.getPermission(number, "0");
            redisUserInfo.setNumber(number);
            //??????????????????
            SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getNumber, number));
            redisUserInfo.setId(user.getId());
            redisUserInfo.setName(user.getName());
            if (ObjectUtil.isNotEmpty(permissionValue)){
                redisUserInfo.setPermissionValueList(permissionValue);
            }
            redisUserInfo.setPhoto("e27887b5a672f309ce7da0a8f7436583");
            redisUserInfo.setToken(tokenHead + token);
            redisService.set(CACHE_PUNCH_REGION, number, JSON.toJSONString(redisUserInfo));  //?????????redis???
            redisService.expire(number, redisTime);
        } catch (Exception e) {
            LOGGER.warn("????????????:{}", e.getMessage());
        }
        return redisUserInfo;
    }
    @Override
    public CommonResult registerAll() {
        List<SysUserRegisterDto> sysUserRegisterDtos = new ArrayList<>();
        List<BasStudent> basStudents = studentMapper.selectList(new QueryWrapper<BasStudent>().lambda().eq(BasStudent::getIsEnable, 1));
        List<BasTeacher> basTeachers = teacherMapper.selectList(null);
        for (BasTeacher basTeacher : basTeachers) {
            SysUserRegisterDto sysUserRegisterDto = new SysUserRegisterDto();
            sysUserRegisterDto.setName(basTeacher.getName());
            sysUserRegisterDto.setNumber(basTeacher.getNumber());
            sysUserRegisterDto.setSex(basTeacher.getSex());
            sysUserRegisterDto.setIsStudent(false);
            sysUserRegisterDto.setPassword("000000");
            sysUserRegisterDtos.add(sysUserRegisterDto);
        }
        for (BasStudent basStudent : basStudents) {
            SysUserRegisterDto sysUserRegisterDto = new SysUserRegisterDto();
            sysUserRegisterDto.setName(basStudent.getName());
            sysUserRegisterDto.setNumber(basStudent.getNumber());
            sysUserRegisterDto.setSex(basStudent.getSex());
            sysUserRegisterDto.setIsStudent(true);
            sysUserRegisterDto.setPassword("000000");
            sysUserRegisterDtos.add(sysUserRegisterDto);
        }
        for (SysUserRegisterDto sysUserRegisterDto : sysUserRegisterDtos) {
            this.register(sysUserRegisterDto);
        }

        return null;
    }

    @Override
    public CommonResult register(SysUserRegisterDto registerDto) {
        if (StringUtils.isEmpty(registerDto.getPassword())) {
            registerDto.setPassword("000000");  //??????????????????
        }
        SysUser userInfo = userMapper.selectOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getNumber, registerDto.getNumber()));

        if (ObjectUtil.isNull(userInfo)) {
//            SysRole sysRole = null;
            SysUser cmsUser = new SysUser();
////            ????????????
//            if (!redisService.get(registerDto.getNumber()+registerDto.getEmail()).equals(registerDto.getPin())){
//                return CommonResult.failed("?????????????????????????????????");
//            }
            //            ????????????
            BeanUtils.copyProperties(registerDto, cmsUser);
//            ????????????
            if (registerDto.getIsStudent()) { //??????
                List<BasStudent> basStudents = studentMapper.selectList(new QueryWrapper<BasStudent>().lambda()
                        .eq(BasStudent::getNumber, cmsUser.getNumber()).eq(BasStudent::getIsEnable, 1));
                BasStudent studentInfo = basStudents.get(0);
                if (!ObjectUtil.isNotNull(basStudents)) {
                    return CommonResult.failed("??????????????????");
                }
                if (!ObjectUtil.equal(studentInfo.getName(), cmsUser.getName())) {
                    return CommonResult.failed("????????????????????????");
                }
//                cmsUser.setSex(studentInfo.getSex());
//                sysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getName, "??????")); //??????????????????
//                cmsUser.setClassNumber(studentInfo.getClassNumber());
//                cmsUser.setMajorNumber(studentInfo.getMajorNumber());

            } else { //??????
                BasTeacher teacherInfo = teacherMapper.selectOne(new QueryWrapper<BasTeacher>().lambda()
                        .eq(BasTeacher::getNumber, cmsUser.getNumber()));
                if (!ObjectUtil.isNotNull(teacherInfo)) {
                    return CommonResult.failed("????????????????????????");
                }
//                cmsUser.setSex(teacherInfo.getSex());
                if (!ObjectUtil.equal(teacherInfo.getName(), cmsUser.getName())) {
                    return CommonResult.failed("????????????????????????");
                }
            }
            //????????????
            //????????????????????????

            SysRole role = roleMapper.selectOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getName, "????????????"));//??????????????????
            List<String> roleId = new ArrayList<>();
            roleId.add(role.getId().toString());
            AdminAuthData adminAuthData = new AdminAuthData();
            adminAuthData.setAuthList(roleId);
            cmsUser.setAuthData(JSONUtil.toJsonStr(adminAuthData));
            cmsUser.setPassword(passwordEncoder.encode(cmsUser.getPassword()));
            cmsUser.setSex(registerDto.getSex());
            int result = userMapper.insert(cmsUser);
            if (result == 1) {
                return CommonResult.success("????????????");
            }
        }
        return CommonResult.failed("?????????????????????????????????");

    }

    @Override
    public CommonResult updatePersonalPassword(SysUpdatePersonalPasswordDto cmsUpdatePasswordDto) {
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getNumber, cmsUpdatePasswordDto.getNumber()));
        if (user == null) {
            return CommonResult.failed("???????????????");
        }
        if (!passwordEncoder.matches(cmsUpdatePasswordDto.getOldPassword(), user.getPassword())) {
            return CommonResult.failed("???????????????");
        }
        if (cmsUpdatePasswordDto.getNewPassword().length() < 6) {
            return CommonResult.failed("???????????????????????????6");
        }
        user.setPassword(passwordEncoder.encode(cmsUpdatePasswordDto.getNewPassword()));
        int update = userMapper.updateById(user);
        if (update == 1) {
            redisService.remove(user.getNumber()); //??????redis??????
        }
        if (update == 0) {
            return CommonResult.failed("??????????????????");
        }
        return CommonResult.success("????????????");
    }

    @Override
    public Boolean resetPas(List<String> numbers) {
        List<SysUser> users = userMapper.selectList(new QueryWrapper<SysUser>().lambda()
                .in(SysUser::getNumber, numbers));
        if (users.size() != numbers.size()) {
            return false;
        }
        for (SysUser user : users) {
            user.setPassword(passwordEncoder.encode("000000"));
            int update = userMapper.updateById(user);
            if (update == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public IPage<SysUserVO> getAllUserPageData(SysUserDto allSysUserDto) {
        Page page = new Page(allSysUserDto.getCurrentPage(), allSysUserDto.getPageSize());
        IPage<SysUserVO> result = userMapper.getAllUserPageData(page, allSysUserDto);
        return result;
    }

    @Override
    public CommonResult getUserInfoByUserNum(String number) {
        SysUser cmsUser = userMapper.selectOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getNumber, number));
        if (ObjectUtil.isNotEmpty(cmsUser)) {
            return CommonResult.success(cmsUser);
        }

        return null;
    }

    @Override
    public CommonResult searchUserByRoleId(SearchUserByRoleDto searchDo) {
        List<SysUserSearchVO> result = userMapper.searchUserByRoleId(searchDo);
        return CommonResult.success(result);
    }

    @Override
    public CommonResult userAddRole(SysUserRoleAddDto cmsUserRoleAdd) {
        String roleId = cmsUserRoleAdd.getRoleId();
        List<Long> idLists = cmsUserRoleAdd.getIdList(); //??????id
        int count = 0;
        for (Long idList : idLists) { //????????????id
            SysUser cmsUser = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getId, idList));
            AdminAuthData userAuthData = new AdminAuthData();
            AdminAuthData origAuthData = JSONUtil.toBean(cmsUser.getAuthData(), AdminAuthData.class); //????????????
            List<String> origAuthList = origAuthData.getAuthList(); //????????????
            origAuthList.add(roleId); //??????????????????
            List<String> collect = origAuthList.stream().distinct().collect(Collectors.toList()); //??????
            userAuthData.setAuthList(collect);
            cmsUser.setAuthData(JSONUtil.toJsonStr(userAuthData)); //????????????????????????????????????
            int i = userMapper.updateById(cmsUser);
            if (i == 1) {
                redisService.remove(cmsUser.getNumber());
                count = count + 1;
            } else if (i != 1) {
                return CommonResult.failed("??????????????????????????????");
            }
        }
        return CommonResult.success("??????????????????????????? " + count + " ??????");

    }
}