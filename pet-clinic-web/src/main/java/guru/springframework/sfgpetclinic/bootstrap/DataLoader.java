package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;


    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(vetService.findAll().size() == 0)
            loadData();
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType dogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        PetType catPetType = petTypeService.save(cat);

        System.out.println("Loaded pet types...");

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setCity("San Diego");
        owner1.setAddress("233 Evandale");
        owner1.setTelephone("2354345");

        Pet pet1 = new Pet();
        pet1.setPetType(dogPetType);
        pet1.setName("Kaizer");
        pet1.setBirthDate(LocalDate.now());
        pet1.setOwner(owner1);
        owner1.getPets().add(pet1);

        ownerService.save(owner1);

        Visit visit = new Visit();
        visit.setPet(pet1);
        visit.setDate(LocalDate.now());
        visit.setDescription("Sneazzy Cat");
        visitService.save(visit);


        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenane");
        owner2.setCity("Dallas");
        owner2.setAddress("2 Street");
        owner2.setTelephone("8392875");

        Pet pet2 = new Pet();
        pet2.setPetType(catPetType);
        pet2.setName("Pussy");
        pet2.setBirthDate(LocalDate.now());
        pet2.setOwner(owner2);
        owner2.getPets().add(pet2);

        ownerService.save(owner2);

        System.out.println("Loaded owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");



        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");

        Specialty savedRadiology = specialtyService.save(radiology);
        Specialty surgery = new Specialty();
        radiology.setDescription("Surgery");
        Specialty savedSurgery = specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        radiology.setDescription("Dentistry");

        vet1.getSpecialties().add(savedRadiology);
        vet2.getSpecialties().add(savedSurgery);
        vetService.save(vet1);
        vetService.save(vet2);
        System.out.println("Loaded vets...");
    }
}
