package com.epam.product.services;

import com.epam.product.model.Product;
import com.epam.product.model.ProductGroup;
import com.epam.product.model.ProductImage;
import com.epam.product.repositories.GroupRepository;
import com.epam.product.repositories.ProductImageRepository;
import com.epam.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EcommerceService {

    @Autowired
    private
    ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private
    GroupRepository groupRepository;


    /* PRODUCT */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(long id) {
        return productRepository.findById(id).get();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public String addProductImage(final String productId, final String filename) {

        ProductImage image = new ProductImage();
        image.setProductId(Long.parseLong(productId));
        image.setPath(filename);
        productImageRepository.save(image);
        return image.toString();
    }

    /* GROUPS */
    public List<ProductGroup> getGroups() {
        return groupRepository.findAll();
    }

    public ProductGroup getGroup(long id) {
        return groupRepository.findById(id).get();
    }

    public ProductGroup saveGroup(ProductGroup group) {
        return groupRepository.save(group);
    }
}
