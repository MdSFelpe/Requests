import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;



public class Request {

    private LocalDateTime createdAt;

    private List<RequestItem> items = new ArrayList<>();

    private OrderStatus status;


    public void addItem(RequestItem item){
        items.add(item);
    }

    public Request() {
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }


    public OrderStatus getStatus() {
        return status;
    }

    public boolean setStatus(OrderStatus newStatus) {

        if (this.status == OrderStatus.CANCELLED) {
            System.out.println("Cannot change a CANCELLED order!");
            return false;
        }

        if (this.status == OrderStatus.PAID) {
            System.out.println("Cannot change a PAID order!");
            return false;
        }

        if (this.status == newStatus) {
            System.out.println("Order already has this status!");
            return false;
        }

        this.status = newStatus;
        return true;
    }

    public double calculateTotal(){

        double total = 0;

        for(RequestItem item: items){
            total += item.calculateTotal();
        }

        return total;
    }

    public List<RequestItem> getItems() {
        return items;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
