package model;

import lombok.*;

import java.text.spi.DateFormatProvider;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Data

public class Order {
    private String OrderId;
    private LocalDate OrderDate;
    private String CustId;
}
