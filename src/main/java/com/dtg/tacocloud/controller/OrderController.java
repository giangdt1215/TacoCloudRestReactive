package com.dtg.tacocloud.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.dtg.tacocloud.data.jpa.OrderJPARepository;
import com.dtg.tacocloud.data.jpa.UserRepository;
import com.dtg.tacocloud.model.TacoOrder;
import com.dtg.tacocloud.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
	
//	private OrderRepository orderRepository;
//	
//	public OrderController(OrderRepository orderRepository) {
//		this.orderRepository = orderRepository;
//	}
	
	private OrderJPARepository orderRepository;
	
	private UserRepository userRepository;
	
	public OrderController(OrderJPARepository orderRepository, UserRepository userRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
	}

    @GetMapping("/current")
    public String orderForm(Model model){
        return "orderForm";
    }

    //Princical/Authentication parameter object
    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, 
    		SessionStatus sessionStatus, @AuthenticationPrincipal User user){
        if(errors.hasErrors())
            return "orderForm";

        log.info("Order submitted: " + order);
        
        //User user = userRepository.findByUsername(principal.getName());
        //User user = (User) authentication.getPrincipal();
        order.setUser(user);
        
        orderRepository.save(order);
        sessionStatus.setComplete();
        
        return "redirect:/";
    }

}
