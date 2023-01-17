package com.oumayma.catalogue;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title,genre;
	private double rating;
	private double price;

}

@RepositoryRestResource
interface MovieRepository extends JpaRepository<Movie,Long>{

}
@Projection(name="fullMovie",types=Movie.class)
interface MovieInterface
{
	public Long getId();
	public String getTitle();
	public String getGenre();
	public double getRating();
	public double getPrice();
}
@SpringBootApplication
public class CatalogueServiceApplication {
	@Autowired
	MovieRepository movieRepository;

	public static void main(String[] args) {
		SpringApplication.run(CatalogueServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(MovieRepository movieRepository,
							RepositoryRestConfiguration restConfiguration) {


		return args -> {
			restConfiguration.exposeIdsFor(Movie.class);

			movieRepository.save(new Movie( null,"Titanic","Romance",9.9,15.500));
			movieRepository.save(new Movie( null,"Titanic2","Romance",9.9,15.500));
			movieRepository.save(new Movie( null,"Titanic3","Romance",9.9,15.500));
			movieRepository.findAll().forEach(System.out::println);

		};
	}
}
