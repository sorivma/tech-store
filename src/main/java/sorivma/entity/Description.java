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
@Getter
@Setter
public class Description {
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Basic
    @Column(name = "text")
    private String text;
    @Basic
    @Column(name = "image_url")
    private String imageUrl;
    @Basic
    @Column(name = "volume")
    private Double volume;
    @Basic
    @Column(name = "weight")
    private Double weight;
    @GeneratedValue(generator="description_description_id_seq")
    @SequenceGenerator(name="description_description_id_seq"
            ,sequenceName="description_description_id_seq", allocationSize=1)
    @Id
    @Column(name = "description_id")
    private Integer descriptionId;
    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER, mappedBy = "description", orphanRemoval = true)
    private List<Characteristic> characteristicList;

    public Description() {
        this.text = "Enter text";
        this.imageUrl = "https://yoomag.ru/image/cache/no_image-1500x1500.png";
        this.volume = 0.0;
        this.weight = 0.0;
    }
}
