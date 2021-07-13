package com.epam.statistic.controller;

import com.epam.statistic.model.StatisticRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @PersistenceContext
    EntityManager entityManager;

    @PostMapping("/addStatistic")
    @Transactional
    public String addStatistic (@RequestParam("id") String productId, @RequestParam("username") String username) {
        StatisticRecord statisticRecord = new StatisticRecord(productId,username);
        entityManager.persist(statisticRecord);

        return "OK";
    }
}
