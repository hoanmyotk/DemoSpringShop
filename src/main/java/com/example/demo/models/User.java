package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "DANGNHAP") // Thay YourTableName bằng tên bảng trong cơ sở dữ liệu
public class User {

    @Id
    @Column(name = "TENDANGNHAP", nullable = false, length = 50)
    private String username;

    @Column(name = "MATKHAU", nullable = false, length = 50)
    private String password;

    @Column(name = "ANH", length = 100)
    private String image;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
