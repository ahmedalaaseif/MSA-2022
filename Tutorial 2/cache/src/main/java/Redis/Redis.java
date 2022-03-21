package Redis;

import redis.clients.jedis.Jedis;

public class Redis {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
    }

}
