package guru.springframework.sfgpetclinic.controllers;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static  org.mockito.Mockito.when;
import static  org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
	
	@Mock
	Owner owner;
	
	
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

	/*@Test
	public void listOwnersTest() throws Exception {
		when(ownerService.findAll()).thenReturn(owners);
		mockMvc.perform(get("/owners"))
				.andExpect(status()
				.is(200))
				.andExpect(view().name("owners/findOwners"))
				.andExpect(model().attribute("owners", hasSize(2)));
	}*/
	
	
	@Test
	public void findTest() throws Exception {
		//when(ownerService.findAll()).thenReturn(owners);
		mockMvc.perform(get("/owners/find"))
				.andExpect(status()
				.is(200))
				.andExpect(view().name("owners/findOwner"));
	}
	
	@Test
	public void displayOwner() throws Exception {
		
		
		
		Owner owner = new Owner();
		owner.setId(1L);
		
		when(ownerService.findById(anyLong())).thenReturn(owner);
		mockMvc.perform(get("/owners/123"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/ownerDetails"))
			.andExpect(model().attribute("owner", hasProperty("id", is (1l))));
	}
	
	@Test 
	public void findOwnerSingleTest() throws Exception {
		Owner owner = new Owner();
		owner.setId(1L);
		owner.setLastName("Allen");
		
		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(java.util.Arrays.asList(owner));
		
		mockMvc.perform(get("/owners"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/" + owner.getId()));
	}
	
	@Test
	public void findOwnersAllTest() throws Exception {
		Owner owner1 = new Owner();
		owner1.setId(1L);
		owner1.setLastName("Allen");
		
		Owner owner2 = new Owner();
		owner2.setId(2L);
		owner2.setLastName("Allen");
		
		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(owner1,owner2));
		
		mockMvc.perform(get("/owners"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/ownersList"))
			.andExpect(model().attribute("selections", hasSize(2)));
	}
	
	@Test
	public void initNewOwnerFormTest() throws Exception {
		mockMvc.perform(get("/owners/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}
	
	@Test
	public void processNewOwnerFormTest() throws Exception {
		Owner owner = new Owner();
		owner.setId(1L);
		
		when(ownerService.save(any())).thenReturn(owner);
		
		mockMvc.perform(post("/owners/new"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/1"));
	}
}
