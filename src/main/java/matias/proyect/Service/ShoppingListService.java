package matias.proyect.Service;

import matias.proyect.Entity.Dto.ShoppingListDto;
import matias.proyect.Entity.ShoppingList;
import matias.proyect.Mapper.ShoppingListMapper;
import matias.proyect.Repositories.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ShoppingListService {
    @Autowired
    ShoppingListRepository repository;

    public ShoppingListDto createShopList(ShoppingListDto shopList){
        ShoppingList entity = ShoppingListMapper.dtoToShoppingList(shopList);
        entity.setPricePerQuantity(calculatepricePerQuantity(shopList.getUnitPrice(),shopList.getAmount()));
        ShoppingList entitySaved = repository.save(entity);
        shopList = ShoppingListMapper.ShoppingListToDto(entitySaved);
        return shopList;
    }

    public static BigDecimal calculatepricePerQuantity(BigDecimal price, Integer amount){
        return price.multiply(new BigDecimal(amount));
    }

}
