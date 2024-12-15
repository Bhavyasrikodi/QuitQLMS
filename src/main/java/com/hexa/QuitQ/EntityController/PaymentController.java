package com.hexa.QuitQ.EntityController;
 
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
 
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
import com.hexa.QuitQ.DTO.PaymentDto;
import com.hexa.QuitQ.DTO.PaymentRequestDto;
import com.hexa.QuitQ.DTO.PointsAmountRequestDto;
import com.hexa.QuitQ.DTO.PointsAmountResponseDto;
import com.hexa.QuitQ.DTO.TiersOffersDto;
import com.hexa.QuitQ.DTO.UserCouponDto;
import com.hexa.QuitQ.DTO.UserCouponRequestDto;
import com.hexa.QuitQ.DTO.UserCouponResponseDto;
import com.hexa.QuitQ.DTO.UserValidationDto;
import com.hexa.QuitQ.Service.PaymentService;
import com.hexa.QuitQ.ServiceImpl.CustomerServiceImpl;
import com.hexa.QuitQ.entities.Payment;
import com.hexa.QuitQ.entities.User;
import com.hexa.QuitQ.enums.PaymentStatus;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.PaymentMapper;
 
import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/api/v1/quitq/payments")
@CrossOrigin(origins="*")
public class PaymentController {
	
	private UUID partnerId = UUID.fromString("59d6f78c-f29b-41cd-a782-0c408133f97c");
 
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CustomerServiceImpl customerServiceImpl;
 
    // http://localhost:8080/api/v1/quitq/payments/for/buy-now
    @PostMapping("/for/buy-now")
    public ResponseEntity<?> createPaymentForBuyNow(
        @Valid @RequestBody PaymentRequestDto paymentRequestDto,@RequestParam(required = false) boolean isPointsUsed) throws ResourceNotFoundException {
 
        Payment payment = paymentService.createPaymentforBuyNow(paymentRequestDto);
        PaymentDto paymentDto = paymentMapper.mapToPaymentDto(payment);
 
        User userId = customerServiceImpl.getUserIdByEmail(paymentDto.getEmail());
        System.out.println("useridquitq: " + userId);
 
        String getUserUrl = "http://localhost:8080/lms/api/v1/userCoupons/getUserByparam?userId={userId}&partnerId={partnerId}";
        UUID uId = restTemplate.getForObject(getUserUrl, UUID.class, userId.getUser_id(), partnerId);
        System.out.println("useridlms :" + uId);
     
   
        PointsAmountRequestDto pointsAmountRequestDto = new PointsAmountRequestDto();
        pointsAmountRequestDto.setUserId(userId.getUser_id());
        pointsAmountRequestDto.setPartnerId(partnerId);
        pointsAmountRequestDto.setAmount(paymentDto.getAmount());
     
 
        PointsAmountResponseDto pointsAmountResponseDto;
        if (isPointsUsed) {
            // If points are used, call the final price method
        	String finalAmountUrl = "http://localhost:8080/api/v1/lms/transactions/finalPrice";
            pointsAmountResponseDto = restTemplate.postForObject(finalAmountUrl, pointsAmountRequestDto, PointsAmountResponseDto.class);
        } else {
            //else call accrualpoints
        	String accrualPointsUrl = "http://localhost:8080/api/v1/lms/transactions/getAccrualPoints";
            pointsAmountResponseDto = restTemplate.postForObject(accrualPointsUrl, pointsAmountRequestDto, PointsAmountResponseDto.class);
        }
     
        System.out.println("Final Amount Response: " + pointsAmountResponseDto);
       
        Double pointsGained = pointsAmountResponseDto.getReceivedPoints();
        Double pointsSpent = pointsAmountResponseDto.getSpentPoints();
        System.out.println("Points Gained: " + pointsGained + ", Points Spent: " + pointsSpent);
     
 
        UserCouponRequestDto userCouponRequestDto = new UserCouponRequestDto();
        if (userCouponRequestDto.getCouponCode() != null) {
            UserValidationDto userValidationDto = new UserValidationDto();
            userValidationDto.setCouponCode(userCouponRequestDto.getCouponCode());
            userValidationDto.setuId(uId);
        String redeemCouponUrl = "http://localhost:8080/lms/api/v1/userCoupons/redeem";
            restTemplate.postForObject(redeemCouponUrl, userValidationDto, Boolean.class);
            System.out.println("Coupon redeemed successfully.");
        }
     
        LMSTransactionRequestDto lmsTransactionRequestDto = new LMSTransactionRequestDto();
        lmsTransactionRequestDto.setPartnerId(partnerId);
        lmsTransactionRequestDto.setPaymentId(payment.getPayment_id());
        lmsTransactionRequestDto.setAmount(pointsAmountResponseDto.getAmountToBePaid()); // Use final amount to be paid
        lmsTransactionRequestDto.setUserId(userId.getUser_id());
        lmsTransactionRequestDto.setPointsGained(pointsGained);
        lmsTransactionRequestDto.setPointsSpent(pointsSpent);
     
        String transactionUrl = "http://localhost:8080/api/v1/lms/transactions/newTransaction";
        restTemplate.postForObject(transactionUrl, lmsTransactionRequestDto, LMSTransactionRequestDto.class);
       
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
    }
 
