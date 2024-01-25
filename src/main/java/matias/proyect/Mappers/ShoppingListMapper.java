package matias.proyect.Mappers;

import matias.proyect.Entities.Dto.ShoppingListDto;
import matias.proyect.Entities.ShoppingList;

public class ShoppingListMapper {

    public static ShoppingList dtoToShoppingList(ShoppingListDto dto) {
        ShoppingList entity = new ShoppingList();

        entity.setNameProduct(dto.getNameProduct());
        entity.setAmount(dto.getAmount());
        entity.setUnitPrice(dto.getUnitPrice());
        return entity;
    }

    public static ShoppingListDto shoppingListToDto(ShoppingList entity) {
        ShoppingListDto dto = new ShoppingListDto();

        dto.setId(entity.getId());
        dto.setNameProduct(entity.getNameProduct());
        dto.setAmount(entity.getAmount());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setPricePerQuantity(entity.getPricePerQuantity());
        dto.setTotalPrice(entity.getTotalPrice());
        return dto;
    }


}
