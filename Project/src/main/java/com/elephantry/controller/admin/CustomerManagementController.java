package com.elephantry.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elephantry.entity.Customer;
import com.elephantry.entity.User;
import com.elephantry.service.CustomerService;
import com.elephantry.service.UserService;
import com.elephantry.util.LanguageResolver;

@Controller
@RequestMapping(value = { "/{lang}/admin/customerManagement", "/admin/customerManagement" })
public class CustomerManagementController {
	private CustomerService customerService;
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewCustomer(@PathVariable("lang") Optional<String> lang, Model model){
		LanguageResolver.resolve(lang, model);
		return "adminViewCustomer";
	}
	

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "email") String email, @RequestParam(value = "keyword") String keyword,
    		@RequestParam(value = "pageNum") int pageNum) {
    	JSONObject json = new JSONObject();
    	try {
			JSONArray jArray = new JSONArray();
			List<Customer> listCustomer = customerService.search(email, keyword, pageNum, 5);
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
			for (Customer customer : listCustomer) {
				jArray.put(jsonParse(customer,formatter));
			}
			json.put("customers", jArray);
			json.put("customerCount", customerService.getCustomerCount(email,keyword));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return json.toString();
    }
    
    @ResponseBody
	@RequestMapping(value = "/isEmailAvailable", method = RequestMethod.POST)
	public String isEmailAvailable(@RequestParam("email") String email) {
		JSONObject res = new JSONObject();
		try {
			Customer customer = customerService.findByEmail(email);
			if (customer == null) {
				res.put("available", true);
			} else
				res.put("available", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res.toString();
	}
    
    @ResponseBody
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public String updateStatus(@RequestParam("customerId") int customerId, @RequestParam("action") String action){
    	JSONObject json = new JSONObject();
    	try {
			User user = userService.findById(customerId);
			if (user!=null) {
				if (action.equals("Deactivate")) {
					user.setActive(0);
				}else{
					user.setActive(1);
				}
				userService.update(user);
				json.put("status", user.getActive());
				json.put("success", true);
			}else{
				json.put("success", false);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return json.toString();
    }
    
    private JSONObject jsonParse(Customer c, DateFormat formatter){
    	JSONObject json = new JSONObject();
    	try {
			json.put("firstName", c.getFirstName());
			json.put("lastName", c.getLastName());
			json.put("gender", c.getGender());
			if (c.getDoB()!=null) {
				json.put("dob", formatter.format(c.getDoB()));
			}else{
				json.put("dob", "");
			}
			if (c.getWebsite()!=null) {
				json.put("website", c.getWebsite());
			}else{
				json.put("website", "");
			}
			if (c.getCompany()!=null) {
				json.put("company", c.getCompany());
			}else{
				json.put("company", "");
			}
			if (c.getPhone()!=null) {
				json.put("phone", c.getPhone());
			}else{
				json.put("phone", "");
			}
			if (c.getAddress()!=null) {
				json.put("address", c.getAddress());
			}else{
				json.put("address", "");
			}
			if (c.getExpiredCredit()!=null) {
				json.put("expiredCredit", c.getExpiredCredit());
			}else{
				json.put("expiredCredit", "");
			}
			json.put("customerId", c.getCustomerId());
			json.put("province", c.getProvince().getProvinceName());
			json.put("package1", c.getPackage1().getPackageName());
			json.put("email", c.getUser().getEmail());
			json.put("active", c.getUser().getActive());
//			System.out.println(c.getFirstName()+" "+c.getLastName()+" "+c.getPhone());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return json;
    }
}
