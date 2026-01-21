package TM;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ItemTM {
    private String ItemCode;
    private String Description;
    private String PackSize;
    private Double UnitPrice;
    private Integer QtyOnHand;

    public ItemTM(String ItemCode, String Description, String PackSize, Double UnitPrice, Integer QtyOnHand){
        this.ItemCode=ItemCode;
        this.Description=Description;
        this.PackSize=PackSize;
        this.UnitPrice=UnitPrice;
        this.QtyOnHand=QtyOnHand;
    }
}
