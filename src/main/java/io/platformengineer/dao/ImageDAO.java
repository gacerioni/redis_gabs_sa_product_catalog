package io.platformengineer.dao;

import io.platformengineer.model.Image;

public interface ImageDAO {
    Image createImage(Image image);
    Image getImageById(Long id);
    void updateImage(Image image);
    void deleteImage(Long id);
}
