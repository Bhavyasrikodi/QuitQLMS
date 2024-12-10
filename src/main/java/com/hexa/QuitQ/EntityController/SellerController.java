package com.hexa.QuitQ.EntityController;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.QuitQ.DTO.SellerDto;
import com.hexa.QuitQ.DTO.SellerUserDto;
import com.hexa.QuitQ.Service.SellerService;
import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.exception.DuplicateEmailException;
import com.hexa.QuitQ.exception.ResourceDeletionException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.SellerMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/quitq/seller")
@CrossOrigin(origins="*")
public class SellerController {
	
	private SellerService sellerService;
	private SellerMapper sellerMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	public SellerController(SellerService sellerService,SellerMapper sellerMapper) {
		this.sellerService = sellerService;
		this.sellerMapper = sellerMapper;
	}
	
	
//	//http://localhost:8080/api/v1/quitq/seller/create
//    @PostMapping("/create")
//    public ResponseEntity<?> createSeller(@Valid @RequestBody SellerUserDto sellerUserDto) {
//        try {
//            Seller seller = sellerService.createSeller(sellerUserDto);
//            SellerDto sellerDto = sellerMapper.mapToSellerDto(seller);
//            return new ResponseEntity<>(sellerDto, HttpStatus.CREATED);
//        }catch (DuplicateEmailException e) {
//            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
//        }
//    }
	
	
	//	http://localhost:8080/api/v1/quitq/seller/getsellerbyid?id=1
    @GetMapping("/getsellerbyid")
    public ResponseEntity<?> getSellerById(@RequestParam("id") Long id) {
        try {
            Seller seller = sellerService.getSellerById(id);
            SellerDto sellerDto = sellerMapper.mapToSellerDto(seller);
            return ResponseEntity.ok(sellerDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	//	http://localhost:8080/api/v1/quitq/seller/deletesellerbyid?id=1
    @DeleteMapping("/deletesellerbyid")
    public ResponseEntity<String> deleteSellerById(@RequestParam("id") Long id) {
        try {
            boolean result = sellerService.deleteSellerById(id);
                return ResponseEntity.ok("Deleted seller with id = " + id);           
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (ResourceDeletionException e) {
        	return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    // http://localhost:8080/api/v1/quitq/seller/updateseller?email=lakshmii@example.com
    @PutMapping("/updateseller")
    public ResponseEntity<?> updateSeller(
            @RequestParam String email, 
            @Valid @RequestBody SellerDto sellerDto) throws ResourceNotFoundException {
        try {
            Seller updatedSeller = sellerService.updateSeller(email, sellerDto);
            SellerDto updatedSellerDto = sellerMapper.mapToSellerDto(updatedSeller);
            return ResponseEntity.ok(updatedSellerDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }
    
    @GetMapping("/email")
    public ResponseEntity<?> getSellerByEmail(@RequestParam("email") String email) {
        try {
            Seller seller = sellerService.findSellerByEmail(email);
            SellerDto sellerDto = sellerMapper.mapToSellerDto(seller);
            return ResponseEntity.ok(sellerDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
