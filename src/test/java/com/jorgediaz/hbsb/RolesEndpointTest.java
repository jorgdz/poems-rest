package com.jorgediaz.hbsb;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jorgediaz.hbsb.exception.custom.NoContentException;
import com.jorgediaz.hbsb.exception.custom.NotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
public class RolesEndpointTest {

	@Autowired
	private MockMvc mockMvc;
		
	/**
	 * Test find all resources exception no content
	 * @throws Exception
	 */
	@Test
	void findAllRoles () throws Exception {
		createRoles();
		
		this.mockMvc.perform(get("/api/roles"))
			.andDo(print())
	      	.andExpect(status().isNoContent())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$.exception", is(NoContentException.class.getSimpleName())));
	}
	
	/**
	 * Test find resource by id exception not found
	 * @throws Exception
	 */
	@Test
	void findRoleById () throws Exception {
		this.mockMvc.perform(get("/api/roles/{id}", 2))
			.andDo(print())
	      	.andExpect(status().isNotFound())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$.exception", is(NotFoundException.class.getSimpleName())));
	}
	
	void createRoles() {
		
	}
}
