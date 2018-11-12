import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
* @Description:    jedis使用管道概念
* @Author:         王雪芬
* @CreateDate:     2018/11/12 13:36
* @UpdateUser:     王雪芬
* @UpdateDate:     2018/11/12 13:36
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
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
