package spring.natanel.fightwaybackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import spring.natanel.fightwaybackend.entity.Customer;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String street;

    @NotNull
    private String city;

    @NotNull
    private String postalCode;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Customer customer;
}
