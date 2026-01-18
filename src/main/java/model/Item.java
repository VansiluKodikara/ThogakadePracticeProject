package model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Data

public class Item {
    private String ItemCode;
    private String Description;
    private String PackSize;
    private Double UnitPrice;
    private Integer QtyOnHand;

}
