package matias.proyect.Services;

import matias.proyect.Entities.Dto.ShoppingListDto;
import matias.proyect.Entities.ShoppingList;
import matias.proyect.Exceptions.ErrorZeroAmount;
import matias.proyect.Exceptions.ErrorZeroPrice;
import matias.proyect.Exceptions.InvalidName;
import matias.proyect.Exceptions.NotFoundException;
import matias.proyect.Mappers.ShoppingListMapper;
import matias.proyect.Repositories.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ShoppingListService {

    private final ShoppingListRepository repository;

    @Autowired
    public ShoppingListService(ShoppingListRepository repository) {
        this.repository = repository;
    }

    //crear item lista de precios
    public ShoppingListDto createShopList(ShoppingListDto shopList) {
        ShoppingList entity = ShoppingListMapper.dtoToShoppingList(shopList);

        validateNameProduct(entity.getNameProduct());
        validateAmount(entity.getAmount());
        validateUnitPrice(entity.getUnitPrice());

        entity.setPricePerQuantity(calculatepricePerQuantity(shopList.getUnitPrice(), shopList.getAmount()));

        entity.setTotalPrice(calculateTotalPrice(shopList.getPricePerQuantity())
                .add(entity.getPricePerQuantity()));

        ShoppingList entitySaved = repository.save(entity);

        setTotalPriceToRepository(entitySaved.getTotalPrice());

        shopList = ShoppingListMapper.shoppingListToDto(entitySaved);
        return shopList;
    }

    //Llamar lista de precio
    public List<ShoppingListDto> getShopList() {
        return repository.findAll().stream()
                .map(ShoppingListMapper::shoppingListToDto)
                .collect(Collectors.toList());
    }

    //Llamar lista de precio por id
    public ShoppingListDto getProductOfShopListById(Long id) {
        ShoppingList shopList = repository.findById(id).orElseThrow(() -> new NotFoundException("No se ha encontrado el producto con id " + id));
        return ShoppingListMapper.shoppingListToDto(shopList);
    }

    //Modificar item lista de precio
    public ShoppingListDto updateProductShopList(Long id, ShoppingListDto shopList) {

        validateAmount(shopList.getAmount());
        validateUnitPrice(shopList.getUnitPrice());

        if (repository.existsById(id)) {
            ShoppingList productToModify = repository.findById(id).get();

            BigDecimal oldPricePerQuantity = productToModify.getPricePerQuantity();

            if (!(Objects.isNull(shopList.getNameProduct()))) {
                validateNameProduct(shopList.getNameProduct());
                productToModify.setNameProduct(shopList.getNameProduct());
            }

            if (!(Objects.isNull(shopList.getAmount()))) {
                productToModify.setPricePerQuantity(calculatepricePerQuantity(productToModify.getUnitPrice(), shopList.getAmount()));
                productToModify.setAmount(shopList.getAmount());
            }

            if (!(Objects.isNull(shopList.getUnitPrice()))) {
                productToModify.setPricePerQuantity(calculatepricePerQuantity(shopList.getUnitPrice(), productToModify.getAmount()));
                productToModify.setUnitPrice(shopList.getUnitPrice());
            }

            if ((!(Objects.isNull(shopList.getAmount()))) || (!(Objects.isNull(shopList.getUnitPrice())))) {

                productToModify.setTotalPrice(
                        productToModify.getTotalPrice().subtract(oldPricePerQuantity)
                                .add(productToModify.getPricePerQuantity())
                );
            }

            ShoppingList productModify = repository.save(productToModify);

            setTotalPriceToRepository(productModify.getTotalPrice());

            return ShoppingListMapper.shoppingListToDto(productModify);
        } else {
            throw new NotFoundException("El producto con id: " + id + " no ha sido encontrado");
        }
    }

    public String deleteProductShopList(Long id) {

        ShoppingList shoppingList = repository.findById(id).orElseThrow(() -> new NotFoundException("El producto con id: " + id + " no ha sido encontrado"));

        System.out.println("nombre del producto: " + shoppingList.getNameProduct());
        System.out.println("precio del producto: " + shoppingList.getUnitPrice());
        System.out.println("cantidad del producto: " + shoppingList.getAmount());
        System.out.println("precio por cantidad del producto: " + shoppingList.getPricePerQuantity());
        System.out.println("precio total del carrito antes: " + shoppingList.getTotalPrice());

        BigDecimal returnOldTotalPrice = shoppingList.getTotalPrice().subtract(shoppingList.getPricePerQuantity());
        shoppingList.setTotalPrice(returnOldTotalPrice);

        System.out.println("precio total del carrito despues: " + shoppingList.getTotalPrice());
        setTotalPriceToRepository(shoppingList.getTotalPrice());

        repository.save(shoppingList);

        String message = "El producto '" + shoppingList.getNameProduct() + "' ha sido eliminado de la lista";

        repository.deleteById(shoppingList.getId());

        return message;

        //debo realizar la suma del total del carrito nuevamente

    }

    //Metodos auxiliares
    public static BigDecimal calculatepricePerQuantity(BigDecimal price, Integer amount) {
        return price.multiply(new BigDecimal(amount));
    }

    public BigDecimal calculateTotalPrice(BigDecimal pricePerQuantity) {
        BigDecimal totalPrice = new BigDecimal(0);

        List<ShoppingListDto> shopList = repository.findAll().stream()
                .map(ShoppingListMapper::shoppingListToDto)
                .collect(Collectors.toList());

        for (ShoppingListDto productOfShopList : shopList) {
            BigDecimal currentPrice = productOfShopList.getPricePerQuantity();


            if (currentPrice != null) {
                totalPrice = currentPrice.add(totalPrice);
            }
        }

        if (pricePerQuantity != null) {
            totalPrice = totalPrice.add(pricePerQuantity);
        }

        return totalPrice;
    }

    public void setTotalPriceToRepository(BigDecimal totalPrice) {

        List<ShoppingList> shopList = repository.findAll();

        for (ShoppingList productOfShopList : shopList) {
            productOfShopList.setTotalPrice(totalPrice);
        }
        repository.saveAll(shopList);
    }

    public void validateNameProduct(String name){
        if (Objects.isNull(name) || name.trim().isEmpty()) {
            throw new InvalidName("El nombre del producto no debe estar vacío");
        }
    }

    public void validateAmount(Integer amount) {
        if (!(Objects.isNull(amount)) && (Objects.equals(amount, 0))) {
                throw new ErrorZeroAmount("No se pueden agregar productos con monto 0");
        }
    }

    public void validateUnitPrice(BigDecimal unitPrice) {
        if (!(Objects.isNull(unitPrice)) && unitPrice.equals(BigDecimal.ZERO)) {
            throw new ErrorZeroPrice("No se pueden agregar productos con precio 0");
        }
    }
}
