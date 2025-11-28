package com.chtrembl.petstore.pet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.chtrembl.petstore.pet.model.Pet;
import com.chtrembl.petstore.pet.model.Status;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByStatusIn(List<Status> statuses);
}
