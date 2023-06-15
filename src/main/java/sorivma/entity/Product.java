package sorivma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @GeneratedValue(generator="product_product_id_seq")
    @SequenceGenerator(name="product_product_id_seq"
            ,sequenceName="product_product_id_seq", allocationSize=1)
    @Id
    @Column(name = "product_id")
    private Integer productId;
    @Basic
    @Column(name = "storaged")
    private Long storaged;
    @Basic
    @Column(name = "garanty_dur")
    private Integer garantyDur;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "price")
    private BigInteger price;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private Description description;
    @ManyToOne
    @JoinColumn(name = "manufactuer_id")
    private Manufactuer manufactuer;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Report> reportList;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_sale",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "sale_id")}
    )
    private Set<Sale> sales;


    public Product() {
        this.storaged = 0L;
        this.garantyDur = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(storaged, product.storaged) && Objects.equals(garantyDur, product.garantyDur) && Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, storaged, garantyDur, name, price);
    }
}
