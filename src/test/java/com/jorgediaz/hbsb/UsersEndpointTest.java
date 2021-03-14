package com.jorgediaz.hbsb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorgediaz.hbsb.entity.User;
import com.jorgediaz.hbsb.services.users.UserService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersEndpointTest {
		
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService serviceUser;
		
	/**
	 * Test find all resources
	 * @throws Exception
	 */
	@Test
	void findAll () throws Exception {
		createUsers();
		
		this.mockMvc.perform(get("/api/users"))
			.andDo(print())
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$[1].email", is("carlosperez@gmail.com")))
	      	.andExpect(jsonPath("$[1].firstname", is("Carlos")))
	      	.andExpect(jsonPath("$[1].lastname", is("Pérez")))
	      	.andExpect(jsonPath("$", hasSize(4)));
	}
	
	/**
	 * Test find by id resource
	 * @throws Exception
	 */
	@Test
	void findById () throws Exception {
		this.mockMvc.perform(get("/api/users/{id}", 3))
			.andDo(print())
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$.id", is(3)));
	}
	
	/**
	 * Test create resource
	 * @throws Exception
	 */
	@Test
	void create () throws Exception {
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("email", "example@example.com");
		user.put("enabled" ,false);
		user.put("firstname" ,"Julian");
		user.put("lastname", "Assange");
		user.put("password", "liberenajulian");
		user.put("tokenexpired", false);
		
		this.mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(user)))
			.andDo(print())
			.andExpect(status().isMovedPermanently())
			.andExpect(redirectedUrl("http://localhost/api/users/1"));
	}
	
	/**
	 * Test update resource by id
	 * @throws Exception
	 */
	@Test
	void update () throws Exception {
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("email", "renealbert@hotmail.com");
		user.put("enabled", true);
		user.put("firstname" ,"René");
		user.put("lastname", "Balverde");
		
		Long userId = 1L;
		
		this.mockMvc.perform(put("/api/users/{id}", userId)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(user)))
			.andDo(print())
			.andExpect(status().isMovedPermanently())
			.andExpect(redirectedUrl(String.format("http://localhost/api/users/%s", userId)));
		
		this.mockMvc.perform(get("/api/users/{id}", userId))
			.andDo(print())
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$.enabled", is(true)))
	      	.andExpect(jsonPath("$.email", is("renealbert@hotmail.com")));
	}
	
	/**
	 * Test delete by id resource
	 * @throws Exception
	 */
	@Test
	void deleteById () throws Exception {
		this.mockMvc.perform(delete("/api/users/{id}", 2))
			.andDo(print())
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$.message", is("Usuario eliminado.")))
	      	.andExpect(jsonPath("$.id", is(2)));
	}
	
	void createUsers() {
		User user1 = new User ();
		user1.setEmail("jdiazm470@utb.edu.ec");
		user1.setEnabled(false);
		user1.setFirstname("Jorge");
		user1.setLastname("Diaz");
		user1.setPassword("1234");
		user1.setTokenexpired(false);
		this.serviceUser.save(user1);
		
		User user2 = new User ();
		user2.setEmail("carlosperez@gmail.com");
		user2.setEnabled(false);
		user2.setFirstname("Carlos");
		user2.setLastname("Pérez");
		user2.setPassword("12345");
		user2.setTokenexpired(false);
		this.serviceUser.save(user2);
		
		User user3 = new User ();
		user3.setEmail("jdzm@outlook.es");
		user3.setEnabled(false);
		user3.setFirstname("Jorge");
		user3.setLastname("Diaz Montoya");
		user3.setPassword("jdiaz1994");
		user3.setTokenexpired(false);
		this.serviceUser.save(user3);
	}
}
