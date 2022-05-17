package dev.jacob.a2_draft.repository;

import dev.jacob.a2_draft.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}
