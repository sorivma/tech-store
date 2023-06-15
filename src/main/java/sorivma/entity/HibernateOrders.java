package sorivma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders", schema = "public", catalog = "tech-store")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HibernateOrders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id")
    private Integer orderId;
    @Basic
    @Column(name = "date")
    private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    HibernateCustomer customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    List<HibernateCheckLine> hibernateCheckLineList;
}
