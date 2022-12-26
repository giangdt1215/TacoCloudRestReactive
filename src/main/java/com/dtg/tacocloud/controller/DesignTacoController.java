package com.dtg.tacocloud.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dtg.tacocloud.data.jpa.IngredientJPARepository;
import com.dtg.tacocloud.model.Ingredient;
import com.dtg.tacocloud.model.Ingredient.Type;
import com.dtg.tacocloud.model.Taco;
import com.dtg.tacocloud.model.TacoOrder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
	
//	private final IngredientRepository ingredientRepo;
//	
//	public DesignTacoController(IngredientRepository ingredientRepo) {
//		this.ingredientRepo = ingredientRepo;
//	}
	
	private final IngredientJPARepository ingredientRepo;
	
	public DesignTacoController(IngredientJPARepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}

    @ModelAttribute
    public void addIngredientsToModel(Model model){
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO","Flour Tortillar", Type.WRAP),
//                new Ingredient("COTO","Corn Tortillar", Type.WRAP),
//                new Ingredient("GRBF","Ground Beef", Type.PROTEIN),
//                new Ingredient("CARN","Carnitas", Type.PROTEIN),
//                new Ingredient("TMTO","Diced Tomatoes", Type.VEGGIES),
//                new Ingredient("LETC","Lettuce", Type.VEGGIES),
//                new Ingredient("CHED","Cheddar", Type.CHEESE),
//                new Ingredient("JACK","Monterrey Jack", Type.CHEESE),
//                new Ingredient("SLSA","Salsa", Type.SAUCE),
//                new Ingredient("SRCR","Sour Cream", Type.SAUCE)
//        );
    	Iterable<Ingredient> ingredients = ingredientRepo.findAll();

        Type[] types = Type.values();
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name="tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    private List<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                          .filter(x -> x.getType().equals(type))
                          .collect(Collectors.toList());
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder){
        if(errors.hasErrors()) {
            return "design";
        }

        log.info("Processing design: {}", taco);
        tacoOrder.addTaco(taco);
        return "redirect:/orders/current";
    }
}
