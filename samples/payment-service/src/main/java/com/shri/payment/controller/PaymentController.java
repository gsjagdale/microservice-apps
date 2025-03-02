package com.shri.payment.controller;

import com.shri.payment.entity.Payment;
import com.shri.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("payments")
@Slf4j
public class PaymentController {

    @Autowired
    private final PaymentRepository paymentRepository;

    @GetMapping("list")
    public List<Payment> listPayments() {
        log.info("request received for payment list");
        return paymentRepository.findAll();
    }

}
