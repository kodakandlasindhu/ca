package com.project.mycar_rental_system.services.customer;

import com.project.mycar_rental_system.dto.BookACarDto;
import com.project.mycar_rental_system.dto.CarDto;
import com.project.mycar_rental_system.entity.BookACar;
import com.project.mycar_rental_system.entity.Car;
import com.project.mycar_rental_system.entity.User;
import com.project.mycar_rental_system.enums.BookCarStatus;
import com.project.mycar_rental_system.repository.BookACarRepository;
import com.project.mycar_rental_system.repository.CarRepository;
import com.project.mycar_rental_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    private final BookACarRepository bookACarRepository;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public boolean bookACar(BookACarDto bookACarDto) {
        Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCarId());
        Optional<User> optionalUser= userRepository.findById(bookACarDto.getUserId());
        if(optionalCar.isPresent() && optionalUser.isPresent()){
            Car existingCar = optionalCar.get();
            BookACar bookACar=new BookACar();
            bookACar.setUser(optionalUser.get());
            bookACar.setCar(existingCar);
            bookACar.setBookCarStatus(BookCarStatus.PENDING);
            long diffInMilliSeconds = bookACarDto.getToDate().getTime()-bookACarDto.getFromDate().getTime();
            long days= TimeUnit.MICROSECONDS.toDays(diffInMilliSeconds);
            bookACar.setDays(days);
            bookACar.setPrice(existingCar.getPrice()*days);
            bookACarRepository.save(bookACar);
            return true;
        }
        return false;
    }

    @Override
    public CarDto getCarById(Long carId) {
        Optional<Car> optionalCar= carRepository.findById(carId);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }


}