package com.pdn.eng.Controller;





import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pdn.eng.DAO.UserRepo;
import com.pdn.eng.Model.Order;
import com.pdn.eng.Model.User;
import com.pdn.eng.Security.AuthenticationResponse;
import com.pdn.eng.Security.JwtTokenUtil;
import com.pdn.eng.Security.MyUserDetailsService;
import com.pdn.eng.Service.OrderService;

@Controller
public class UserController {
	
	@Autowired
	private OrderService orderService;
	
	
	@Autowired
	UserRepo repo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	
	//testing purpose
	@GetMapping("/hello")
	@ResponseBody
	public String hello()
	{
		return "hello";
	}
	 
	
	@PostMapping(value="/user/registration",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String register(@Valid  @RequestBody User user)
	{
		User regUser = repo.findByMobileNumber(user.getMobileNumber());
		//check whether user is already exists
		if(regUser != null) {
			return "mobileNumber is already exist";
		}
		user.setRole("user");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repo.save(user);
		//successfully registered and return the registered user
		return "successfully registered";
	}
	
	//for user login
	@PostMapping(value="/user/login",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> login(@Valid @RequestBody LoginObj login) throws Exception {

		User user = repo.findByMobileNumber(login.getMobileNumber());

		if(user == null){
			return new ResponseEntity<>(
					"user not found",
					HttpStatus.BAD_REQUEST
			);
		}

		try {
		 authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken( login.getMobileNumber(),login.getPassword(),null)
				 );
		}catch(BadCredentialsException e) {
			return new ResponseEntity<>(
					"Incorrect MobileNumber or Password.",
					HttpStatus.BAD_REQUEST
			);
		}
		
		final UserDetails userDetail=userDetailsService.loadUserByUsername(login.getMobileNumber());
		final String jwtToken=jwtTokenUtil. generateToken(userDetail);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
		
	}
	
	//check the filter and identify the user
	@GetMapping("/users/hello")
	@ResponseBody
	public String hi(@RequestHeader("Authorization") String auth)
	{
		String token = null;
		if(auth.startsWith("Bearer ")){
			token=auth.substring(7);
		}
		final String user=jwtTokenUtil.getUsernameFromToken(token);
		return user;
				
	}
	

	@PostMapping("users/orders/add")
	@ResponseBody
    public Order addOrder(@RequestHeader("Authorization") String auth, @Valid  @RequestBody Order order ){
		String token = null;
		if(auth.startsWith("Bearer ")){
			token=auth.substring(7);
		}
		final String user = jwtTokenUtil.getUsernameFromToken(token);
		order.user=user;
        return orderService.addOrder(order);
    }
	 
	
	
	
	
	
	
}
