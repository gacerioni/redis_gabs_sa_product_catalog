package io.platformengineer;

import io.platformengineer.dao.*;
import io.platformengineer.model.Category;
import io.platformengineer.model.Image;
import io.platformengineer.model.Product;
import io.platformengineer.service.CategoryService;
import io.platformengineer.service.CategoryServiceImpl;
import io.platformengineer.util.RedisIdGenerator;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileOutputStream;

import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String DYNAMIC_CONTENT_DIR = System.getProperty("user.home") + File.separator + "app_dynamic_content";
    static String dynamicContentDir = System.getProperty("user.home") + File.separator + "app_dynamic_content";
    static File directory = new File(dynamicContentDir);

    public static void main(String[] args) {
        // 1 - STORING SOME CATEGORIES IN REDIS!!!
        Category category = new Category("Automotive");

        CategoryDAO categoryDAO = new CategoryDAORedisImpl();

        // Initialize the service with the DAO
        CategoryService categoryService = new CategoryServiceImpl(categoryDAO);

        // Create the category
        Category createdCategory = categoryService.createCategory(category);
        System.out.println("Created Category: " + createdCategory.getName());

        /*
        // CREATE A NEW PRODUCT USING THE CATEGORY 1
        Category category = new CategoryDAORedisImpl().getCategoryById(1L);
        Product product = new Product("PlayStation 5", "Sony PS5 EMEA", "Sony", 500.0, "USD", category);
        ProductDAORedisImpl productDAORedis = new  ProductDAORedisImpl();

        productDAORedis.createProduct(product);*/

        /*
        // 3 - STORING IMAGES AS BINARY IN REDIS!!!
        // First we check if the folder we use for media is good
        if(!directory.exists())
        {
            boolean created = directory.mkdirs();
            if (!created) {
                System.err.println("Failed to create dynamic content directory.");
            }
        } else {
            System.out.printf(DYNAMIC_CONTENT_DIR);
        }

        // Initialize the DAO
        ImageDAO imageDAO = new ImageDAORedisImpl();
        String fileName = "ps5_cover.png";

        // Path to the image file we want to upload
        String imagePath = DYNAMIC_CONTENT_DIR + "\\" + fileName;

        try {
            // Read the image file into a byte array
            byte[] imageData = Files.readAllBytes(Path.of(imagePath));

            // Create a new Image object with the binary data
            Image image = new Image();
            image.setData(imageData);

            // Use the DAO to store the image in Redis
            Image savedImage = imageDAO.createImage(image);
            System.out.println("Image saved with ID: " + savedImage.getId());

            // Write the clone to the filesystem
            String directoryPath = new File(imagePath).getParent();
            String clonedImagePath = directoryPath + File.separator + "clone_of_"+fileName;
            try (FileOutputStream fos = new FileOutputStream(clonedImagePath)) {
                fos.write(imageData);
            }
            System.out.println("Clone of the image saved to: " + clonedImagePath);


        } catch (IOException e) {
            System.err.println("Error reading the image file: " + e.getMessage());
        }
        */


    }
}