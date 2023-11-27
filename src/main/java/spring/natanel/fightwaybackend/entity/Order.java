package spring.natanel.fightwaybackend.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
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

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    //LocalDateTime.now()
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date date;

    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    private String paymentInformation;

    @NotNull
    private String status;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem > items;

}
