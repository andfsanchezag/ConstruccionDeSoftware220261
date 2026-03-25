package app.domain.models.orders;

import app.domain.models.inventory.Medicine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderMedicine extends OrderDetail {

    private Medicine medicine;

    private String dose;

    private String duration;
}
