package dev.jacob.a2_draft.car;

import dev.jacob.a2_draft.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}
