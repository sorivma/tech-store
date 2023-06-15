package sorivma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Report {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "report_id")
    private Integer reportId;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private HibernateCustomer hibernateCustomer;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "text")
    private String text;
    @Basic
    @Column(name = "rate")
    private String rate;
}
