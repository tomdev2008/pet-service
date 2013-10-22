pet-service
===========

pet 服务端


模块说明：
----------

1、 pet-parent          	【pom】父模块，定义了工程依赖以及系统版本等；

2、 pet                 	【过期】旧版本pet应用，提供所有功能，当用户升级到新版本后，停止此应用的服务；

3、 pet-hub             	【基础服务】，服务端转发代理应用，负责鉴权、转发、事件消息、注册时的短信验证码 功能；

4、 pet-commons         	【jar包】，公共工具；

5、 pet-domain          	【jar包】，领域模型层封装，以及缓存封装；

6、 pet-statistic       	【消息监听】收集 pet-hub 事件消息入仓库，提供统计数据源；

7、 pet-wordfilter      	【消息监听】字符过滤器，异步，消息驱动，对动态和圈子信息进行关键词过滤，自动审核；

8、 pet-file-server     	【基础服务】文件服务器，提供统一的文件上传、下载、删除接口；

9、 pet-sso-server      	【基础服务】认证服务器，提供 注册、登录、退出、token校验、改密码、验证码 服务；

10、pet-service-bbs     	【业务模块】圈子功能接口；

11、pet-service-user    	【业务模块】用户个人中心功能接口；

12、pet-service-manager		【维护模块】业务模块管理功能，供维护和运营人员使用；



使用说明：
----------

【后台服务入口】 提供统一标准的 输入/输出 协议为客户端提供服务，协议说明如下

服务URL
  
  测试系统:   http://123.178.27.74/pet-hub/request
  
  正式系统:   http://www.52pet.net/pet-hub/request

输入参数
  
  参数名称：body
  
  例如：
    http://123.178.27.74/pet-hub/request?body={"service":"service.uri.pet_sso","method":"login","channel":"1","params":{"username":"cc","password":"123"}}
  
  参数格式：
    
    以用户登录的请求参数为例，必填项如下
    
    {
        "service":"service.uri.pet_sso",
        "method":"login",
        "channel":"1",
        "params":{
            "username":"cc",
            "password":"123"
        }
    }

  参数说明（只包含了必填项）：
      
    service: 业务模块的注册名，下文会给出业务模块的注册表；
    
    method: 具体的业务方法；
    
    channel: 请求来源渠道
  
    params: 供业务方法使用的参数，具体参数内容，应参考业务模块注册表，其中有部分模块可以不需要此参数；
    
  返回值说明：
    
    统一格式的返回，其中 success 标识请求是否成功，返回 true 则 entity 为 object，object 格式由业务模块定义
    返回 false 则表示请求异常，其中 entity 为 exception 信息
    
    {
      "success": true / false,
      "entity": object / exception
    }

业务功能注册表
--------------

认证服务：
  
  注册名: service.uri.pet_sso
  
  方法:
      
    1、login
      
      功能：用户登录
      
      输入：
      
          {
            "username":"cc",
            "password":"123",
            "deviceToken":"xxx"
          }
      
      输出：
          
          {
              "success":true,
              "entity":{
                  "authenticationToken":{
                      "token":"xxx",
                      "createDate":"2013-10-22 10:33:13",
                      "expire":-1,
                      "userid":000
                  },
                  "chatserver":{
                      "id":1,
                      "address":"61.51.110.55",
                      "name":"test.com",
                      "version":0
                  }
              }
          }
          
      
    2、logout
      
      功能：退出登录
      
      输入：使用 token 完成退出，无需 params 指定额外参数。
      
      输出：{"success":true,"entity":"OK"}
    
    3、register
      
      功能：用户注册
      
      输入：
        
        {
            "nickname":""
            "phonenumber":""
            "password":""
            "birthdate":""
            "gender":""
            "city":""
            "signature":""
            "img":""
            "hobby":""
            "deviceToken":""
        }
        
      输出：
        
        {
              "success":true,
              "entity":{
                    "token":"xxx",
                    "createDate":"2013-10-22 10:33:13",
                    "expire":-1,
                    "userid":000
              }
        }
        
    4、resetPassword
      
      功能：修改密码
    
      输入：
        
        {
            "password":"xxx"
            "phonenumber":"000"
        }
      
      输出：{"success":true,"entity":"OK"}
        
      
    5、getVerificationCode
      
      功能：获取验证码
    
      输入：{"phonenumber":"000"}
      
      输出：{"success":true,"entity":"ABCD"}      
      
      
    6、verifyCode
      
      功能：校验验证码
      
      输入：{"phoneNumber":"15199999999","verificationCode":"JDL3"}
      
      输出：{"success":true,"entity":true}




<hr/>
用户中心服务：



      
      


