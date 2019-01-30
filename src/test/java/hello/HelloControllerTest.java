package hello;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

	@MockBean
	private RestTemplate restTemplate;

	@Autowired
	private MockMvc mvc;

	@Before
	public void before() {

		when(restTemplate.getForEntity(contains("/admin/message"), any()))
				.thenReturn(ResponseEntity.ok().body("Hello admin"));

		when(restTemplate.getForEntity(contains("/user/message"), any()))
				.thenReturn(ResponseEntity.ok().body("Hello user"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testAdminAccess_Ok() throws Exception {

		mvc.perform(get("/hello")) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.message").value("Hello admin"));
	}

	@Test
	@WithMockUser(roles = "USER")
	public void testUserAccess_Ok() throws Exception {

		mvc.perform(get("/hello")) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.message").value("Hello user"));
	}

}
