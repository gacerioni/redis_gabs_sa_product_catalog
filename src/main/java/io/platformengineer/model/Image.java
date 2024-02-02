package io.platformengineer.model;

public class Image {
    private Long id;
    private byte[] data;

    public Image() {
    }

    public Image(Long id, byte[] data) {
        this.id = id;
        this.data = data;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
