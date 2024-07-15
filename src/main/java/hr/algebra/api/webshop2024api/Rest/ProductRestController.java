package hr.algebra.api.webshop2024api.Rest;

import hr.algebra.api.webshop2024api.ApiDTO.DTOProduct;
import hr.algebra.api.webshop2024api.ApiMapper.ProductMapper;
import hr.algebra.api.webshop2024api.ApiMapper.SubcategoryMapper;
import hr.algebra.bl.webshop2024bl.Service.ProductService;
import hr.algebra.dal.webshop2024dal.Entity.Image;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import javax.validation.Valid;

import hr.algebra.utils.CustomExceptions.ImageProcessingException;
import hr.algebra.utils.ImageConverter.ImageConverter;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/webShopApi")
public class ProductRestController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SubcategoryMapper subcategoryMapper;
    private final ImageConverter imageConverter;

    public ProductRestController(ProductService productService, ProductMapper productMapper, SubcategoryMapper subcategoryMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.subcategoryMapper = subcategoryMapper;
        imageConverter = new ImageConverter(320,320,"png");

    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/products/allProducts")
    public CompletableFuture<List<DTOProduct>> findAllProducts(){
        return CompletableFuture.completedFuture(
                productService.findAll()
                        .stream()
                        .map(productMapper::ProductToDTOProduct)
                        .collect(Collectors.toList())
        );
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/products/{productId}")
    public CompletableFuture<DTOProduct> findProductById(@PathVariable int productId) {
        Product product = productService.findById(productId);
        if (product == null) {
            throw new CustomNotFoundException("Product not found for id: " + productId);
        }
        return CompletableFuture.completedFuture(productMapper.ProductToDTOProduct(product));
    }

    @Async
    @ResponseBody
    @PostMapping("/products")
    public CompletableFuture<DTOProduct> createProduct(@Valid @RequestBody DTOProduct dtoProduct) {
        String base64img;
        try {
            base64img = imageConverter.convertToBase64(dtoProduct.getImageUrl());
        } catch (IOException e) {
            throw new ImageProcessingException(e.getMessage());
        }
        Product newProduct = productMapper.DTOProductToProduct(dtoProduct);
        newProduct.setImage(new Image(base64img));
        Product savedProduct = productService.save(newProduct);

        return CompletableFuture.supplyAsync(() -> productMapper.ProductToDTOProduct(savedProduct))
                .thenApplyAsync(savedDtoProduct -> savedDtoProduct);
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/products/{productId}")
    public CompletableFuture<DTOProduct> updateProduct(@PathVariable int productId, @Valid @RequestBody DTOProduct product){
        Product extistingProduct = productService.findById(productId);

        if (extistingProduct == null) {
            throw new CustomNotFoundException("Product not found for id: " + productId);
        }

        extistingProduct.setName(product.getName());
        extistingProduct.setDescription(product.getDescription());
        extistingProduct.setPrice(product.getPrice());
        extistingProduct.setSubcategory(subcategoryMapper.DTOSubcategoryToSubcategory(product.getSubcategory()));

        Product updatedProduct = productService.save(extistingProduct);

        return CompletableFuture.completedFuture(productMapper.ProductToDTOProduct(updatedProduct));
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/products/{productId}")
    public CompletableFuture<String> deleteProduct(@PathVariable int productId){
        Product productToDelete = productService.findById(productId);
        if (productToDelete == null){
            throw new CustomNotFoundException(("Product id not found - " + productId));
        }
        productService.deleteById(productId);

        return CompletableFuture.completedFuture("Deleted product with ID: " + productId);
    }
}
