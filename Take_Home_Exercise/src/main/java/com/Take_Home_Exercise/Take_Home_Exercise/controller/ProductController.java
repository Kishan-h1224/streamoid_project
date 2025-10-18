package com.Take_Home_Exercise.Take_Home_Exercise.controller;

import com.Take_Home_Exercise.Take_Home_Exercise.model.Product;
import com.Take_Home_Exercise.Take_Home_Exercise.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Integer>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Integer> response = productService.uploadAndSaveProducts(file);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        Page<Product> products = productService.getAllProducts(page, limit);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/products/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        Page<Product> products = productService.searchProducts(brand, color, minPrice, maxPrice, page, limit);
        return ResponseEntity.ok(products);
    }
}

