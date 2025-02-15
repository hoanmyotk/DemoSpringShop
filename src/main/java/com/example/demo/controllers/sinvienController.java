package com.example.demo.controllers;

import java.util.ArrayList;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.sinhvien;

import jakarta.servlet.http.HttpSession;

@Controller //Chú thích lớp sau là 1 Controller
public class sinvienController {
@RequestMapping("/mysinhvien") //http://localhost:8080/mysinhvien
public String HienThiSV(Model model, HttpSession session) {
//Gán trị vào biến tb
String tb="Xin chào Spring Boot";
//Giá trị của biến tb vào biên tbmodel để sử dụng trên trang HienThiSv.html 
model.addAttribute("tbmodel", tb); //request.setAttribute(("tbmodel", tb)
//Khai báo 1 mảng
ArrayList<sinhvien> ds= new ArrayList<sinhvien>();
ds.add(new sinhvien("01","Hung"));
ds.add(new sinhvien("02","Nga"));
//Lưu giá trị của ds vào biến dsmodel sử dụng trên trang HienThiSv.html 
model.addAttribute("dsmodel",ds);
//Lưu giá trị của ds vào session: mysession
session.setAttribute("mysession", ds);
return "HienThiSV"; //Trả về View: HienThiSv.html
}
}

