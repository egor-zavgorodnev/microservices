package com.epam.product.cache;

import com.epam.product.services.ProductAvaliabilityService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ProductAvailiabilityHazelcast implements ProductAvaliabilityService {

    @Override
    @Cacheable("products")
    public boolean getProductAvailability(long id) {
        return requestToMockAvailabilityService(id);
    }

    private boolean requestToMockAvailabilityService(long id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextInt(11) > 5;
    }
}
