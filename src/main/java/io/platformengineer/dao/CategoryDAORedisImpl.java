package io.platformengineer.dao;

import io.platformengineer.model.Category;

import io.platformengineer.util.ConfigManager;
import io.platformengineer.util.RedisIdGenerator;
import io.platformengineer.util.RedisKeyUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;


public class CategoryDAORedisImpl implements CategoryDAO {
    private Jedis jedis;
    private RedisIdGenerator idGenerator;

    public CategoryDAORedisImpl() {

        String redisHost = ConfigManager.getEnvVariable("REDIS_HOST", "localhost");
        int redisPort = Integer.parseInt(ConfigManager.getEnvVariable("REDIS_PORT", "6379"));
        String redisUser = ConfigManager.getEnvVariable("REDIS_USER", "default");
        String redisPassword = ConfigManager.getEnvVariable("REDIS_PASSWORD", "wrongpass");

        JedisPool jedisPool = new JedisPool(redisHost, redisPort, redisUser, redisPassword);
        this.jedis = jedisPool.getResource();

        // Also initialize the idGenerator, since we all go to the same Redis target
        this.idGenerator = new RedisIdGenerator(jedis);
    }

    public CategoryDAORedisImpl(Jedis jedis, RedisIdGenerator idGenerator) {
        this.jedis = jedis;
        this.idGenerator = idGenerator;
    }

    @Override
    public Category createCategory(Category category) {

        Long categoryId = idGenerator.generateCategoryId(); // Generate a new ID using Redis as a counter
        category.setId(categoryId);
        System.out.printf("Category ID: " + categoryId);

        String key = RedisKeyUtil.getCategoryKey(categoryId);
        System.out.println(key);
        System.out.println(category.getId());
        String existingType = jedis.type(key);

        if (!"none".equals(existingType) && !"hash".equals(existingType)) {
            throw new IllegalStateException("Cannot create category, key exists with type " + existingType);
        }
        jedis.hset(key, "name", category.getName());
        return category;
    }

    @Override
    public Category getCategoryById(Long id) {
        // Use HGETALL to retrieve the category by ID
        String key = RedisKeyUtil.getCategoryKey(id);
        List<String> data = jedis.hvals(key);
        if (data.isEmpty()) {
            return null;
        }
        Category category = new Category(id, data.get(0));
        return category;
    }

    @Override
    public Category updateCategory(Category category) {
        // Similar to create, but ensuring the category exists first
        String key = "category:" + category.getId();
        jedis.hset(key, "name", category.getName());

        return category;
    }

    @Override
    public void deleteCategory(Long id) {
        // Use DEL to remove the category
        String key = "category:" + id;
        jedis.del(key);
    }

    @Override
    public List<Category> getAllCategories() {
        // This operation is more complex in Redis and not directly supported
        // It requires scanning keys and fetching each category
        Set<String> keys = jedis.keys("category:*");
        List<Category> categories = new ArrayList<>();
        for (String key : keys) {
            Category category = getCategoryById(Long.parseLong(key.split(":")[1]));
            if (category != null) {
                categories.add(category);
            }
        }
        return categories;
    }
}