package spring.natanel.fightwaybackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "products" ,uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Product {

    @Id
    @GeneratedValue //(strategy = GenerationType.IDENTITY)//increased by one any time
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal price; // just like double or float without rounding issues

    @NotNull
    private String type;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER) // get the roles immediately with the other props
    @JoinTable(name = "products_category",

            joinColumns = @JoinColumn(         //the current class(the first class)
                    name = "product_id",
                    referencedColumnName = "id"
            ),

            inverseJoinColumns = @JoinColumn(  //the foreign class(the second class)
                    name = "category_id",
                    referencedColumnName = "id")
    )
    private Category category;

    private String imageUrl;
}
