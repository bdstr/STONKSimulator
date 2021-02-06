package net.kloczkowski.STONKSimulator.API.repository;

import net.kloczkowski.STONKSimulator.API.model.OpenPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenPositionRepository extends JpaRepository<OpenPosition, Long> {
}
