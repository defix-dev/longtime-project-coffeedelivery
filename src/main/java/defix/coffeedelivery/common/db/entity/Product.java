package defix.coffeedelivery.common.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 85)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "type", nullable = false, length = 85)
    private String type;

}