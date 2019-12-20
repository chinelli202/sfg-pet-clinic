package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@Profile({"default","map"})
public class VisitServiceMap extends AbstractMapService<Visit, Long> implements VisitService {
    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Visit object) {
        super.delete(object);
    }

    @Override
    public Visit save(Visit object) {

        //save logic. make sure the persisted visit object has a valid pet attached to it. valid here meaning
        //with existing id. so raise exception if no pets, or pet ids null

        if(object==null||object.getPet()==null||object.getPet().getId()==null){
            throw new RuntimeException("Error with object. Either null or pet isn't valid");
        }

        return super.save(object);
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }
}
