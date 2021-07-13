package com.epam.product.controllers;

import com.epam.product.model.Product;
import com.epam.product.model.ProductImage;
import com.epam.product.repositories.ProductImageRepository;
import com.epam.product.services.EcommerceService;
import com.epam.product.services.ProductAvaliabilityService;
import com.epam.product.storage.StorageService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EcommerceService ecommerceService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Validator productValidator;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    @Qualifier("productAvailiabilityHazelcast")
    private ProductAvaliabilityService productAvaliabilityService;

    @Value("${recommendationAddress}")
    private String recommendationAddress;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(productValidator);
    }

    @GetMapping
    public List<Product> index() {
        System.out.println("запрос пришел сюда");
        return ecommerceService.getProducts();
    }

    @GetMapping("availability/{id}")
    public boolean getAvailability(@PathVariable("id") long id) {
        return productAvaliabilityService.getProductAvailability(id);
    }

    @PostMapping
    public Product create(@RequestBody @Valid Product product) {
        return ecommerceService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public Product view(@PathVariable("id") long id) {

        return ecommerceService.getProduct(id);
    }

    @GetMapping("/{id}/recommendations")
    @HystrixCommand(fallbackMethod = "viewRecommendationFallback")
    public List<Product> viewRecommendation(@PathVariable("id") long id) {
        return Arrays.asList(restTemplate.getForObject(recommendationAddress + "/" + id, Product[].class));
    }

    public List<Product> viewRecommendationFallback(@PathVariable("id") long id) {
        return ecommerceService.getProducts();
    }

    @PostMapping(value = "/{id}")
    public Product edit(@PathVariable("id") long id, @RequestBody @Valid Product product) {

        Product updatedProduct = ecommerceService.getProduct(id);

        if (updatedProduct == null) {
            return null;
        }

        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());

        return ecommerceService.saveProduct(updatedProduct);
    }

    @GetMapping("/{id}/images")
    public List<ProductImage> viewImages(@PathVariable("id") String productId) {
        TypedQuery<ProductImage> query =
                entityManager.createQuery("FROM ProductImage WHERE product_id = :product_id", ProductImage.class)
                        .setParameter("product_id", Long.parseLong(productId));
        return query.getResultList();
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("id") String id) {

        ProductImage image = productImageRepository.getOne(Long.parseLong(id));

        // Relative path to StorageProperties.rootLocation
        String path = "product-images/" + image.getProductId() + "/";

        Resource file = storageService.loadAsResource(path + image.getPath());
        String mimeType = "image/png";
        try {
            mimeType = file.getURL().openConnection().getContentType();
        } catch (IOException e) {
            System.out.println("Can't get file mimeType. " + e.getMessage());
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, mimeType)
                .body(file);
    }

    @PostMapping(value = "/{id}/uploadimage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handleFileUpload(
            @PathVariable("id") String id, @RequestBody MultipartFile file) {

        // Relative path to the rootLocation in storageService
        String path = "/product-images/" + id;
        String filename = storageService.store(file, path);

        return ecommerceService.addProductImage(id, filename);
    }

}
