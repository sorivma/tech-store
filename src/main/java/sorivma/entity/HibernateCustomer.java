package sorivma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customer", schema = "public", catalog = "tech-store")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HibernateCustomer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "customer_id")
    private Integer customerId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "middle_name")
    private String middleName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "addresss")
    private String addresss;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<HibernateOrders> orders;
    @OneToMany(mappedBy = "hibernateCustomer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Report> reports;
}
