package com.epam.kafka.controllers;

import com.epam.kafka.dto.StatisticRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private KafkaTemplate<Long, StatisticRecord> kafkaTemplate;

    @PostMapping
    public void sendStatistic(Long msgId, String productId, String username) {
        StatisticRecord statisticRecord = new StatisticRecord(productId, username);
        ListenableFuture<SendResult<Long, StatisticRecord>> future = kafkaTemplate.send("msg", msgId, statisticRecord);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }

}
