package io.platformengineer.dao;

import io.platformengineer.dao.ImageDAO;
import io.platformengineer.model.Image;
import io.platformengineer.util.ConfigManager;
import io.platformengineer.util.RedisIdGenerator;
import io.platformengineer.util.RedisKeyUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ImageDAORedisImpl implements ImageDAO {
    private Jedis jedis;
    private RedisIdGenerator idGenerator;

    public ImageDAORedisImpl() {

        String redisHost = ConfigManager.getEnvVariable("REDIS_HOST", "localhost");
        int redisPort = Integer.parseInt(ConfigManager.getEnvVariable("REDIS_PORT", "6379"));
        String redisUser = ConfigManager.getEnvVariable("REDIS_USER", "default");
        String redisPassword = ConfigManager.getEnvVariable("REDIS_PASSWORD", "wrongpass");


        JedisPool jedisPool = new JedisPool(redisHost, redisPort, redisUser, redisPassword);
        this.jedis = jedisPool.getResource();

        // Also initialize the idGenerator, since we all go to the same Redis target
        this.idGenerator = new RedisIdGenerator(jedis);
    }

    public ImageDAORedisImpl(Jedis jedis, RedisIdGenerator idGenerator) {
        this.jedis = jedis;
        this.idGenerator = idGenerator;
    }

    @Override
    public Image createImage(Image image) {
        Long imageId = idGenerator.generateImageId(); // Assuming this method exists and returns a long
        image.setId(imageId);

        String key = RedisKeyUtil.getImageKey(imageId);
        jedis.set(key.getBytes(), image.getData()); // Store the binary data
        return image;
    }

    @Override
    public Image getImageById(Long id) {
        String key = RedisKeyUtil.getImageKey(id);
        byte[] data = jedis.get(key.getBytes()); // Retrieve the binary data

        if (data == null) {
            return null;
        }

        return new Image(id, data);
    }

    @Override
    public void updateImage(Image image) {
        // Similar to createImage
        String key = RedisKeyUtil.getImageKey(image.getId());
        jedis.set(key.getBytes(), image.getData()); // Update the binary data
    }

    @Override
    public void deleteImage(Long id) {
        String key = RedisKeyUtil.getImageKey(id);
        jedis.del(key.getBytes()); // Delete the binary data
    }



}
