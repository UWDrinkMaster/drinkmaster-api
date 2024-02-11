package ca.uwaterloo.drinkmasterapi.feature.order.event;
import org.springframework.context.ApplicationEvent;

public class OrderCompletionEvent extends ApplicationEvent {
    private final Long orderId;
    private final boolean isCompleted;

    public OrderCompletionEvent(Object source, Long orderId, boolean isCompleted) {
        super(source);
        this.orderId = orderId;
        this.isCompleted = isCompleted;
    }

    public Long getOrderId() {
        return orderId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
