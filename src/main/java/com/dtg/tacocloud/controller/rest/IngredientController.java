package com.dtg.tacocloud.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.dtg.tacocloud.data.jpa.IngredientJPARepository;
import com.dtg.tacocloud.model.Ingredient;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class IngredientController {

	private IngredientJPARepository repo;
	
	public IngredientController(IngredientJPARepository ingredientRepo) {
		this.repo = ingredientRepo;
	}
	
	@GetMapping
	public Iterable<Ingredient> allIngredients(){
		return repo.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
		return repo.save(ingredient);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteIngredient(@PathVariable("id") String ingredientId){
		repo.deleteById(ingredientId);
	}
}
