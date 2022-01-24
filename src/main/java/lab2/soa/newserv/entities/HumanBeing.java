package lab2.soa.newserv.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "human_being")
public class HumanBeing {
    @Id
    @NotNull
    @JsonProperty("id")
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически


    @JsonProperty("name")
    @NotNull(message = "Name cant be null")
    @NotBlank(message = "Name cant be empty string")
    private String name; //Поле не может быть null, Строка не может быть пустой


    @JsonProperty("coordinates")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates; //Поле не может быть null


    @JsonProperty("creationDate")
    @Column(name = "creation_date", nullable = false)
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @JsonProperty("realHero")
    @Column(name = "real_hero")
    private boolean realHero;

    @JsonProperty("hasToothpick")
    @Column(name = "has_tooth_pick", nullable = false)
    private Boolean hasToothpick; //Поле может быть null

    @JsonProperty("impactSpeed")
    @NotNull(message = "Impact Speed cant be null")
    @DecimalMin(value = "-675", message = "Impact Speed cant be less than -675")
    @Column(name = "impact_speed", nullable = false)
    private Double impactSpeed; //Значение поля должно быть больше -675, Поле не может быть null

    @JsonProperty("soundtrackName")
    @Column(name = "soundtrack_name", nullable = false)
    private String soundtrackName; //Поле не может быть null

    @JsonProperty("minutesOfWaiting")
    @Column(name = "minutes_of_waiting")
    private double minutesOfWaiting;

    @JsonProperty("weaponType")
    @Column(name = "weapon_type", nullable = false)
    private WeaponType weaponType; //Поле может быть null


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car")
    private Car car; //Поле может быть null
}