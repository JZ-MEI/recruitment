# recruitment

#### 介绍
基于Springboot+layui实现的招聘网站

#### 软件架构
前端使用layui完成；
后端分为求职者端，招聘者端和管理员端；
其中求职者端在Apply项目中；
招聘者端和管理员端在Company项目中；


#### 安装教程

想必大家都会吧

#### 使用说明

项目中用到的图片我放在本机的一个tomcat服务器中，端口为9000，自行配置；
自动构造的简历pdf和主动上传的简历pdf也在上述服务器里
总体程序流程都没问题，可能有点设计缺陷，想改的话自己改改吧



总体流程没问题，能跑（我和程序都能跑），可能有点设计缺陷，但是我懒得改了，你们想改自己改吧，如果好用的话也可以提交一下，我会亲自在心里感谢你。



1.项目涉及到了邮箱验证码发送，但是登录偷懒没用拦截器
2.构造简历pdf用了iText
3.智能推荐我自己写的，很垃圾，但也有点作用，毕竟我不太懂大数据。
4.管理员我分了两个角色，普通管理员和超级管理员，但是没给把自己设置成超级管理员的接口（我感觉这个接口有点病），所以想加入超级管理员的话就注册后劳烦自己吧数据库admin_info的is_audit和is_super改成1就好了

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
