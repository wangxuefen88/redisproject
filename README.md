# redisproject
本人csdn博客 https://blog.csdn.net/dtttyc

redis与jedis的结合-简单入门程序
一.前提


我们这次连接redis使用的是jedis,首先需要创建一个项目,使用工具是idea,其他的工具都可以,然后使用的maven项目,在pom文件下面需要引用redis的jar包

<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>2.9.0</version>
</dependency>
1.连接到reids服务器操作
   1首先创建一个JAVA类名字叫做JedisSingleDemo

  

public class JedisSingleDemo {
    public static  void main(String[] args){
        Jedis jedis=new Jedis("192.168.190.22");   //连接到reids
        String res="this is judy";   
        jedis.set(res,"this,is,first");     //通过jedis的set方法创建一个key value的字段
        jedis.append(res,"who");             //然后使用append进行追加,跟java中string是一个道理
        String address=jedis.get(res);         //通过get方法得到key的value
        System.out.println(address+"first test jedis");
    }
输出

this,is,firstwhofirst test jedis
Process finished with exit code 0


通过redis的客户端查询输出



2.可能出现的错误
DENIED Redis is running in protected mode because protected mode is enabled, no bind address was specified, no authentication password is requested to clients. In this mode connections are only accepted from the loopback interface. If you want to connect from external computers to Redis you may adopt one of the following solutions:     1) Just disable protected mode sending the command 'CONFIG SET protected-mode no' from the loopback interface by connecting to Redis from the same host the server is running,         however MAKE SURE Redis is not publicly accessible from internet if you do so. Use CONFIG REWRITE to make this change permanent.     2) Alternatively you can just disable the protected mode by editing the Redis configuration file, and setting the protected mode option to 'no', and then restarting the server.     3) If you started the server manually just for testing, restart it with the '--protected-mode no' option.     4) Setup a bind address or an authentication password. NOTE: You only need to do one of the above things in order for the server to start accepting connections from the outside.


       出现这个错误的原因是因为reids开启了保护模式,所以这个时候你的jedis在进行访问的时候被拒绝了



解决办法
    连接redis的客户端,reids-cli           
  输入 :config set protected-mode "no"
    这句话的意思就是把reids中环内配置的保护类型给去掉

二 在jedis中使用管道


1 使用管道的概念是重要使用Pipeline对象,使用到了一个system.exit(0) , sync()

 

2 对比正常情况下使用reids的管道和不使用reids的管道的区别



使用管道之后的



3代码



public class JedisPipelineDemo {
    public static void main(String[] args){
        Jedis jedis = new Jedis("192.168.190.22");  //连接jedis
         Pipeline pipelined = jedis.pipelined();           //使用jedis的管道
         pipelined.set("judy1","a,b,c,d");                //使用单个的value
         pipelined.sadd("xuefen","第一个","第二个","第三个");  //存放多个value
         Response<String> judy = pipelined.get("judy1");     //查询单个儿
         Response<Long> xuefen = pipelined.scard("xuuefen");
         pipelined.sync();        //循环到写到redis的server端
         System.out.println(judy.get()+"管道的单个set方法");
         System.out.println(xuefen.get()+"这个管道使用的存储多个value");
         System.exit(0);  //表示吧整个虚拟机内容都停掉,如果是1则表示的非正常退出,他跟return的区别是他返回到的是上一层,而system.exit()表示返回最上一层

    }
}
项目地址
