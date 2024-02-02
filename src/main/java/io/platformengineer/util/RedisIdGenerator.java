package io.platformengineer.util;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class RedisIdGenerator {
    private Jedis jedis;
    private static final String CATEGORY_ID_KEY = "category:id:counter";
    private static final String PRODUCT_ID_KEY = "product:id:counter";
    private static final String IMAGE_ID_KEY = "image:id:counter";

    public RedisIdGenerator(Jedis jedis) {
        this.jedis = jedis;
    }

    public long generateCategoryId() {
        return jedis.incr(CATEGORY_ID_KEY);
    }

    public long generateProductId() {
        return jedis.incr(PRODUCT_ID_KEY);
    }

    public long generateImageId() {
        return jedis.incr(IMAGE_ID_KEY);
    }

    // Method for generating hash-based IDs if needed
    public String generateUniqueId() {
        return UUID.randomUUID().toString();
    }


}
