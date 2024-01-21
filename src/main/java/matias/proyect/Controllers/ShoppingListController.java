package matias.proyect.Controllers;

import jakarta.validation.Valid;
import lombok.Getter;
import matias.proyect.Entity.Dto.ShoppingListDto;
import matias.proyect.Service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RecursiveTask;

@RestController
@RequestMapping("/api/shopList")
public class ShoppingListController {

    @Autowired
    ShoppingListService service;
    @PostMapping
    public ResponseEntity<ShoppingListDto> createShopList(@RequestBody @Valid ShoppingListDto shopList){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createShopList(shopList));
    }

    @GetMapping
    public ResponseEntity<List<ShoppingListDto>> getShopList(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getShopList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ShoppingListDto> getProductOfShopListById(@PathVariable @Valid Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getProductOfShopListById(id));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ShoppingListDto> updateProductShopList(@PathVariable @Valid Long id, @RequestBody ShoppingListDto shopList){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateProductShopList(id, shopList));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProductShopList(@PathVariable @Valid Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteProductShopList(id));
    }

}
