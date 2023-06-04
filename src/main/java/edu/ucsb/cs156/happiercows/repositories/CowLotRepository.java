package edu.ucsb.cs156.happiercows.repositories;

import edu.ucsb.cs156.happiercows.entities.CowLot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CowLotRepository extends CrudRepository<CowLot, Long> {
    Iterable<CowLot> findAllByUserCommonsId(Long userCommonsId);
    Optional<CowLot> findByUserCommonsIdAndHealth(Long userCommonsId, Double Health);
}