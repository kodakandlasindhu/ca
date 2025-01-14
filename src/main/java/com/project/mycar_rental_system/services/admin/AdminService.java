package com.project.mycar_rental_system.services.admin;

import com.project.mycar_rental_system.dto.BookACarDto;
import com.project.mycar_rental_system.dto.CarDto;
import com.project.mycar_rental_system.dto.CarDtoListDto;
import com.project.mycar_rental_system.dto.SearchCarDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    boolean postCar(CarDto carDto) throws IOException;

    List<CarDto> getAllCars();

    void deleteCar(Long id);

    CarDto getCarById(Long id);

    boolean updateCar(Long carId, CarDto carDto) throws IOException;

    List<BookACarDto> getBookings();

    boolean changeBookingStatus(Long bookingId,String status);

    CarDtoListDto searchCar(SearchCarDto searchCarDto);
}

