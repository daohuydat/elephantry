package com.elephantry.controller;

import java.security.Principal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elephantry.entity.Country;
import com.elephantry.entity.Customer;
import com.elephantry.entity.Package;
import com.elephantry.entity.Province;
import com.elephantry.entity.Recommendation;
import com.elephantry.entity.User;
import com.elephantry.model.E;
import com.elephantry.service.CountryService;
import com.elephantry.service.CustomerService;
import com.elephantry.service.PackageService;
import com.elephantry.service.ProvinceService;
import com.elephantry.service.UserService;
import com.elephantry.util.LanguageResolver;
import com.elephantry.util.MailHelper;

@Controller
@RequestMapping(value = { "/account", "/{lang}/account" })
public class AccountController {

	private CustomerService customerService;
	private UserService userService;
	private CountryService countryService;
	private ProvinceService provinceService;
	private PackageService packageService;

	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Autowired
	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	@Autowired
	public void setPackageService(PackageService packageService) {
		this.packageService = packageService;
	}

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin(@PathVariable("lang") Optional<String> lang, Model model, HttpSession session) {
		String language = LanguageResolver.resolve(lang, model);
		if (session.getAttribute("userid") != null) {
			return "redirect:/" + language + "/index";
		}
		return "signin";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String redirectBaseOnRole(@PathVariable("lang") Optional<String> lang, Authentication authentication,
			Model model) {
		String language = LanguageResolver.resolve(lang, model);
		try {
			List<GrantedAuthority> roles = (List<GrantedAuthority>) authentication.getAuthorities();
			if (roles.size() > 0) {
				if ("ROLE_ROOT".equalsIgnoreCase(roles.get(0).toString())
						|| "ROLE_ADMIN".equalsIgnoreCase(roles.get(0).toString())) {
					return "redirect:/" + language + "/admin/home/index";
				} else if ("ROLE_CUST".equalsIgnoreCase(roles.get(0).toString())) {
					return "redirect:/" + language + "/portal/home/index";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/" + language + "/account/signin?failed";
	}

	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String signoutPage(@PathVariable("lang") Optional<String> lang, Model model, HttpServletRequest request,
			HttpServletResponse response, Authentication authentication, HttpSession httpSession) {
		String language = LanguageResolver.resolve(lang, model);
		try {
			if (authentication != null) {
				httpSession.invalidate();
				new SecurityContextLogoutHandler().logout(request, response, authentication);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/" + language + "/account/signin?signout";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupPage(@PathVariable("lang") Optional<String> lang, Model model,
			@RequestParam("p") String packagecode) {
		String language = LanguageResolver.resolve(lang, model);
		System.out.println(packagecode);
		model.addAttribute("countries", countryService.findAll());
		if (packagecode.equals(null)) {
			model.addAttribute("packagecode", "freetrial");
		} else if(packagecode.equals("freetrial") || packagecode.equals("standard") || packagecode.equals("premium")){
			model.addAttribute("packagecode", packagecode);
		}else return "redirect:/" + language + "/index#services";
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@PathVariable("lang") Optional<String> lang, Model model, HttpServletRequest request, HttpSession httpSession,
			HttpServletResponse response, Authentication authentication) {
		String language = LanguageResolver.resolve(lang, model);
		int i = 0;
		try {
			User user = new User();
			Date date = new Date();
			user.setEmail(request.getParameter("email"));
			user.setCreatedTime(date);
			String hashed = BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt());
			user.setPassword(hashed);
			user.setRoleId(E.Role.CUSTOMER.getValue());
			user.setActive(1);
			Customer customer = new Customer();
			customer.setFirstName(request.getParameter("firstName"));
			customer.setLastName(request.getParameter("lastName"));
			customer.setGender(request.getParameter("gender"));
			customer.setPhone(request.getParameter("phone"));
			customer.setAddress(request.getParameter("address"));
			if (null != request.getParameter("province")) {
				customer.setProvince(provinceService.findById(Integer.parseInt(request.getParameter("province"))));
			}
			customer.setUser(user);

			if (null != request.getParameter("package")) {
				i = Integer.parseInt(request.getParameter("packageId"));
				customer.setPackage1(packageService.findById(1));
			}
			int a = customerService.save(customer);
			if( a > 0){
				MailHelper.sendMailSignupSuccess(user.getEmail(), customer.getFirstName() + " " + customer.getLastName() );
			}
			if (authentication != null) {
				httpSession.invalidate();
				new SecurityContextLogoutHandler().logout(request, response, authentication);
			}
			authenticateUserAndSetSession(user.getEmail(), request.getParameter("password"), request);
			httpSession = request.getSession();
			if(httpSession.getAttribute("packageSelected") == null){
				httpSession.setAttribute("packageSelected", i);
				System.out.println("signup:" + httpSession.getAttribute("packageSelected"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (i == 1) {
			return "redirect:/payment/paymentSuccess";
		} else
			return "redirect:/account/checkout";
	}

	@ResponseBody
	@RequestMapping(value = "/signup/isEmailAvailiable", method = RequestMethod.POST)
	public String isEmailAvailiable(HttpServletResponse response, @RequestParam String email) {
		System.out.println(email);
		try {
			if (!"".equalsIgnoreCase(email)) {
				User user = userService.findUserByEmail(email);
				if (user == null) {
					// System.out.println("true nek");
					return "true";
				} else {
					// System.out.println("false ne");
					return "false";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}

	@ResponseBody
	@RequestMapping(value = "/signin/isEmailExist", method = RequestMethod.POST)
	public String isEmailExist(HttpServletResponse response, @RequestParam String email) {
		try {
			if (!"".equalsIgnoreCase(email)) {
				User user = userService.findUserByEmail(email);
				if (user == null) {
					return "false";
				} else {
					return "true";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}

	@ResponseBody
	@RequestMapping(value = "/signin/checkPassword", method = RequestMethod.POST)
	public String checkPassword(HttpServletResponse response, @RequestParam String password,
			@RequestParam String email) {

		User user = userService.findUserByEmail(email);
		if (!"".equals(password) && user != null) {
			if (BCrypt.checkpw(password, user.getPassword())) {
				return "true";
			}
		}
		return "false";
	}

	@ResponseBody
	@RequestMapping(value = "/signin/forgotPassword", method = RequestMethod.POST)
	public String forgotPassword(HttpServletResponse response, @RequestParam String email) {
		JSONObject res = new JSONObject();
		String uuid = UUID.randomUUID().toString();
		String newPass = uuid;
		if (uuid.length() > 8) {
			newPass = uuid.substring(0, 8);
		}
		MailHelper.sendMailResetPassword(email, newPass);
		String hashed = BCrypt.hashpw(newPass, BCrypt.gensalt());
		User user = userService.findUserByEmail(email);
		user.setPassword(hashed);
		userService.update(user);
		try {
			res.put("noti", true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res.toString();
	}

	private void authenticateUserAndSetSession(String email, String password, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

		// generate session if one doesn't exist
		request.getSession();

		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

	@ResponseBody
	@RequestMapping(value = "/signup/getProvinces", method = RequestMethod.GET)
	public String getProvinces(@RequestParam String countryId) {
		JSONObject res = new JSONObject();
		List<Province> ls = provinceService.findByCountryId(countryId);

		try {
			JSONArray jsonArray = new JSONArray();
			for (Province r : ls) {
				JSONObject obj = getJSON(r);

				jsonArray.put(obj);
			}
			res.put("provinces", jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return res.toString();
	}

	private JSONObject getJSON(Province p) {
		JSONObject res = new JSONObject();
		try {
			res.put("provinceId", p.getProvinceId());
			res.put("provinceName", p.getProvinceName());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/signup/getPackages", method = RequestMethod.GET)
	public List<Package> getPackages() {
		return packageService.findAll();
	}
	

	@ResponseBody
	@RequestMapping(value = "/signup/getPackageById", method = RequestMethod.GET)
	public Package getPackageById(@RequestParam int id) {
		return packageService.findById(id);
	}

	@ResponseBody
	@RequestMapping(value = "/signup/updatePackage", method = RequestMethod.GET)
	public String updatePackage(@RequestParam int packageId, Principal principal, HttpSession httpSession) {
		Customer c = customerService.findByEmail(principal.getName());
		if (c != null) {
//			c.setPackage1(packageService.findById(packageId));
//			customerService.update(c);
			if(httpSession.getAttribute("packageSelected") != null){
				httpSession.setAttribute("packageSelected", packageId);
			}
		}
		return "checkout";
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public String checkoutPage(@PathVariable("lang") Optional<String> lang, Model model, Principal principal, HttpSession httpSession) {
		String language = LanguageResolver.resolve(lang, model);
		if (principal == null) {
			return "redirect:/account/signin";
		} else {
			System.out.println("checkoutID:" + httpSession.getAttribute("packageSelected"));
			if(httpSession.getAttribute("packageSelected") != null){
//				Customer c = customerService.findByEmail(principal.getName());
//				model.addAttribute("packageId", c.getPackage1().getPackageId());
				model.addAttribute("packageId", httpSession.getAttribute("packageSelected").toString());
				System.out.println("checkoutID2:" + httpSession.getAttribute("packageSelected"));
			}
			
		}

		return "checkout";
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String checkout(@PathVariable("lang") Optional<String> lang, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String language = LanguageResolver.resolve(lang, model);
		System.out.println("checkout");
		return "checkout";
	}

	@ResponseBody
	@RequestMapping(value = "/upgradePackage", method = RequestMethod.GET)
	public String upgradePackage(@PathVariable("lang") Optional<String> lang, Model model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam int packageId) {
		String language = LanguageResolver.resolve(lang, model);
		System.out.println(packageId);
		System.out.println("checkout");
		return "redirect:/account/checkout";
	}
}
