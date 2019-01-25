package hello;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@Autowired
	private MessageService messageService;

	@RequestMapping("/hello")
	public Hello getMessage(HttpServletRequest request) {
		return new Hello(request.isUserInRole("ADMIN") ? messageService.getAdminMessage() : messageService.getMessage());
	}

}
