package edu.ucsb.cs156.happiercows.repositories;

import edu.ucsb.cs156.happiercows.entities.Profit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
@Repository
public interface ProfitRepository extends CrudRepository<Profit, Long> {
    Iterable<Profit> findAllByUserCommonsId(Long user_commons_id);

    @Modifying
    @Transactional
    Iterable<Profit> deleteAllByUserCommonsId(Long user_commons_id);
}