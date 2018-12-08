package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/hello")
	public Hello greeting(@RequestParam(value = "name") String name) {
		return new Hello(counter.incrementAndGet(), "Hello " + name);
	}

	@RequestMapping("/admin")
	public Hello adminHello(@RequestParam(value = "name") String name) {
		return new Hello(counter.incrementAndGet(), "Hello admin " + name);
	}
}
