package guru.springframework.sfgpetclinic.controllers;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.hasSize;
import static  org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

public class OwnerControllerTest {

	
	@Mock
	OwnerService ownerService;
	
	
	@InjectMocks
	OwnerController ownerController;
	
	MockMvc mockMvc;
	
	Set<Owner> owners;
	
	
	@Before
	public void setUP() {
		MockitoAnnotations.initMocks(this);
		//init mock controller
		mockMvc = MockMvcBuilders
				.standaloneSetup(ownerController)
				.build();
		Owner owner1 = new Owner(); owner1.setId(1L);
		Owner owner2 = new Owner(); owner2.setId(2L);
		owners = new HashSet<Owner>();
		owners.add(owner1);
		owners.add(owner2);
	}

	@Test
	public void listOwnersTest() throws Exception {
		when(ownerService.findAll()).thenReturn(owners);
		mockMvc.perform(get("/owners"))
				.andExpect(status()
				.is(200))
				.andExpect(view().name("owners/index"))
				.andExpect(model().attribute("owners", hasSize(2)));
	}
	@Test
	public void findTest() throws Exception {
		when(ownerService.findAll()).thenReturn(owners);
		mockMvc.perform(get("/owners/find"))
				.andExpect(status()
				.is(200))
				.andExpect(view().name("notimplemented"));
	}
}
