package ru.sur_pavel.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet publish(Long id) {
        return findDoAndSave(id, Pet::publish);
    }

    public Pet expire(Long id) {
        return findDoAndSave(id, Pet::expire);
    }

    private Pet findDoAndSave(Long id, Action action) {
        Pet foundPet = petRepository.findOne(id);
        Pet modifiedPet = action.perform(foundPet);
        return petRepository.save(modifiedPet);
    }

    public Pet findOne(Long id) {
        return petRepository.findOne(id);
    }

    @FunctionalInterface
    private interface Action {

        Pet perform(Pet pet);

    }

}
