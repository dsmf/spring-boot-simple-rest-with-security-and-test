package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageService implements MessageServiceInterface {
	
	private RestTemplate restTemplate;
	
	@Autowired
	public MessageService(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public String getAdminMessage() {
		ResponseEntity<String> response = restTemplate.getForEntity("https://example.com/admin/message", String.class);
		return response.getBody();
	}

	@Override
	public String getMessage() {
		ResponseEntity<String> response = restTemplate.getForEntity("https://example.com/user/message", String.class);
		return response.getBody();
	}

}
