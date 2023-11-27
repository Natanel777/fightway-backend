package spring.natanel.fightwaybackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER) // get the roles immediately with the other props
    @JoinTable(name = "customer_roles",

            joinColumns = @JoinColumn(         //the current class(the first class)
                    name = "customer_id",
                    referencedColumnName = "id"
            ),

            inverseJoinColumns = @JoinColumn(  //the foreign class(the second class)
                    name = "role_id",
                    referencedColumnName = "id")
    )
    private Set<Role> roles;

    //NULL in the beginning it will change after the first order of the client

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Order> orderHistory;

    @OneToOne
    private Cart cart;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private Address address;
}
