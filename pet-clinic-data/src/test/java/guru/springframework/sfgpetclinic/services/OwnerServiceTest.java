
package guru.springframework.sfgpetclinic.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.map.OwnerServiceMap;
import guru.springframework.sfgpetclinic.services.map.PetServiceMap;
import guru.springframework.sfgpetclinic.services.map.PetTypeServiceMap;

public class OwnerServiceTest {
	
	OwnerServiceMap ownerServiceMap;
	Owner testOwner;
	Long setId;
	String ownerLastName;
	@Before
	public void setUp() {
		ownerServiceMap = new  OwnerServiceMap(new PetServiceMap(), new PetTypeServiceMap());
		setId = 1L;
		ownerLastName = "paul";
		testOwner = new Owner();
		testOwner.setId(setId);
		
		testOwner.setLastName(ownerLastName);
		ownerServiceMap.save(testOwner);
	}
	
	
	@Test
	public void findAll() {
		Set<Owner> resultSet = ownerServiceMap.findAll();
		assertEquals(1,resultSet.size());
	}
	
	@Test
	public void findById() {
		Owner ownerResult = ownerServiceMap.findById(setId);
		assertEquals(setId, ownerResult.getId());
	}
	@Test
	public void Save() {
		Long lid = 2L;
		Owner owner = new Owner();
		owner.setId(lid);
		Owner savedOwner = ownerServiceMap.save(owner);
		
		//
		
		assertEquals(lid, savedOwner.getId());
	}
	
	@Test
	public void delete() {
		ownerServiceMap.delete(testOwner);
		
		Set<Owner> returnedOwners = ownerServiceMap.findAll();
		
		assertEquals(0, returnedOwners.size());
	}
	
	@Test
	public void findByLastName() {
		Owner foundOwner = ownerServiceMap.findByLastName(ownerLastName);
		assertNotNull(foundOwner);
		assertEquals(ownerLastName, foundOwner.getLastName());
	}
}
