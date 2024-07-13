package hr.algebra.dal.webshop2024dal.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_image")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    public ProductImage(Product product, Image image) {
        this.product = product;
        this.image = image;
    }
}
