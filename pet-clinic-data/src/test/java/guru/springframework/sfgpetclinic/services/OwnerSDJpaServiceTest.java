package guru.springframework.sfgpetclinic.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentMatcher;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;

public class OwnerSDJpaServiceTest {
	
	private static final String lastName = "Bertrand";
	//mocked repos
	@Mock 
	OwnerRepository ownerRepository;
	@Mock
	PetRepository petRepository;
	@Mock
	PetTypeRepository petTypeRepository;

	Owner testOwner;
	
	@InjectMocks
	OwnerSDJpaService service;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		//owner setup
		long id = 1L;
		testOwner = new Owner();
		testOwner.setId(id);
		testOwner.setLastName(lastName);
	}
	
	@Test
	public void findByLastNameTest() {
		
		
		
		Mockito.when(ownerRepository.findByLastName(any())).thenReturn(testOwner);	
		Owner resultOwner = service.findByLastName(lastName);
		assertEquals(lastName, resultOwner.getLastName());	
	}
	
	@Test
	public void save() {
		Mockito.when(ownerRepository.save(testOwner)).thenReturn(testOwner);
		Owner returnOwner = service.save(testOwner);
		
		assertNotNull(returnOwner);
		verify(ownerRepository).save(any());
		
	}
	
	@Test 
	public void findById() {
		Owner specialOwner = new Owner();
		Long idSpecial = 2L;
		specialOwner.setId(idSpecial);
		
		
		Mockito.when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(specialOwner));
		
		Owner returnOwner = service.findById(2L);
		assertNotNull(returnOwner);
		assertEquals(idSpecial, returnOwner.getId());
		
	}
	
	@Test
	public void findByNotFound() {
		
		Mockito.when(ownerRepository.findById(null)).thenReturn(Optional.empty());
		
		Owner returnOwner = service.findById(1L);
		assertNull(returnOwner);
	}
	
	@Test
	public void delete(){
		service.delete(testOwner);
		verify(ownerRepository).delete(any());
	}
	
	@Test
	public void deleteById(){
		service.deleteById(1L);
		verify(ownerRepository).deleteById(anyLong());
	}
}
