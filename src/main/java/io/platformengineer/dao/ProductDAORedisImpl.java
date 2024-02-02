package io.platformengineer.dao;
import io.platformengineer.dao.ProductDAO;
import io.platformengineer.model.Product;
import io.platformengineer.model.Category;
import io.platformengineer.util.ConfigManager;
import io.platformengineer.util.RedisIdGenerator;
import io.platformengineer.util.RedisKeyUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDAORedisImpl implements ProductDAO{

    private Jedis jedis;
    private RedisIdGenerator idGenerator;

    public ProductDAORedisImpl() {

        String redisHost = ConfigManager.getEnvVariable("REDIS_HOST", "localhost");
        int redisPort = Integer.parseInt(ConfigManager.getEnvVariable("REDIS_PORT", "6379"));
        String redisUser = ConfigManager.getEnvVariable("REDIS_USER", "default");
        String redisPassword = ConfigManager.getEnvVariable("REDIS_PASSWORD", "wrongpass");

        JedisPool jedisPool = new JedisPool(redisHost, redisPort, redisUser, redisPassword);
        this.jedis = jedisPool.getResource();

        // Also initialize the idGenerator, since we all go to the same Redis target
        this.idGenerator = new RedisIdGenerator(jedis);
    }

    public ProductDAORedisImpl(Jedis jedis, RedisIdGenerator idGenerator) {
        this.jedis = jedis;
        this.idGenerator = idGenerator;
    }

    @Override
    public Product createProduct(Product product) {
        Long productId = idGenerator.generateProductId();
        product.setId(productId);

        String key = RedisKeyUtil.getProductKey(productId);
        Map<String, String> productMap = Map.of(
                "name", product.getName(),
                "description", product.getDescription(),
                "vendor", product.getVendor(),
                "price", product.getPrice().toString(),
                "currency", product.getCurrency(),
                "mainCategory", Long.toString(product.getMainCategory().getId())
                // Gabs: Handling of images is skipped for now
        );

        jedis.hset(key, productMap);
        return product;
    }

    @Override
    public Product getProductById(Long id) {
        String key = RedisKeyUtil.getProductKey(id);
        Map<String, String> fields = jedis.hgetAll(key);

        if (fields == null || fields.isEmpty()) {
            return null;
        }

        Product product = new Product();
        product.setId(id);
        product.setName(fields.get("name"));
        product.setDescription(fields.get("description"));
        product.setVendor(fields.get("vendor"));
        product.setPrice(Double.valueOf(fields.get("price")));
        product.setCurrency(fields.get("currency"));
        // Assume a method getCategoryById exists to fetch a Category
        Category mainCategory = new CategoryDAORedisImpl().getCategoryById(1L);
        // Images handling would be added here

        return product;
    }

    @Override
    public void updateProduct(Product product) {
        // Similar to createProduct, but checks if product exists and then updates
        String key = RedisKeyUtil.getProductKey(product.getId());
        // Assuming product exists and similar map creation as in createProduct
        //jedis.hset(key,);
    }

    @Override
    public void deleteProduct(Long id) {
        String key = RedisKeyUtil.getProductKey(id);
        jedis.del(key);
    }

    @Override
    public List<Product> findAll() {
        // Implement based on your application's needs, possibly using scan for keys
        // and then fetching each product's details. Be mindful of performance.
        return new ArrayList<>();
    }

}
