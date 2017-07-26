package ru.sur_pavel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.sur_pavel.domain.InvalidPetStateTransitionException;
import ru.sur_pavel.domain.Pet;
import ru.sur_pavel.domain.PetService;

@RepositoryRestController
public class PetResourceController {

    private final PetService petService;

    @Autowired
    public PetResourceController(PetService petService) {
        this.petService = petService;
    }

    @ResponseBody
    @RequestMapping(value = "/pets/{id}/publishing", method = RequestMethod.PUT, produces = "application/hal+json")
    public PersistentEntityResource publish(@PathVariable("id") Long id, PersistentEntityResourceAssembler assembler)
            throws InvalidPetStateTransitionException {
        return assembler.toFullResource(petService.publish(id));
    }

    @RequestMapping(value = "/pets/{id}/publishing", method = RequestMethod.HEAD)
    @ResponseBody
    public void publishHead(@PathVariable("id") Long id) {
        Pet pet = petService.findOne(id);
        if (pet == null || pet.getStatus() != Pet.Status.NEW) {
            throw new ResourceNotFoundException();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/pets/{id}/expiration", method = RequestMethod.PUT, produces = "application/hal+json")
    public PersistentEntityResource expire(@PathVariable("id") Long id, PersistentEntityResourceAssembler assembler)
            throws InvalidPetStateTransitionException {
        return assembler.toFullResource(petService.expire(id));
    }

    @RequestMapping(value = "/pets/{id}/expiration", method = RequestMethod.HEAD)
    @ResponseBody
    public void expirationHead(@PathVariable("id") Long id) {
        Pet pet = petService.findOne(id);
        if (pet == null || pet.getStatus() != Pet.Status.PUBLISHED) {
            throw new ResourceNotFoundException();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/pets/search/byNick", method = RequestMethod.GET)
    public PersistentEntityResource findPetByNick(@RequestParam("nick") String nick, PersistentEntityResourceAssembler assembler)
            throws InvalidPetStateTransitionException {
        return assembler.toFullResource(petService.findByNick(nick));
    }

}
