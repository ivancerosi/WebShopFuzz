package hr.algebra.api.webshop2024api.ApiDTO;

import javax.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOSubcategory {
    private Long subcategoryId;
    @NotEmpty(message = "Subcategory name is required!")
    private String name;
    private DTOCategory category;

    public DTOSubcategory(String name, DTOCategory category) {
        this.name = name;
        this.category = category;
    }

    public DTOSubcategory(Long subcategoryId, String name, DTOCategory category) {
        this.subcategoryId = subcategoryId;
        this.name = name;
        this.category = category;
    }
}
