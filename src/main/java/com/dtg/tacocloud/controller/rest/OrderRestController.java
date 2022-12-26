package com.dtg.tacocloud.controller.rest;

import java.util.Optional;

import com.dtg.tacocloud.messaging.OrderMessagingService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dtg.tacocloud.data.jpa.OrderJPARepository;
import com.dtg.tacocloud.model.TacoOrder;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class OrderRestController {

	private OrderJPARepository repo;
	private OrderMessagingService messageService;

	public OrderRestController(
			OrderJPARepository orderRepo,
			OrderMessagingService messageService) {
		this.repo = orderRepo;
		this.messageService = messageService;
	}

	@GetMapping
	public Iterable<TacoOrder> allOrders(){
		return repo.findAll();
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public TacoOrder postOrder(@RequestBody TacoOrder order) {
		messageService.sendOrder(order);
		return repo.save(order);
	}
	
	@PutMapping(path="/{orderId}", consumes="application/json")
    public TacoOrder putOrder(@PathVariable("orderId") Long orderId,
    		@RequestBody TacoOrder order) {
    	order.setId(orderId);
    	return repo.save(order);
    }
    
    @PatchMapping(path="/{orderId}", consumes = "application/json")
    public TacoOrder patchOrder(@PathVariable("orderId") Long id,
    		@RequestBody TacoOrder patch) {
    	Optional<TacoOrder> optOrder = repo.findById(id);
    	if(optOrder.isPresent()) {
    		TacoOrder order = optOrder.get();
    		
    		if(patch.getDeliveryName() != null) {
    			order.setDeliveryName(patch.getDeliveryName());
    		}
    		
    		if(patch.getDeliveryStreet() != null) {
    			order.setDeliveryStreet(patch.getDeliveryStreet());
    		}
    		
    		if(patch.getDeliveryCity() != null) {
    			order.setDeliveryCity(patch.getDeliveryCity());
    		}
    		
    		if(patch.getDeliveryState() != null) {
    			order.setDeliveryState(patch.getDeliveryState());
    		}
    		
    		if(patch.getDeliveryZip() != null) {
    			order.setDeliveryZip(patch.getDeliveryZip());
    		}
    		
    		if(patch.getCcNumber() != null) {
    			order.setCcNumber(patch.getCcNumber());
    		}
    		
    		if(patch.getCcExpiration() != null) {
    			order.setCcExpiration(patch.getCcExpiration());
    		}
    		
    		if(patch.getCcCVV() != null) {
    			order.setCcCVV(patch.getCcCVV());
    		}
    		
    		return repo.save(order);
    	}
    	
    	return null;
    }
    
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long id) {
    	try {
    		repo.deleteById(id);
    	} catch (EmptyResultDataAccessException e) {
    		
    	}
    	
    }
}
