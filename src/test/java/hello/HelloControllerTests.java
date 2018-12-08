package hello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTests {

	@Autowired
	private MockMvc mvc;

	@Test
	@WithMockUser(roles = "ADMIN")
	public void givenAdmin_whenAccessingAdminUrl_thenOk() throws Exception {
		mvc.perform(get("/admin").param("name", "foo")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("Hello admin foo"));
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void givenAdmin_whenAccessingAdminUrl_withoutNameParam_thenOkWithDefault() throws Exception {
		mvc.perform(get("/admin")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("Hello admin foo"));
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void givenUser_whenAccessingHelloUrl_thenOk() throws Exception {
		mvc.perform(get("/hello").param("name", "bar")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("Hello bar"));
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void givenUser_whenAccessingAdminUrl_thenReturnForbidden() throws Exception {
		mvc.perform(get("/admin").param("name", "foo")).andDo(print()).andExpect(status().isForbidden());
	}

}
