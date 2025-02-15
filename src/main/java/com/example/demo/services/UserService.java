package com.example.demo.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.SanPham;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User findByUsernameAndPassword(String username, String password){
    	return userRepository.findByUsernameAndPassword(username, password);
    }
    // Tìm người dùng theo username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findById(username);
    }

    // Thêm người dùng mới
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Cập nhật người dùng
    public User updateUser(String username, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setImage(updatedUser.getImage());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with username: " + username);
        }
    }

    // Xóa người dùng
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }
}

