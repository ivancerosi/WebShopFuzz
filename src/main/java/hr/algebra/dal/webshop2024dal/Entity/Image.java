package hr.algebra.dal.webshop2024dal.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Lob
    @Column(name = "image_b64", nullable = false, columnDefinition = "text")
    private String b64image;

    public Image(String b64image) {
        this.b64image = b64image;
    }
}

