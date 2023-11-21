/* Author:
 * Created:
 * Matriculation Number:
 */
package com.example.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class FormController {
	@Autowired
	private FormBeanRepository formBeanRepository;

	@GetMapping("/index")
	public String form(Model model) {
		model.addAttribute("formObject", new FormBean());
		return "index";
	}

	@PostMapping("/index")
	public String formSubmit(@ModelAttribute FormBean formObject, Model model) {
		model.addAttribute("formObject", formObject);

		formBeanRepository.save(formObject);

		model.addAttribute("users", formBeanRepository.findAll());
		
		System.out.println();
		
		// Exercise: Calling APIs
		WebClient client = WebClient.create();

		ResponseEntity<String> response = client.get()
				.uri("https://dog-api.kinduff.com/api/facts")
				.retrieve()
				.toEntity(String.class).block();

		System.out.println(response.getBody());
		System.out.println("*".repeat(30));
		System.out.println(response.getStatusCode());
		System.out.println("*".repeat(30));
		System.out.println(response.getHeaders());

		return "index";
	}
}