    // http://localhost:8080/api/v1/quitq/payments/for/cart
    @PostMapping("/for/cart")
    public ResponseEntity<?> createPaymentForCart(@Valid @RequestBody PaymentRequestDto paymentRequestDto , @RequestParam(required = false) boolean isPointsUsed) throws ResourceNotFoundException {
        Payment payment = paymentService.createPaymentforCart(paymentRequestDto);
        PaymentDto paymentDto = paymentMapper.mapToPaymentDto(payment);
        try {
        User userId = customerServiceImpl.getUserIdByEmail(paymentDto.getEmail());
        System.out.println("useridquitq: " + userId);
     
     
        String getUserUrl = "http://localhost:8080/lms/api/v1/userCoupons/getUserByparam?userId={userId}&partnerId={partnerId}";
        UUID uId = restTemplate.getForObject(getUserUrl, UUID.class, userId.getUser_id(), partnerId);
        System.out.println("useridlms :" + uId);
     
   
        PointsAmountRequestDto pointsAmountRequestDto = new PointsAmountRequestDto();
        pointsAmountRequestDto.setUserId(userId.getUser_id());
        pointsAmountRequestDto.setPartnerId(partnerId);
        pointsAmountRequestDto.setAmount(paymentDto.getAmount());
     
 
        PointsAmountResponseDto pointsAmountResponseDto;
        if (isPointsUsed) {
            // If points are used, call the final price method
        	String finalAmountUrl = "http://localhost:8080/api/v1/lms/transactions/finalPrice";
            pointsAmountResponseDto = restTemplate.postForObject(finalAmountUrl, pointsAmountRequestDto, PointsAmountResponseDto.class);
        } else {
            //else call accrualpoints
        	String accrualPointsUrl = "http://localhost:8080/api/v1/lms/transactions/getAccrualPoints";
            pointsAmountResponseDto = restTemplate.postForObject(accrualPointsUrl, pointsAmountRequestDto, PointsAmountResponseDto.class);
        }
     
        System.out.println("Final Amount Response: " + pointsAmountResponseDto);
       
        Double pointsGained = pointsAmountResponseDto.getReceivedPoints();
        Double pointsSpent = pointsAmountResponseDto.getSpentPoints();
        System.out.println("Points Gained: " + pointsGained + ", Points Spent: " + pointsSpent);
     
 
        UserCouponRequestDto userCouponRequestDto = new UserCouponRequestDto();
        if (userCouponRequestDto.getCouponCode() != null) {
            UserValidationDto userValidationDto = new UserValidationDto();
            userValidationDto.setCouponCode(userCouponRequestDto.getCouponCode());
            userValidationDto.setuId(uId);
        String redeemCouponUrl = "http://localhost:8080/lms/api/v1/userCoupons/redeem";
            restTemplate.postForObject(redeemCouponUrl, userValidationDto, Boolean.class);
            System.out.println("Coupon redeemed successfully.");
        }
     
        LMSTransactionRequestDto lmsTransactionRequestDto = new LMSTransactionRequestDto();
        lmsTransactionRequestDto.setPartnerId(partnerId);
        lmsTransactionRequestDto.setPaymentId(payment.getPayment_id());
        lmsTransactionRequestDto.setAmount(pointsAmountResponseDto.getAmountToBePaid()); // Use final amount to be paid
        lmsTransactionRequestDto.setUserId(userId.getUser_id());
        lmsTransactionRequestDto.setPointsGained(pointsGained);
        lmsTransactionRequestDto.setPointsSpent(pointsSpent);
     
        String transactionUrl = "http://localhost:8080/api/v1/lms/transactions/newTransaction";
        restTemplate.postForObject(transactionUrl, lmsTransactionRequestDto, LMSTransactionRequestDto.class);
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
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
    
    //http://localhost:8080/api/v1/quitq/payments/getCoupons/email?email=john.doe@example.com
    @GetMapping("/getCoupons/email")
    public ResponseEntity<?> getActiveCoupons(@RequestParam("email") String email) throws ResourceNotFoundException {
        User userId = customerServiceImpl.getUserIdByEmail(email);     
    String getUserUrl = "http://localhost:8080/lms/api/v1/userCoupons/getUserByparam?userId={userId}&partnerId={partnerId}";
        UUID uId = restTemplate.getForObject(getUserUrl, UUID.class, userId.getUser_id(), partnerId);
        System.out.println("user :" + uId);       
    String getCouponsUrl = "http://localhost:8080/lms/api/v1/userCoupons/getActiveCoupons?uId={uId}";
        UserCouponDto[] userCouponDtoArray = restTemplate.getForObject(getCouponsUrl, UserCouponDto[].class, uId);
        List<UserCouponDto> userCouponDtoList = Arrays.asList(userCouponDtoArray);
        System.out.println("user :" + userCouponDtoList);
        return ResponseEntity.ok(userCouponDtoList);
    }
    
    //http://localhost:8080/api/v1/quitq/payments/getTiersStatus/email?email=john.doe@example.com
    @GetMapping("/getTiersStatus/email")
    public ResponseEntity<?> getTierStatus(@RequestParam("email") String email){
    	User userId = customerServiceImpl.getUserIdByEmail(email);
    	String getUserUrl ="http://localhost:8080/api/v1/lms/offers/getTierDetails?partnerId={partnerId}&UserId={userId}";
    	TiersOffersDto tiersOffersDto=restTemplate.getForObject(getUserUrl,TiersOffersDto.class,partnerId, userId);
    	return ResponseEntity.ok(tiersOffersDto);
    	}
 
    //http://localhost:8080/api/v1/quitq/payments/applyCoupon
    @PostMapping("/applyCoupon")
    public ResponseEntity<?> applyCoupon(@RequestBody UserCouponRequestDto userCouponRequestDto){
    String getUserUrl = "http://localhost:8080/api/v1/lms/transactions/applyCoupon";
    UserCouponResponseDto userCouponResponseDto=restTemplate.postForObject(getUserUrl,userCouponRequestDto, UserCouponResponseDto.class);
    return ResponseEntity.ok(userCouponResponseDto);
    }
 
    //http://localhost:8080/api/v1/quitq/payments/redeemCoupon
    @PostMapping("/redeemCoupon")
    public boolean redeemCoupon(@RequestBody UserValidationDto userValidationDto){
        String getUserUrl =  "http://localhost:8080/lms/api/v1/userCoupons/redeem";
        restTemplate.postForObject(getUserUrl, userValidationDto, Boolean.class );
        return true;
    }
    
    //http://localhost:8080/api/v1/quitq/payments/finalPrice
    @PostMapping("/finalPrice")
    public ResponseEntity<?> applyCoupon(@RequestBody  PointsAmountRequestDto pointsAmountRequestDto){
        String getUserUrl = "http://localhost:8080/api/v1/lms/transactions/finalPrice";
        PointsAmountResponseDto pointsAmountResponseDto=restTemplate.postForObject(getUserUrl, pointsAmountRequestDto,PointsAmountResponseDto.class);
        	return ResponseEntity.ok(pointsAmountResponseDto);
    }
    
}
