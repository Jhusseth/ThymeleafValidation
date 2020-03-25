package co.edu.icesi.ci.thymeval.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.ci.thymeval.model.User;
import co.edu.icesi.ci.thymeval.model.ValidationGroup1;
import co.edu.icesi.ci.thymeval.model.ValidationGroup2;
import co.edu.icesi.ci.thymeval.service.UserService;

@Controller
public class UserController {

	UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
		;
	}

	@GetMapping("/users/")
	public String indexUser(Model model) {
		model.addAttribute("users", userService.findAll());
		return "users/index";
	}
	
	@GetMapping("/users/add1")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "users/add-user-1";
	}

	@GetMapping("/users/add2/{id}")
	public String addUser2(@PathVariable("id") long id,Model model) {
		Optional<User> user = userService.findById(id);
		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("user", user.get());
		model.addAttribute("genders", userService.getGenders());
		model.addAttribute("types", userService.getTypes());
		return "users/add-user-2";
	}

	@PostMapping("/users/add1")
	public String saveUser(@Validated (ValidationGroup1.class) User user, BindingResult result, @RequestParam(value = "action", required = true) String action) {
	 if (!action.equals("Cancel")) {
		 if(result.hasErrors()) {
			 return "redirect:/users/add2";
		 } 
		 userService.save(user);
	 }
		 return "redirect:/users/add2/{id}";	
	}
	
	@PostMapping("/users/add2/{id}")
	public String saveUser2(@Validated (ValidationGroup2.class) User user, BindingResult result,@PathVariable("id")long id, @RequestParam(value = "action", required = true) String action) {
	 if (!action.equals("Cancel")) {
		 if(result.hasErrors()) {
			 return "redirect:/users/add1";
		 } 
		 userService.save(user);
	 }
		 return "redirect:/users/";	
	}

	@GetMapping("/users/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Optional<User> user = userService.findById(id);
		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("user", user.get());
		model.addAttribute("genders", userService.getGenders());
		model.addAttribute("types", userService.getTypes());
		return "users/update-user";
	}

	@PostMapping("/users/edit/{id}")
	public String updateUser(@Valid User user,  BindingResult result,@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action) {
		if (action != null && !action.equals("Cancel")) {
			
			if(result.hasErrors()) {
				return "redirect:/users/edit/{id}";
			}
			userService.save(user);
		}
		return "redirect:/users/";
	}

	@GetMapping("/users/del/{id}")
	public String deleteUser(@PathVariable("id") long id) {
		User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userService.delete(user);
		return "redirect:/users/";
	}
}