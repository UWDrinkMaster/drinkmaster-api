package ca.uwaterloo.drinkmasterapi.feature.order.event;

import ca.uwaterloo.drinkmasterapi.feature.order.dto.PourItemDTO;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.List;

public class PourDrinkEvent extends ApplicationEvent {
    private final Long orderId;
    private final Long machineId;
    private final Long transId;
    private final LocalDateTime orderTime;
    private final List<PourItemDTO> pourItems;

    public PourDrinkEvent(Object source, Long orderId, Long machineId, Long transId, LocalDateTime orderTime, List<PourItemDTO> pourItems) {
        super(source);
        this.orderId = orderId;
        this.machineId = machineId;
        this.transId = transId;
        this.orderTime = orderTime;
        this.pourItems = pourItems;
    }

    // Getters
    public Long getOrderId() {
        return orderId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public Long getTransId() {
        return transId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public List<PourItemDTO> getPourItems() {
        return pourItems;
    }
}
