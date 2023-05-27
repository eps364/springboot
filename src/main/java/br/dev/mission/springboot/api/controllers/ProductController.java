package br.dev.mission.springboot.api.controllers;

import br.dev.mission.springboot.api.dtos.ProductRequestDto;
import br.dev.mission.springboot.api.models.ProductModel;
import br.dev.mission.springboot.api.services.ProductService;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductRequestDto productRequestDto){
        if(productService.existsByName(productRequestDto.getName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Name is already in use!");
        }
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRequestDto,productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));
    }

    @GetMapping
    public ResponseEntity<Page<ProductModel>> getAllProduct(@PageableDefault(page = 0, size = 10, sort = "id",direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> productModelOptional = productService.findById(id);
        return productModelOptional.<ResponseEntity<Object>>map(productModel ->
                ResponseEntity.status(HttpStatus.OK).body(productModel)).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> productModelOptional = productService.findById(id);
        if(!productModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productService.delete(productModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductRequestDto productRequestDto) {
        Optional<ProductModel> productModelOptional = productService.findById(id);
        if (!productModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        var productModel = productModelOptional.get();
        productModel.setName(productRequestDto.getName());
        productModel.setBrand(productRequestDto.getBrand());

        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
    }

}
