package com.Take_Home_Exercise.Take_Home_Exercise.service;

import com.Take_Home_Exercise.Take_Home_Exercise.model.Product;
import com.Take_Home_Exercise.Take_Home_Exercise.repository.ProductRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    public Map<String, Integer> uploadAndSaveProducts(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Empty file!!");
        }
        List<Product> validProducts = new ArrayList<>();
        List<Product> invalidProducts = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(reader)
                    .withType(Product.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Product> products = csvToBean.parse();

            for (Product product : products) {
                if (isValid(product)) {
                    validProducts.add(product);
                } else {
                    invalidProducts.add(product);
                }
            }

            if (!validProducts.isEmpty()) {
                productRepository.saveAll(validProducts);
            }

        } catch (IOException e) {
            throw new IllegalStateException("CSV file parsing failed: " + e.getMessage());
        }
        return Map.of(
                "stored", validProducts.size(),
                "failed", invalidProducts.size()
        );
    }
    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }
    public Page<Product> searchProducts(String brand, String color, BigDecimal minPrice, BigDecimal maxPrice, int page, int size) {
        Specification<Product> spec = Specification.where(null);
        if (brand != null && !brand.isBlank()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("brand"), brand));
        }
        if (color != null && !color.isBlank()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("color"), color));
        }
        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(spec, pageable);
    }
    private boolean isValid(Product product) {
        if (product.getSku() == null || product.getSku().isBlank() ||
                product.getName() == null || product.getName().isBlank() ||
                product.getBrand() == null || product.getBrand().isBlank() ||
                product.getMrp() == null || product.getPrice() == null) {
            return false;
        }
        if (product.getPrice().compareTo(product.getMrp()) > 0) {
            return false;
        }
        if (product.getQuantity() == null || product.getQuantity() < 0) {
            return false;
        }
        return true;
    }
}
