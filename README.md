# SSO-SAMPLE

此项目为一个简单的SSO（单点登录）示例。`sso-server`模块为认证服务，
`sso-subsysa`和`sso-subsysb`两个模块为用户测试使用的子系统。

## 示例使用方法

1. 修改host文件，添加以下内容
```
127.0.0.1	auth.com
127.0.0.1	subsysa.com
127.0.0.1	subsysb.com
```

2. 启动`sso-server`、`sso-subsysa`和`sso-subsysb`

3. 端口在相应模块的`application.yml`中修改，认证服务`url`在子模块的`sso.properties`中修改

## 测试流程

1. `sso-subsysa`访问受保护资源

	访问`http://subsysa.com:8001/res`，自动跳转到`http://auth.com:8000/login`，登录后跳回`http://subsysa.com:8001/res`。

2. 在`sso-server`中登录后，访问`sso-subsysa`中受保护资源

	访问`http://auth.com:8000/login`后登录，再访问`http://subsysa.com:8001/res`，能正常显示资源，不会跳转到登录页面。

3. 在`sso-subsysa`登录后，访问`sso-subsysb`中受保护资源

	先执行1中步骤，然后访问`http://subsysb.com:8002/res`，能正常显示资源，不会跳转到登录页面。

4. `sso-server`中登出后，访问`sso-subsysa`中受保护资源

	`sso-server`中登出，访问`htttp://auth.com:8000/res`跳转到`http://auth.com:8000/login`，访问`http://subsysa.com:8001/res`，
	同样跳转到登录页面。

5. `sso-subsysa`中登出后，访问`sso-server`中受保护资源

	`sso-subsysa`中登出，访问`htttp://auth.com:8000/res`跳转到登录页面。

6. `sso-subsysb`中登出后，访问`sso-subsysb`中受保护资源

	`sso-subsysa`中登出，访问`http://subsysb.com:8002/res`，跳转到登录页面。