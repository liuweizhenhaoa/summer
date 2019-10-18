package com.summer.springboot.jwt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
public class MyAccessDecisionManager implements AccessDecisionManager {


    /**
     * 通过传递的参数来决定用户是否有访问对应受保护对象的权限
     *
     * @param authentication 包含了当前的用户信息，包括拥有的权限。这里的权限来源就是前面登录时UserDetailsService中设置的authorities。
     * @param o              就是FilterInvocation对象，可以得到request等web资源
     * @param collection     configAttributes是本次访问需要的权限
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        if (collection == null || 0 >= collection.size()) {
            return;
        } else {
            collection.forEach(configAttribute -> {
                String needRole = configAttribute.getAttribute();
                authentication.getAuthorities().forEach(entiy -> {
                    if (needRole.trim().equals(entiy.getAuthority().trim())) {
                        return;
                    }
                });

            });
            throw new AccessDeniedException("当前访问没有权限");
        }
    }

    /**
     * 表示此AccessDecisionManager是否能够处理传递的ConfigAttribute呈现的授权请求
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    /**
     * 表示当前AccessDecisionManager实现是否能够为指定的安全对象（方法调用或Web请求）提供访问控制决策
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
