package com.elephantry.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elephantry.entity.User;
import com.elephantry.model.E;
import com.elephantry.service.UserService;
import com.elephantry.util.LanguageResolver;
import com.elephantry.util.MailHelper;

@Controller
@RequestMapping(value = { "/{lang}/admin/adminManagement", "/admin/adminManagement" })
public class AdminManagementController {
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewAdmin(@PathVariable("lang") Optional<String> lang, Model model, @RequestParam(value="s", required=false) String s){
		LanguageResolver.resolve(lang, model);
		if (s != null) {
			if (s.equals("1")) {
				model.addAttribute("successMessage", "done");
			}else{
				model.addAttribute("successMessage", "");
			}
		}
		return "adminViewAdmin";
	}
	
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchAdmin(@RequestParam("q") String q, @RequestParam("pageNum") int pageNum){
		JSONObject json = new JSONObject();
		try {
			List<User> lu = userService.searchAdmin(q, pageNum, 5);
		    JSONArray jArray = new JSONArray();
			for (User user : lu) {
				jArray.put(jsonParse(user));
			}
			json.put("users", jArray);
			json.put("adminCount", userService.getAdminCount(q));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@PathVariable("lang") Optional<String> lang, Model model){
		LanguageResolver.resolve(lang, model);
		return "addNewAdmin";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String doAdd(@PathVariable("lang") Optional<String> lang, Model model, @RequestParam("txtEmail") String email){
		String language = LanguageResolver.resolve(lang, model);
		User u = new User();
		try {
			u.setEmail(email);
			String uuid = UUID.randomUUID().toString();
			String newPass = uuid;
			if(uuid.length() > 8){
				newPass = uuid.substring(0,8);
			}
			String hashed = BCrypt.hashpw(newPass, BCrypt.gensalt());
			u.setPassword(hashed);
			u.setRoleId(E.Role.ADMIN.getValue());
			userService.save(u);
			MailHelper.sendMailAddNewAdmin(email, newPass);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return "redirect:/"+language+"/admin/adminManagement/view?s=1";
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public String updateStatus(@RequestParam("userId") int userId, @RequestParam("action") String action){
		JSONObject json = new JSONObject();
		try {
			User u = userService.findById(userId);
			if (u!=null) {
				if (action.equals("Deactivate")) {
					u.setActive(0);
				}else{
					u.setActive(1);
				}
				userService.update(u);
				json.put("active", u.getActive());
				json.put("success", true);
			}else{
				json.put("success", false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/isEmailAvailable", method = RequestMethod.POST)
	public String isEmailAvailable(@RequestParam("email") String email) {
		JSONObject res = new JSONObject();
		try {
			User user = userService.findUserByEmail(email);
			if (user == null) {
				res.put("available", true);
			} else
				res.put("available", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res.toString();
	}
	
	private JSONObject jsonParse(User u){
		JSONObject j = new JSONObject();
		try {
			j.put("userId", u.getUserId());
			j.put("email", u.getEmail());
			j.put("active", u.getActive());
			j.put("createdTime", u.getCreatedTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}
}
