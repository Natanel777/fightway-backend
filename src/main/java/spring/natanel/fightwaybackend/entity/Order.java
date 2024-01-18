package spring.natanel.fightwaybackend.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    //LocalDate.now()
    @NotNull
    private LocalDate date;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private String paymentInformation;

    @NotNull
    private String address;

    @NotNull
    private String city;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String postalCode;

    @NotNull
    private String status;

}
