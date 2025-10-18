package com.Take_Home_Exercise.Take_Home_Exercise.model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @CsvBindByName
    private String sku;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String brand;
    @CsvBindByName
    private String color;
    @CsvBindByName
    private String size;
    @CsvBindByName
    private BigDecimal mrp;
    @CsvBindByName
    private BigDecimal price;
    @CsvBindByName
    private Integer quantity;
}
