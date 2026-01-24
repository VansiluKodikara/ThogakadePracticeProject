package TM;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString
public class OrderTM {
    private String OrderId;
    private LocalDate OrderDate;
    private String CustId;

    public OrderTM(String OrderId, LocalDate OrderDate, String CustId){
        this.OrderId=OrderId;
        this.OrderDate=OrderDate;
        this.CustId=CustId;
    }
}
