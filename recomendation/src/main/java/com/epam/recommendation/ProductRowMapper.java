package com.epam.recommendation;

import com.epam.recommendation.dto.Product;
import com.epam.recommendation.dto.ProductGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getString("price"));
        product.setDescription(resultSet.getString("description"));
        product.setCreated(resultSet.getString("created"));
        product.setGroup(new ProductGroup(resultSet.getString("group_id")));
        product.setUserId(resultSet.getString("user_id"));

        return product;
    }
}
