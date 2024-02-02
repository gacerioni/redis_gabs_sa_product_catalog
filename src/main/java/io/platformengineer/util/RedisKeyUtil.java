package io.platformengineer.util;

public class RedisKeyUtil {

    private static final String CATEGORY_PREFIX = "category:";
    private static final String PRODUCT_PREFIX = "product:";
    private static final String USER_PREFIX = "user:";
    private static final String IMAGE_PREFIX = "image:";

    public static String getCategoryKey(Long id) {
        return CATEGORY_PREFIX + id;
    }

    public static String getProductKey(Long id) {
        return PRODUCT_PREFIX + id;
    }

    public static String getUserKey(Long id) {
        return USER_PREFIX + id;
    }

    public static String getImageKey(Long id) {
        return IMAGE_PREFIX + id;
    }

}
