package sorivma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "check_line", schema = "public", catalog = "tech-store")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HibernateCheckLine {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "check_id")
    private Integer checkId;
    @Basic
    @Column(name = "quantity")
    private Long quantity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private HibernateOrders order;
}
