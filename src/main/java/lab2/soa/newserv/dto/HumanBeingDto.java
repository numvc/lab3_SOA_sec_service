package lab2.soa.newserv.dto;

import lab2.soa.newserv.entities.HumanBeing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HumanBeingDto extends Response {
    private HumanBeing humanBeing;
}
