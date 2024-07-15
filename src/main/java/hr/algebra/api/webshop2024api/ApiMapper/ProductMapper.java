package hr.algebra.api.webshop2024api.ApiMapper;

import hr.algebra.api.webshop2024api.ApiDTO.DTOProduct;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "subcategory", source = "subcategory")
    @Mapping(target = "b64image", source = "image.b64image")
    DTOProduct ProductToDTOProduct(Product source);

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "subcategory", source = "subcategory")
    @Mapping(target = "image", ignore = true)
    Product DTOProductToProduct(DTOProduct destination);

}
