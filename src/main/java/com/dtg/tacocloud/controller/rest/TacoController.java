package com.dtg.tacocloud.controller.rest;

import com.dtg.tacocloud.data.jpa.TacoRepository;
import com.dtg.tacocloud.model.Taco;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Observable;

@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoController {

	private TacoRepository tacoRepo;
	
	public TacoController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}
	
	@GetMapping(params = "recent")
	public Flux<Taco> recentTacos(){
		return tacoRepo.findAll().take(12);
	}
	
	@GetMapping("/{id}")
	public Mono<Taco> tacoById(@PathVariable("id") Long id){
		return tacoRepo.findById(id);
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Taco> postTaco(@RequestBody Mono<Taco> tacoMono) {
		//saveAll return Flux, use next() to get Mono
		return tacoRepo.saveAll(tacoMono).next();
//		return tacoMono.flatMap(tacoRepo::save);
	}
}
