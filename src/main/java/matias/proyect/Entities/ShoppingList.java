package matias.proyect.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "Lista_Compras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Nombre")
    private String nameProduct;
    @Column(name = "Cantidad")
    private Integer amount;
    @Column(name = "Precio_Unitario")
    private BigDecimal unitPrice;
    @Column(name = "Precio_Por_Cantidad")
    private BigDecimal pricePerQuantity;
    @Column(name = "Precio_Total")
    private BigDecimal totalPrice;

}
