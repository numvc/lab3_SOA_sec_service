package lab2.soa.newserv.dto;


import lab2.soa.newserv.entities.HumanBeing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HumanBeingListDto extends Response {
    private List<HumanBeing> humanBeingList;
    private int pageSize;
    private int pageNumber;
}
