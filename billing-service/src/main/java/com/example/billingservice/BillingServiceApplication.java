package com.example.billingservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
class Bill{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date billingDate;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long subscriberID;
    @Transient
    private Subscriber subscriber;
    @OneToMany(mappedBy = "bill")
    private Collection<MovieItem> movieItems;

}
@RepositoryRestResource
interface BillRepository extends JpaRepository<Bill,Long>{}
@Projection(name="fullBill",types=Bill.class)
interface BillProjection{
    public Long getId();
    public Date getBillingDate();
    public Long getSubscriberID();
    public Collection<MovieItem> getMovieItems();
}
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
class MovieItem{
    @Id @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long movieID;
    @Transient
    private Movie movie;
    private double price;
    private int quantity;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Bill bill;
}
@RepositoryRestResource
interface MovieItemRepository extends JpaRepository<MovieItem,Long>{}
@Data
class Subscriber{
    private Long id; private String email; private String password; private String name,surname,address; private java.sql.Date birth;
}
@FeignClient(name="SUBSCRIBER-SERVICE")
interface SubscriberService{
    @GetMapping("/subscribers/{id}")
    public Subscriber findSubscriberById(@PathVariable(name="id") Long id);
}
@Data
class Movie{ private Long id; private String title,genre; private double rating; private double price;}
@FeignClient(name="CATALOGUE-SERVICE")
interface CatalogueService{
    @GetMapping("/movies/{id}")
    public Movie findMovieById(@PathVariable("id") Long id);
    @GetMapping("/movies")
    public PagedModel<Movie> findAllMovies();

}
@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            MovieItemRepository movieItemRepository,
                            SubscriberService subscriberService,
                            CatalogueService catalogueService)
    {
        return args -> {
            Subscriber c1=subscriberService.findSubscriberById(2L);
            System.out.println("*****************");
            System.out.println("ID= "+c1.getId());
            System.out.println("Name= "+c1.getName());
            System.out.println("Email= "+c1.getEmail());

            Bill bill1=billRepository.save(new Bill(null,new Date(),c1.getId(),null,null));

            PagedModel<Movie> movies = catalogueService.findAllMovies();
            movies.getContent().forEach(p->{
                movieItemRepository.save(new MovieItem(null,p.getId(),null,p.getPrice(),90,bill1));
            });


        };
    }
}
@RestController
class BillRestController{
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private MovieItemRepository movieItemRepository;
    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private CatalogueService catalogueService;

    @GetMapping("/fullBill/{id}")
    public Bill getBill(@PathVariable(name="id") Long id) {
        Bill bill = billRepository.findById(id).get();
        bill.setSubscriber(subscriberService.findSubscriberById(bill.getSubscriberID()));
        bill.getMovieItems().forEach(pi ->{
            pi.setMovie(catalogueService.findMovieById(pi.getMovieID()));
        });
        return bill;
    }
}