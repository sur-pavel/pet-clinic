package ru.sur_pavel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import ru.sur_pavel.domain.Pet;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PetResourceProcessor implements ResourceProcessor<Resource<Pet>> {

    private final RepositoryEntityLinks entityLinks;

    @Autowired
    public PetResourceProcessor(RepositoryEntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @Override
    public Resource<Pet> process(Resource<Pet> resource) {
        Pet pet = resource.getContent();
//        if (hasAccessToModify(pet)) {
            Pet.Status status = pet.getStatus();
            if (status == Pet.Status.NEW) {
                resource.add(entityLinks.linkForSingleResource(Pet.class, pet.getId()).withRel("update"));
                resource.add(entityLinks.linkForSingleResource(Pet.class, pet.getId()).withRel("deletion"));
                resource.add(linkTo(methodOn(PetResourceController.class).publish(pet.getId(), null)).withRel("publishing"));
            }
            if (status == Pet.Status.PUBLISHED) {
                resource.add(linkTo(methodOn(PetResourceController.class).expire(pet.getId(), null)).withRel("expiration"));
            }
//        }
        return resource;
    }

/*
    private static boolean hasAccessToModify(Pet pet) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }
        CustomUserDetailsService.CustomUserDetails principal = (CustomUserDetailsService.CustomUserDetails) auth.getPrincipal();
        return principal != null && pet.getUser().getPhoneNumber().equals(principal.getUsername());
    }
*/

}
