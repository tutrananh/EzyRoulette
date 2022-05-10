package com.example.ezyroulettehttpserver.controller;

import com.example.ezyroulettehttpserver.entity.Order;
import com.example.ezyroulettehttpserver.entity.User;
import com.example.ezyroulettehttpserver.service.PaypalService;
import com.example.ezyroulettehttpserver.service.UserService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaypalController {

	@Autowired
	PaypalService service;

	@Autowired
	UserService userService;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";
	public static final String USERNAME_URL="?username=";
	@GetMapping("/addBalance")
	public String home(@RequestParam("username") String username) {
		return "addBalance";
	}

	@PostMapping("/pay")
	public String payment(@ModelAttribute("order") Order order) {
		try {
			System.out.println(order.toString());
			Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
					order.getIntent(), order.getDescription(), "http://localhost:8080/"+CANCEL_URL+USERNAME_URL+order.getUsername(),
					"http://localhost:8080/"+SUCCESS_URL+USERNAME_URL+order.getUsername());
			for(Links link:payment.getLinks()) {
				System.out.println(link.getHref());
				if(link.getRel().equals("approval_url")) {
					return "redirect:"+link.getHref();
				}
			}
			
		} catch (PayPalRESTException e) {
		
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	 @GetMapping(value = CANCEL_URL)
	    public String cancelPay() {
	        return "cancel";
	    }

	    @GetMapping(value = SUCCESS_URL)
	    public String successPay(@RequestParam("username") String username, @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
	        try {
	            Payment payment = service.executePayment(paymentId, payerId);
				float addAmount = Float.parseFloat(payment.getTransactions().get(0).getAmount().getTotal());
	            System.out.println(username + " " + addAmount);
	            if (payment.getState().equals("approved")) {
					User user = userService.updateUser(username,"none", addAmount);
	                return "success";
	            }
	        } catch (PayPalRESTException e) {
	         System.out.println(e.getMessage());
	        }
	        return "redirect:/";
	    }

}
