package com.epam.product.cache;

import com.epam.product.model.ProductAvaliability;
import com.epam.product.services.ProductAvaliabilityService;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductAvaliabilityWithCache implements ProductAvaliabilityService {

    private static final ConcurrentHashMap<Long, ProductAvaliability> cache = new ConcurrentHashMap<>();

    public ProductAvaliabilityWithCache() {
    }

    @Override
    public boolean getProductAvailability(long id) {

        for (ProductAvaliability productAvaliability : cache.values()) {
            if (productAvaliability.getProductId() == id) {
                if (System.currentTimeMillis() - productAvaliability.getCreationTime() > 300) {
                    cache.remove(id);
                    break;
                } else {
                    return productAvaliability.isProductAvailable();
                }
            }
        }

        boolean productAvaliabilityValue = requestToMockAvailabilityService(id);
        cache.put(id, new ProductAvaliability(id, productAvaliabilityValue));
        return productAvaliabilityValue;
    }

    private boolean requestToMockAvailabilityService(long id) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextInt(11) > 5;
    }

}
