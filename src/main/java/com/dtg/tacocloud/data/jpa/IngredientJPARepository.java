package com.dtg.tacocloud.data.jpa;

import org.springframework.data.repository.CrudRepository;

import com.dtg.tacocloud.model.Ingredient;

public interface IngredientJPARepository extends CrudRepository<Ingredient, String>{

}
