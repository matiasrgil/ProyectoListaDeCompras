package matias.proyect.Controllers;

import jakarta.validation.Valid;
import matias.proyect.Entity.Dto.ShoppingListDto;
import matias.proyect.Service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shopList")
public class ShoppingListController {

    @Autowired
    ShoppingListService service;
    @PostMapping
    public ResponseEntity<ShoppingListDto> createShopList(@RequestBody @Valid ShoppingListDto shopList){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createShopList(shopList));
    }

}
