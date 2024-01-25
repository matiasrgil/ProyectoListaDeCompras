package matias.proyect.Controllers;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ShoppingListControllerTest {

    @Test
    public void testCrearShoppingListController(){
        ShoppingListController shopListCont = new ShoppingListController();
        assertNotNull(shopListCont);
    }

}
