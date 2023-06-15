package sorivma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Manufactuer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "manufactuer_id")
    private Integer manufactuerId;
    @Basic
    @Column(name = "country")
    private String country;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "manufactuer", fetch = FetchType.EAGER)
    List<Product> productList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufactuer that = (Manufactuer) o;
        return Objects.equals(manufactuerId, that.manufactuerId) && Objects.equals(country, that.country) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufactuerId, country, name);
    }
}
