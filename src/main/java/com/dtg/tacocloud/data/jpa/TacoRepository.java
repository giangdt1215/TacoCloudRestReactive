package com.dtg.tacocloud.data.jpa;


import com.dtg.tacocloud.model.Taco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {

}
