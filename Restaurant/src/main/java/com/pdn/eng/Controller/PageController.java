package com.pdn.eng.Controller;

import com.pdn.eng.DAO.UserRepo;
import com.pdn.eng.Model.Order;
import com.pdn.eng.Service.OrderService;
import com.pdn.eng.Model.Food;
import com.pdn.eng.Model.User;
import com.pdn.eng.Service.FoodService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class PageController {

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    @Autowired
    private FoodService foodService;
    
    @Autowired
	UserRepo repo;
    
    @Autowired
    private OrderService orderService;
    
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ModelAndView home() {
        List<Food> allFoods = foodService.getAllFoods();
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("foods", allFoods);
        return mv;
    }

    @PostMapping("/add-Food")
    public ModelAndView add(@Valid Food food, @RequestParam("imageFile") MultipartFile imageFile,HttpSession session) {
    	
    	//check whether admin has logged
    	if(session.getAttribute("admin")==null) {
    		ModelAndView mv = new ModelAndView("adminLogin");
    		mv.addObject("msg","Please Login");
    		return mv;
    	}
    	
        //original file name
        String originalFileName = imageFile.getOriginalFilename();
        //if we only store original file name it will cause duplicates.
        //therefore get current date time and append it with original file name and extension
        String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
        String time = new SimpleDateFormat("_HHmmss").format(new Date());

        String arr[] = originalFileName.split("\\.");
        if (arr.length != 2) {
            return new ModelAndView().addObject("image", "wrong file name");
        }
        String prefix = arr[0];
        String extension = arr[1];
        String fileName = prefix + date + time + "." + extension;
        //create path to write
        Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
        //write file
        try {
            Files.write(fileNameAndPath, imageFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        food.setFoodImage(fileName);
        foodService.createFood(food);
        ModelAndView mv = new ModelAndView("addFood");
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView addPage(HttpSession session) {
    	//check whether admin has logged
    	if(session.getAttribute("admin")==null) {
    		ModelAndView mv = new ModelAndView("adminLogin");
    		mv.addObject("msg","Please Login");
    		return mv;
    	}
        ModelAndView mv = new ModelAndView("addFood");
        return mv;
    }

    @GetMapping(value = "/show/{id}")
    public ModelAndView showFood(@PathVariable("id") String id) throws IOException {
        Food food = foodService.findFoodById(id);
        if(food == null){
            return new ModelAndView();
        }
        String fileName = food.getFoodImage();
        Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
        File file = new File(fileNameAndPath.toString());
        FileInputStream fis=new FileInputStream(file);
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        int b;
        byte[] buffer = new byte[1024];
        while((b=fis.read(buffer))!=-1){
            bos.write(buffer,0,b);
        }
        byte[] fileBytes=bos.toByteArray();
        fis.close();
        bos.close();


        byte[] encoded= Base64.encodeBase64(fileBytes);
        String encodedString = new String(encoded);

        ModelAndView map = new ModelAndView("showImage");
        map.addObject("image", encodedString);
        map.addObject("food",food);

        return map;
    }
    
    @RequestMapping("/login")
	public ModelAndView login(@Valid @RequestParam String number,@Valid @RequestParam String password,HttpSession session) {
    	
    	User regUser = repo.findByMobileNumber(number);
    	boolean isPasswordMatch = passwordEncoder.matches(password, regUser.getPassword());
    	
    	
    	if(!isPasswordMatch || !regUser.getRole().equals("admin")) {
    		ModelAndView mv = new ModelAndView("adminLogin");
    		mv.addObject("msg","Invalid Login");
    		return mv;
    	}
    	
    	//session expire time 6 hrs
    	session.setMaxInactiveInterval(60*60*6);
    	//store variable in a session
    	session.setAttribute("admin", "admin");
    	
    	//redirect admin to add food page
    	 ModelAndView mv = new ModelAndView("addFood");
         return mv;
	}
    
    @RequestMapping("/loginPage")
	public ModelAndView login() {
		return new ModelAndView("adminLogin");
	}
    
    @RequestMapping("/logOutPage")
  	public ModelAndView logOut(HttpSession session) {
    	if (session.getAttribute("admin")!= null) {  
    	    session.invalidate();
    	}
    	 List<Food> allFoods = foodService.getAllFoods();
         ModelAndView mv = new ModelAndView("index");
         mv.addObject("foods", allFoods);
         return mv;
  	}
    
    @GetMapping("/orders")
    public ModelAndView viewOrders(HttpSession session) {
    	
    	//check whether admin has logged
    	if(session.getAttribute("admin")==null) {
    		ModelAndView mv = new ModelAndView("adminLogin");
    		mv.addObject("msg","Please Login");
    		return mv;
    	}
    	List<Food> allFoods = foodService.getAllFoods();
        HashMap<String ,String > foodMap = new HashMap<>();
        for (int i = 0; i < allFoods.size(); i++) {
            foodMap.put(allFoods.get(i).getFoodId(),allFoods.get(i).getFoodName());
        }
        List<Order> allOrders = (List<Order>) orderService.getOrders();
        ModelAndView mv = new ModelAndView("orders");
        mv.addObject("orders", allOrders);
        mv.addObject("map",foodMap);
        return mv;
    }

}
