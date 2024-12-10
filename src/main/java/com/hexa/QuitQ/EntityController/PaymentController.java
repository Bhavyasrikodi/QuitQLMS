package com.hexa.QuitQ.EntityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hexa.QuitQ.DTO.LMSTransactionRequestDto;
import com.hexa.QuitQ.DTO.LMSUserRequestDto;
import com.hexa.QuitQ.DTO.PaymentDto;
import com.hexa.QuitQ.DTO.PaymentRequestDto;
import com.hexa.QuitQ.Service.PaymentService;
import com.hexa.QuitQ.entities.Payment;
import com.hexa.QuitQ.enums.PaymentStatus;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.PaymentMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/quitq/payments")
@CrossOrigin(origins="*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private RestTemplate restTemplate;

    // http://localhost:8080/api/v1/quitq/payments/for/buy-now
    @PostMapping("/for/buy-now")
    public ResponseEntity<?> createPaymentForBuyNow(@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        try {
            Payment payment = paymentService.createPaymentforBuyNow(paymentRequestDto);
            PaymentDto paymentDto = paymentMapper.mapToPaymentDto(payment);
            LMSTransactionRequestDto lmsTransactionRequestDto = new LMSTransactionRequestDto();
            lmsTransactionRequestDto.setPayment_id(paymentDto.getPaymentId());
            lmsTransactionRequestDto.setAmount(paymentDto.getAmount());
            LMSTransactionRequestDto lmsResponse = restTemplate.postForObject("http://localhost:8080/api/v1/lms/transactions/newTransaction", lmsTransactionRequestDto , LMSTransactionRequestDto.class);
            return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/api/v1/quitq/payments/for/cart
    @PostMapping("/for/cart")
    public ResponseEntity<?> createPaymentForCart(@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        try {
            Payment payment = paymentService.createPaymentforCart(paymentRequestDto);
            PaymentDto paymentDto = paymentMapper.mapToPaymentDto(payment);
            return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/api/v1/quitq/payments/get/paymentdetails?orderId=21
    @GetMapping("/get/paymentdetails")
    public ResponseEntity<?> findPaymentDetails(@RequestParam Long orderId) {
        try {
            Payment payment = paymentService.findPaymentDetails(orderId);
            PaymentDto paymentDto = paymentMapper.mapToPaymentDto(payment);
            return new ResponseEntity<>(paymentDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    //http://localhost:8080/api/v1/quitq/payments/get/status/15
    @GetMapping("/get/status/{paymentId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable Long paymentId) {
        try {
            PaymentStatus paymentStatus = paymentService.getPaymentStatus(paymentId);
            return new ResponseEntity<>(paymentStatus, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

