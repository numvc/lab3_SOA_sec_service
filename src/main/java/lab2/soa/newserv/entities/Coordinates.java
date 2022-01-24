package lab2.soa.newserv.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordinates")
public class Coordinates {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @JsonProperty("x")
    private int x;

    @JsonProperty("y")
    @DecimalMin(value = "-881", message = "Y cant be less than -881")
    private int y; //Значение поля должно быть больше -881
}

