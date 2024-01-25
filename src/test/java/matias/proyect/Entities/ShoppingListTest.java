package matias.proyect.Entities;

import matias.proyect.Repositories.ShoppingListRepository;
import matias.proyect.Services.ShoppingListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingListTest {

    @Autowired
    ShoppingListService service;
    @Autowired
    ShoppingListRepository repository;

    @Test
    public void testCrearShoppingList() {
        ShoppingList shopList = new ShoppingList();
        assertNotNull(shopList);
    }

    @Test
    public void testCrearShoppingListWithAtributes() {
        ShoppingList shopList = new ShoppingList();

        assertNotNull(shopList);
        assertNull(shopList.getId());
        assertNull(shopList.getNameProduct());
        assertNull(shopList.getAmount());
        assertNull(shopList.getUnitPrice());
        assertNull(shopList.getPricePerQuantity());
        assertNull(shopList.getTotalPrice());
    }

    @Test
    public void testSetterGetterShoppingList() {
        ShoppingList shopList = new ShoppingList();

        shopList.setId(1L);
        shopList.setNameProduct("Mayonesa");
        shopList.setAmount(1);
        shopList.setUnitPrice(BigDecimal.valueOf(1000));

        assertEquals(1L, shopList.getId());
        assertEquals("Mayonesa", shopList.getNameProduct());
        assertEquals(1, shopList.getAmount());
        assertEquals(BigDecimal.valueOf(1000), shopList.getUnitPrice());
    }

    @Test
    public void testCalculatePricePerQuantity() {
        ShoppingList shoppingList = new ShoppingList();

        shoppingList.setPricePerQuantity(service.calculatepricePerQuantity(BigDecimal.valueOf(1000),2));
        assertEquals(BigDecimal.valueOf(2000), shoppingList.getPricePerQuantity());
    }

    //NO SE COMO HACER EL TEST DE CALCULAR TOTALPRICE, SIENDO QUE TENGO QUE IR AL REPOSITORIO Y LAS SOLUCIONES QUE ENCONTRE FUERON CON MOCKITO

/*
    @Test
    public void testCalculateTotalPrice() {
        ShoppingList shoppingList = new ShoppingList();

        BigDecimal pricePerQuantity = service.calculatepricePerQuantity(BigDecimal.valueOf(1000),2);
        shoppingList.setPricePerQuantity(pricePerQuantity);
        shoppingList.setTotalPrice(service.calculateTotalPrice(pricePerQuantity));

        assertEquals(BigDecimal.valueOf(2000), shoppingList.getTotalPrice());
    }
*/
    /*
@Test
public void testCalculateTotalPrice() {
    // Definir la clase DummyShoppingListRepository localmente
    class DummyShoppingListRepository implements ShoppingListRepository {
        @Override
        public List<ShoppingList> findAll() {
            // Devolver una lista vacía o con objetos ShoppingList según sea necesario para la prueba
            return Collections.emptyList();
        }

        // Implementar otros métodos del repositorio según sea necesario para las pruebas
    }

    // Crear una instancia de DummyShoppingListRepository
    DummyShoppingListRepository dummyRepository = new DummyShoppingListRepository();

    // Crear un servicio utilizando el DummyShoppingListRepository
    ShoppingListService service = new ShoppingListService(dummyRepository);

    // Crear un objeto ShoppingList para la prueba
    ShoppingList shoppingList = new ShoppingList();
    shoppingList.setUnitPrice(BigDecimal.valueOf(1000));
    shoppingList.setAmount(2);

    // Llamar al método calculateTotalPrice y verificar el resultado
    BigDecimal pricePerQuantity = service.calculatepricePerQuantity(shoppingList.getUnitPrice(), shoppingList.getAmount());
    shoppingList.setPricePerQuantity(pricePerQuantity);
    BigDecimal totalPrice = service.calculateTotalPrice(pricePerQuantity);

    assertEquals(BigDecimal.valueOf(2000), totalPrice);
}
*/


}
