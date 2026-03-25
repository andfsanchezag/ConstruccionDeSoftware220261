package app.domain.models.billing;

import app.domain.models.enums.ItemType;
import app.domain.models.orders.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InvoiceItem {

    private long id;

    private Order order;

    private ItemType itemType;

    private String itemName;

    private int quantity;

    private double unitPrice;

    private double totalPrice;
}
