package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Size;
import com.example.demo.services.SizeService;

@Controller
@RequestMapping("/size")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @PostMapping("/create")
    public String createSize(Size size, RedirectAttributes redirectAttributes) {
        sizeService.createSize(size);
        redirectAttributes.addFlashAttribute("message", "Kích thước mới đã được tạo.");
        return "redirect:/home/create/product";// Trả về trang tạo sản phẩm sau khi tạo xong kích thước
    }
}