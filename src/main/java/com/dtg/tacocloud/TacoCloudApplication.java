package com.dtg.tacocloud;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dtg.tacocloud.data.jpa.IngredientJPARepository;
import com.dtg.tacocloud.data.jpa.TacoRepository;
import com.dtg.tacocloud.data.jpa.UserRepository;
import com.dtg.tacocloud.model.Ingredient;
import com.dtg.tacocloud.model.Taco;
import com.dtg.tacocloud.model.Ingredient.Type;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(IngredientJPARepository repo, UserRepository userRepo, PasswordEncoder encoder,
			TacoRepository tacoRepo) {
		return args -> {
			// Create Ingredients
			Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
			Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
			Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
			Ingredient carnitas = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
			Ingredient tomatoes = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
			Ingredient lettuce = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
			Ingredient cheddar = new Ingredient("CHED", "Cheddar", Type.CHEESE);
			Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
			Ingredient salsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
			Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);
			repo.save(flourTortilla);
			repo.save(cornTortilla);
			repo.save(groundBeef);
			repo.save(carnitas);
			repo.save(tomatoes);
			repo.save(lettuce);
			repo.save(cheddar);
			repo.save(jack);
			repo.save(salsa);
			repo.save(sourCream);

			Taco taco1 = new Taco();
			taco1.setName("Carnivore");
			taco1.setIngredients(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
			tacoRepo.save(taco1);
			
			Taco taco2 = new Taco();
			taco2.setName("Bovine Bounty");
			taco2.setIngredients(Arrays.asList(cornTortilla, groundBeef, cheddar, jack, sourCream));
			tacoRepo.save(taco2);
			
			Taco taco3 = new Taco();
			taco3.setName("Veg-out");
			taco3.setIngredients(Arrays.asList(flourTortilla, cornTortilla, tomatoes, lettuce, salsa));
			tacoRepo.save(taco3);
		};
	}
}
