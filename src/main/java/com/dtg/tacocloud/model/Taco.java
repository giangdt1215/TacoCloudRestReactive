package com.dtg.tacocloud.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.rest.core.annotation.RestResource;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@Table (Spring data jdbc)
@Entity
@EqualsAndHashCode(exclude = "createdAt")
@RestResource(rel = "tacos", path = "tacos")
public class Taco implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date createdAt;

    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;

// Spring jdbc - Spring data jdbc
//    @NotNull(message = "You must choose at least 1 ingredient ")
//    @Size(min=1, message="You must choose at least 1 ingredient")
//    private List<IngredientRef> ingredients = new ArrayList<>();
//    
//    public void addIngredient(Ingredient ingredient) {
//    	this.ingredients.add(new IngredientRef(ingredient.getId()));
//    }
    
    @NotNull(message = "You must choose at least 1 ingredient ")
    @Size(min=1, message="You must choose at least 1 ingredient")
    @ManyToMany(targetEntity = Ingredient.class)
    private List<Ingredient> ingredients = new ArrayList<>();
    
    public void addIngredient(Ingredient ingredient) {
    	this.ingredients.add(ingredient);
    }
    
    @PrePersist
    void createdAt() {
    	this.createdAt = new Date();
    }
}
