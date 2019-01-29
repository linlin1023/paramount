package com.paramount.admin.common.realm;

import com.paramount.admin.common.utils.SpringUtil;
import com.paramount.admin.common.utils.UserUtil;
import com.paramount.admin.dao.PermissionDao;
import com.paramount.admin.dao.RoleDao;
import com.paramount.admin.domain.Permission;
import com.paramount.admin.domain.Role;
import com.paramount.admin.domain.User;
import com.paramount.admin.domain.User.Status;
import com.paramount.admin.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by pll on 2018/11/25 21:35.
 * Email suppot@paramountmerchandize.co.nz
 */
public class ShiroRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String username = usernamePasswordToken.getUsername();
        UserService userService = SpringUtil.getBean(UserService.class);

        User user = userService.getUser(username);
        if (user == null) {
            throw new UnknownAccountException("username is not exist");
        }

        if (!user.getPassword()
                .equals(userService.passwordEncoder(new String(usernamePasswordToken.getPassword()), user.getSalt()))) {
            throw new IncorrectCredentialsException("password is wrong");
        }

        if (user.getStatus() != Status.VALID) {
            throw new IncorrectCredentialsException("inactive status，please contact administrator");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), getName());

        UserUtil.setUserSession(user);

        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("权限配置");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = UserUtil.getCurrentUser();
        List<Role> roles = SpringUtil.getBean(RoleDao.class).listByUserId(user.getId());
        Set<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toSet());
        authorizationInfo.setRoles(roleNames);
        List<Permission> permissionList = SpringUtil.getBean(PermissionDao.class).listByUserId(user.getId());
        UserUtil.setPermissionSession(permissionList);
        Set<String> permissions = permissionList.stream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
                .map(Permission::getPermission).collect(Collectors.toSet());
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    /**
     * 重写缓存key，否则集群下session共享时，会重复执行doGetAuthorizationInfo权限配置
     */
    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principals;
        Object object = principalCollection.getPrimaryPrincipal();

        if (object instanceof User) {
            User user = (User) object;

            return "authorization:cache:key:users:" + user.getId();
        }

        return super.getAuthorizationCacheKey(principals);
    }
}
