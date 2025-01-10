package com.parking.parkinglot.ejb;

import com.parking.parkinglot.entities.Cars;
import com.parking.parkinglot.common.CarDto;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    // Aici avem useCaseurile

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");
        try {
            TypedQuery<Cars> typedQuery = entityManager.createQuery("SELECT c FROM Cars c", Cars.class);
            List<Cars> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDto(List<Cars> cars) {
        List<CarDto> carDtos = new ArrayList<>();
        for (Cars car : cars) {
            CarDto carDto = new CarDto(
                    car.getId(),
                    car.getLicencePlate(),
                    car.getParkingSpot(),
                    car.getOwner().getUsername());
            carDtos.add(carDto);
        }
        return carDtos;
    }
}