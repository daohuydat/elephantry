package com.elephantry.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.elephantry.service.TranslationService;
import com.elephantry.util.LanguageResolver;
import com.elephantry.util.MailHelper;

@Controller
@RequestMapping(value={"/","/{lang}"})
public class IndexController {

	@Autowired
	TranslationService translationService;

	@RequestMapping(value = { "", "/", "/index" })
	public String index(@PathVariable("lang") Optional<String> lang, Model model) {
		LanguageResolver.resolve(lang, model);
		
		return "index";
	}

	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String error403(@PathVariable("lang") Optional<String> lang, Model model) {
		LanguageResolver.resolve(lang, model);
		return "403";
	}

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String error404(@PathVariable("lang") Optional<String> lang, Model model) {
		LanguageResolver.resolve(lang, model);
		return "404";
	}
	
	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String error500(@PathVariable("lang") Optional<String> lang, Model model) {
		LanguageResolver.resolve(lang, model);
		return "500";
	}

	
	@RequestMapping(value = "/sendUsEmail", method = RequestMethod.POST)
	public String sendUsEmail(@PathVariable("lang") Optional<String> lang, Model model,
			@RequestParam("fullName") String fullName, @RequestParam("email") String email, @RequestParam("message") String message) {
		
		String recipient = "datdhse03781@outlook.com";
        String subject =  fullName + " send you from Elephantry";
        String content = "<p>"+message+"</p><br />" + 
        "<a href='mailto:"+email+"'>"+email+"</a>";

       
        try {
            MailHelper.sendMail(recipient, subject, content);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return "500";
        } 
        return "redirect:/index";
	}

}
