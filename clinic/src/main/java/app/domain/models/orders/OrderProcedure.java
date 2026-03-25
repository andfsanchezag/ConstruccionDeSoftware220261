package app.domain.models.orders;

import app.domain.models.inventory.Procedure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderProcedure extends OrderDetail {

    private Procedure procedure;

    private int quantity;

    private String frequency;
}
