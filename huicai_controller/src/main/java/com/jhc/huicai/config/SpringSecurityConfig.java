package com.jhc.huicai.config;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jhc.huicai.BO.AdminSaveDetails;
import com.jhc.huicai.BO.AdminUserDetails;
import com.jhc.huicai.DO.SysPermission;
import com.jhc.huicai.DO.SysUser;
import com.jhc.huicai.VO.RedisUserInfo;
import com.jhc.huicai.service.ISysUserService;
import com.jhc.huicai.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法级别的权限控制
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
//                .successForwardUrl("/index")
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        // swagger api json
                        "/v2/api-docs",
                        // 用来获取支持的动作
                        // 用来获取api-docs的URI
                        "/swagger-resources/**",
                        // 安全选项
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/test/**",
                        "/websocket/**",
                        "/images/**"
                )
                .permitAll()
                .antMatchers(//过滤token验证
                        "/sysUser/register",
                        "/sysUser/logout",
                        "/sysUser/login",
                        "/sysUser/sendEmail",
                        "/webSysUser/login",
                        "/backstageSysUser/login",
                        "/backstageSysUser/logout",
                        "/backstageSysUser/register"
                )
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
//                .anyRequest().permitAll();
//                // 测试时全部运行访问
//                .antMatchers("/**")
//                .permitAll()
                // 除上面外所有请求全部需要鉴全认证
                .anyRequest()
                .authenticated()
                .and()
                // 自定义 jwt过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 指定用户认证时，默认从哪里获取认证用户信息
         */
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {
            SysUser user = iSysUserService.getOne(new QueryWrapper<SysUser>().lambda().eq(
                    SysUser::getNumber, username
            ));
            if (user != null) {
                try {
                    RedisUserInfo redisUserInfo = JSON.parseObject(redisService.get(username), RedisUserInfo.class);
                    // 从redis拿权限
                    if (redisUserInfo == null) {
                        redisUserInfo = new RedisUserInfo();
                    }
                    List<SysPermission> permissionList = redisUserInfo.getPermissionList();
                    if (permissionList == null) {
                        permissionList = new ArrayList<>();
                    }
                    System.out.println(JSON.toJSONString(permissionList));
                    userSaveDetails().setSysUser(user);
                    return new AdminUserDetails(user, permissionList);
                } catch (Exception e) {
                    throw new UsernameNotFoundException(e.getMessage());
                }
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }

    @Bean
    public AdminSaveDetails userSaveDetails() {
        return new AdminSaveDetails();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

}