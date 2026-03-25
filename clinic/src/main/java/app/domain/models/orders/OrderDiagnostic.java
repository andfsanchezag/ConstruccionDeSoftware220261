package app.domain.models.orders;

import app.domain.models.inventory.DiagnosticSupport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderDiagnostic extends OrderDetail {

    private DiagnosticSupport diagnosticSupport;

    private int quantity;
}
