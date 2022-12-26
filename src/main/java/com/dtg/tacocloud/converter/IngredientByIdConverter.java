package com.dtg.tacocloud.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.dtg.tacocloud.data.jpa.IngredientJPARepository;
import com.dtg.tacocloud.model.Ingredient;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientJPARepository ingredientRepo;

    public IngredientByIdConverter(IngredientJPARepository ingredientRepo){
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(id).orElse(null);
    }
}
