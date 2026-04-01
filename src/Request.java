import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;



public class Request {

    private LocalDateTime createdAt;

    private List<RequestItem> items = new ArrayList<>();

    public void addItem(RequestItem item){
        items.add(item);
    }

    public Request() {
        this.createdAt = LocalDateTime.now();
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
