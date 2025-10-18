package com.Take_Home_Exercise.Take_Home_Exercise.service;

import com.Take_Home_Exercise.Take_Home_Exercise.model.Product;
import com.Take_Home_Exercise.Take_Home_Exercise.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void uploadAndSaveProducts_shouldStoreValidProductsAndRejectInvalidOnes() {
        String csvContent = "sku,name,brand,color,size,mrp,price,quantity\n" +
                "VALID-SKU,Valid Product,BrandA,Red,M,100,80,10\n" +
                "INVALID-SKU-1,Price > MRP,BrandB,Blue,L,100,120,5\n" +
                "INVALID-SKU-2,Negative Qty,BrandC,Green,S,200,150,-1\n" +
                ",Missing SKU,BrandD,Black,XL,300,250,20\n";

        MockMultipartFile file = new MockMultipartFile(
                "file", "products.csv", "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));
        Map<String, Integer> result = productService.uploadAndSaveProducts(file);
        assertEquals(1, result.get("stored"));
        assertEquals(3, result.get("failed"));
        verify(productRepository).saveAll(anyList());
    }
    @Test
    void searchProducts_shouldCallRepositoryWithCorrectSpecification() {
        List<Product> mockProducts = Collections.singletonList(new Product());
        Page<Product> productPage = new PageImpl<>(mockProducts);
        when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(productPage);
        productService.searchProducts("TestBrand", "Red", null, null, 0, 10);
        ArgumentCaptor<Specification> specificationCaptor = ArgumentCaptor.forClass(Specification.class);
        verify(productRepository).findAll(specificationCaptor.capture(), any(Pageable.class));
        Specification<Product> capturedSpec = specificationCaptor.getValue();
        assertNotNull(capturedSpec, "The specification passed to the repository should not be null.");
    }
}

