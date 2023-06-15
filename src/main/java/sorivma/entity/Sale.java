package sorivma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sale {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sale_id")
    private Integer saleId;
    @Basic
    @Column(name = "sale_percentage")
    private Double salePercentage;
    @Basic
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "sales")
    private Set<Product> productSet;
}
