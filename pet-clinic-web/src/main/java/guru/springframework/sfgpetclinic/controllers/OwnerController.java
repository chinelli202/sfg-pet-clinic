package guru.springframework.sfgpetclinic.controllers;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;


@RequestMapping({"/owners"})
@Controller
public class OwnerController {

    private final OwnerService ownerService;
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /*@RequestMapping({"","/","/index","/index.html"})
    public String listOwners(Model model){
        model.addAttribute("owners",ownerService.findAll());
        return "owners/index";
    }*/
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
    	dataBinder.setDisallowedFields("id");
    }
    
    @RequestMapping("/find")
    public String find(Model model) {
    	model.addAttribute("owner", new Owner());
    	return "owners/findOwner";
    }
    
    @RequestMapping("/{ownerId}")
    public ModelAndView showOwmer(@PathVariable Long ownerId) {
    	ModelAndView mav = new ModelAndView("owners/ownerDetails");
    	mav.addObject("owner", ownerService.findById(ownerId));
    	return mav;
    }
    
    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {
    	
    	if(owner.getLastName() == null) {
    		owner.setLastName("");
    	}
    	
    	List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
    	
    	if(results.isEmpty()) {
    		result.rejectValue("lastName", "notFound", "not found");
    		return "owners/findOwner";
    	}
    	
    	else if(results.size() == 1) {
    		Owner ownerResult = results.iterator().next();
    		return "redirect:/owners/" + ownerResult.getId();
    	}
    	
    	else {
    		model.put("selections", results);
    		return "owners/ownersList";
    	}
    }
    
    @GetMapping("/new")
    public String initNewOwnerForm(Model model) {
    	model.addAttribute("owner",new Owner());
    	return "owners/createOrUpdateOwnerForm";
    }
    
    @PostMapping("/new")
    public String processNewOwnerForm(@Valid Owner owner, BindingResult result) {
    	if(result.hasErrors()) {
    		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    	}
    	else {
    		Owner returnedOwner = ownerService.save(owner);
    		return "redirect:/owners/"+returnedOwner.getId();    		
    	}
    }
    
    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") Long ownerId, Model model) {
    	model.addAttribute("owner", ownerService.findById(ownerId));
    	return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") Long ownerId) {
    	if(result.hasErrors()) {
    		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    	}
    	
    	else {
    		owner.setId(ownerId);
    		Owner savedOwner = ownerService.save(owner);
    		return "redirect:/owners/"+ ownerId;
    	}
    }
}
