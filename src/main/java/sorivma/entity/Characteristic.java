package sorivma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Characteristic {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "characteristic_id")
    private Integer characteristicId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "value")
    private String value;
    @ManyToOne
    @JoinColumn(name = "info_id")
    private Description description;

    @Override
    public String toString() {
        return "Characteristic{" +
                "characteristicId=" + characteristicId +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", product=" + description +
                '}';
    }
}
