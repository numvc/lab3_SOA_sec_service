package lab2.soa.newserv.service;

import lab2.soa.newserv.entities.Car;
import lab2.soa.newserv.entities.Coordinates;
import lab2.soa.newserv.entities.HumanBeing;
import lab2.soa.newserv.entities.WeaponType;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class HumanBeingService {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @SneakyThrows
    public HumanBeing mapHumanBeing(HttpServletRequest request) {
        HumanBeing newHumanBaing = new HumanBeing();

        System.out.println(request.getParameterMap().toString());

        Car newCar = new Car();
        Coordinates newCoord = new Coordinates();

        newCoord.setX(Integer.parseInt(request.getParameter("x")));
        newCoord.setY(Integer.parseInt(request.getParameter("y")));
        newCar.setName(request.getParameter("car"));

        newHumanBaing.setCar(newCar);
        newHumanBaing.setCoordinates(newCoord);
        newHumanBaing.setName(request.getParameter("name"));
        newHumanBaing.setHasToothpick(Boolean.valueOf(request.getParameter("hasToothpick")));
        newHumanBaing.setImpactSpeed(Double.valueOf(request.getParameter("impactSpeed")));
        newHumanBaing.setMinutesOfWaiting(Double.parseDouble(request.getParameter("minutesOfWaiting")));
        newHumanBaing.setRealHero(Boolean.parseBoolean(request.getParameter("realHero")));
        newHumanBaing.setSoundtrackName(request.getParameter("soundtrackName"));
        newHumanBaing.setWeaponType(WeaponType.valueOf(request.getParameter("weaponType")));

        return newHumanBaing;
    }

    public String validateHumanBeing(HumanBeing humanBeing) {
        Set<ConstraintViolation<HumanBeing>> constraintViolations =
                validator.validate(humanBeing);
        StringBuilder message = new StringBuilder();

        if (!constraintViolations.isEmpty())
            for (ConstraintViolation s : constraintViolations)
                message.append(s.getMessage()).append(", ");


        Set<ConstraintViolation<Coordinates>> constraintViolationsCoordinates =
                validator.validate(humanBeing.getCoordinates());
        if (!constraintViolationsCoordinates.isEmpty())
            for (ConstraintViolation s : constraintViolationsCoordinates)
                message.append(s.getMessage()).append(", ");


        Set<ConstraintViolation<Car>> constraintViolationsCar =
                validator.validate(humanBeing.getCar());
        if (!constraintViolationsCar.isEmpty())
            for (ConstraintViolation s : constraintViolationsCar)
                message.append(s.getMessage()).append(", ");


        System.out.println(message);
        if (message.length() < 2)
            return "Done";

        return message.toString();
    }

}
