pet-service
===========

<h1>模块说明：

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



<h1>使用说明：

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

<hr/>
<h1>业务功能注册表

<h2>认证服务：</h2>
  
  注册名: service.uri.pet_sso
  
  方法:
      
    1、login
      
      功能：用户登录
      
      输入：{ "username":"cc", "password":"123", "deviceToken":"xxx" }
      
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
    
      输入：{ "password":"xxx", "phonenumber":"000" }
      
      输出：{ "success":true,"entity":"OK" }
        
      
    5、getVerificationCode
      
      功能：获取验证码
    
      输入：{"phoneNum":"000"}
      
      输出：{"success":true,"entity":"ABCD"}      
      
      
    6、verifyCode
      
      功能：校验验证码
      
      输入：{"phoneNum":"15199999999","verificationCode":"JDL3"}
      
      输出：{"success":true,"entity":true}

    7、isUsernameInuse
      
      功能：校验用户名是否存在，存在返回 entity=true ，不存在返回 entity=false

      输入：{"username":"xxx"}
      
      输出：{"success":true,"entity":true}
      
    8、token
    
      功能：校验 Token 是否正确
      
      输入：
          
          如果只校验 token 不需要传协议中的 params 参数，
          如果需要返回版本以及开机图片，需要传 {"action":"open"}

      输出：
      
          不需要开机图片和版本号的返回
          {
              "success":true,
              "entity":{
                  "authenticationToken":{"token":"xxx","createDate":"2013-10-22 10:04:26","userid":"xxx"},
                  "chatserver":{"id":1,"address":"xxx","name":"xxx","version":0}
              }
          }
          
          需要开机图片和版本号的返回值
          {
              "success":true,
              "entity":{
                  "authenticationToken":{"token":"xxx","createDate":"2013-10-22 10:04:26","userid":"xxx"},
                  "chatserver":{"id":1,"address":"xxx","name":"xxx","version":0},
                  "firstImage":"http://xxx.xxx",
                  "version":{
                      "version":"",
                      "iosurl":""
                  }
              }
          }
          

<h2>用户中心服务：</h2>

  注册名：service.uri.pet_user
  
  方法：
  
    1、getUserinfo
      
      功能：获取用户信息
      
      输入：
        
        app端 使用 token 完成获取，无需 params 指定额外参数。
        
        内部获取时，使用 {"userid":"000"}
      
      输出：
        
        {
	  "success":true,
    	  "entity":{
              "longitude":0.0,
              "latitude":0.0,
              "id":747,
              "nickname":"cc",
              "username":"cc",
              "version":0,
              "email":"",
              "phoneNumber":"",
              "ifFraudulent":"0",
              "deviceToken":"" 
          }
	}
	      
    2、updateUser
    	
      功能：更新用户信息
      
      输入：
        
        跟旧的协议内容一致，即现有可修改的用户基本信息，不包括坐标
        {
            "nickname":"cc",
            "username":"cc",
            "email":"",
            "phoneNumber":"",
            "ifFraudulent":"0",
            ...
	}
		
      输出：{"success":true,"entity":"OK" }
    
    3、updateUserLocation
      
      功能：更新用户坐标
      
      输入：
      
      	其中 userid 非必填，方法可以通过 token 获取
      	{ "userid":"xxx", "longitude":000.000, "latitude":000.000 }
      
      输出：{"success":true,"entity":"OK" }
      
    4、getPetinfo
    
      功能：获取宠物信息
      
      输入：
      
      	获取当前登录人的宠物时，不需要传递 userid,用TOKEN即可获取，获取其他人宠物，需要userid
        {"userid":731}
      	
      输出：
    	
        {
            "success":true,
            "entity":[
                {
                    "id":556,
                    "gender":"male",
                    "img":"1133_1134,1137_1138,",
                    "nickname":"xxx",
                    "type":1004,
                    "userid":000,
                    "version":8,
                    "trait":"xxx",
                    "birthdate":"2"
                },
                ......
            ]
        }

    5、savePetinfo
    
      功能：添加宠物信息，不需要传宠物ID
      
      输入：{ "nickname":"", "type":"", "img":"", "trait":"", "gender":"","birthdate":"" }
      
      输出：{"success":true,"entity":"OK" }
      
    6、updatePetinfo
    
      功能：更新宠物信息，一定要传宠物ID
      
      输入：{ "id":"", "nickname":"", "type":"", "img":"", "trait":"", "gender":"","birthdate":"" }
      
      输出：{ "success":true,"entity":"OK" }
      
    7、addOrRemoveFriend
      
      功能：添加或删除好友关系，目前只有 XMPP 回调此接口
      
      输入：{ "SubscriptionType":"", "aId":"", "bId":""}
      
      输出：{"success":true,"entity":"OK" }
    
    8、pushMsgApn
    
      功能：给IOS推送离线消息，目前只有 XMPP 回调此接口
      
      输入：{"fromname":"", "toname":"", "msg":""}
      
      输出：{"success":true,"entity":"OK" }
      
    9、getFriendList
    
      功能：获取好友列表，如果获取当前登录人的好友列表，则不需要传递 params 参数，用 token 即可获取
      
      输入：{"userid":"770"}
      
      输出：{"success":true,"entity":[{"id":"747","alias":"别名","nickname":"cc","username":"cc","phoneNumber":"","deviceToken":""}]}

      
    10、getNearPerson

      功能：获取附近的人，distance属性表示距离，包含在 user 对象中

      输入：{"gender":"性别", "petType":"宠物类型", "longitude":"","latitude":""}

      输出：{
                "success":true,
                "entity":[ 
                    { user:{} , petList:[ {} ] } 
                ] 
            }

    11、delPetinfo
    
      功能：删除宠物

      输入：{"id":"宠物ID"}
      
      输出：{"success":true,"entity":"OK" }

    12、adminFlushUserPetTypeIndex
    
      功能：重建用户与宠物类型的索引，此功能仅限管理人员使用，不对客户端开放
      
      输入：{"uid":"all","pwd":""}

      输出：{"success":true,"entity":"OK" }
      
    13、adminFlushFriendShipIndex
      
      功能：更新好友列表索引，此功能仅限管理人员使用，不对客户端开放
      
      输入：{"method":"adminFlushFriendShipIndex",params:{"pwd":""}}
       
      输出：{"success":true,"entity":"OK" }
      
  <h2>用户动态服务:</h2>
  注册名：service.uri.pet_states
    方法：
    
    1、addReply
    功能：添加动态回复

    输入：{"msg":"","pid":"","puserid":"","stateid":"","stateUserid":""}
    
    输出：{"success":true,"entity":"E1C15A49026A44838D2F547FEA282D2F"}

    2、addUserState
    功能：添加动态

    输入：{"msg":"","imgid":"","ifTransmitMsg":"","transmitUrl":"","transmitMsg":""}
    
    输出：{"success":true,"entity":"DC5D7F8A8D36455794236AC74F56D393"}
