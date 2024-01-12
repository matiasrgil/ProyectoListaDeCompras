package matias.proyect.Entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDto {

    private Long id;
    private String nameProduct;
    private Integer amount;
    private BigDecimal unitPrice;
    private BigDecimal pricePerQuantity;
    private BigDecimal totalPrice;

}
