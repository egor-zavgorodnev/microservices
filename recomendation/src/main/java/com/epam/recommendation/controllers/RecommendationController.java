package com.epam.recommendation.controllers;

import com.epam.recommendation.ProductRowMapper;
import com.epam.recommendation.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final int RECOMMENDED_PRODUCTS_LIMIT = 5;

    @GetMapping("/{id}")
    public List<Product> getRecommendedProducts(@PathVariable("id") long id) {
        String getCurrentProductSql = "SELECT * FROM products WHERE ID = ?";
        String getAllProductsSql = "SELECT * FROM products";

        ProductRowMapper productRowMapper = new ProductRowMapper();
        Product currentProduct = jdbcTemplate.queryForObject(getCurrentProductSql, new Object[]{id}, productRowMapper);
        List<Product> products = jdbcTemplate.query(getAllProductsSql, productRowMapper);

        return products.stream()
                .filter(product -> product.getGroup().getId().equals(currentProduct.getGroup().getId()))
                .filter(product -> product.getId() != currentProduct.getId())
                .limit(RECOMMENDED_PRODUCTS_LIMIT).collect(Collectors.toList());
    }


}
