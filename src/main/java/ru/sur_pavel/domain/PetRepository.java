package ru.sur_pavel.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {
    @RestResource(rel = "published", path = "published")
    @Query("select pet from Pet pet where pet.status = 'PUBLISHED'")
    Page<Pet> findPublished(Pageable pageable);

    List<Pet> findAllByNick(String nick);
//    List<Pet> findAllByClient(Client client);

}
