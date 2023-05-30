package edu.ucsb.cs156.happiercows.repositories;

import edu.ucsb.cs156.happiercows.entities.CowLot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CowLotRepository extends CrudRepository<CowLot, Long> {
    Iterable<CowLot> findAllByCommonsIdAndUserId(Long commons_id, long user_id);
}