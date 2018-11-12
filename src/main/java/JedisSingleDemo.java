import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @param
 * @Author: judy
 * @Description:
 * @Date: Created in 23:51 2018/10/16
 */
//@Slf4j
public class JedisSingleDemo {
    public static  void main(String[] args){
        Jedis jedis=new Jedis("192.168.190.22");   //连接到reids
        String res="this is judy";
        jedis.set(res,"this,is,first");     //通过jedis的set方法创建一个key value的字段
        jedis.append(res,"who");             //然后使用append进行追加,跟java中string是一个道理
        String address=jedis.get(res);         //通过get方法得到key的value
        System.out.println(address+"first test jedis");
    }

}
