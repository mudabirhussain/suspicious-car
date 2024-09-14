package ai.suspicious.car.controllers;

import ai.suspicious.car.records.AIResponse;
import ai.suspicious.car.records.CarEntity;
import ai.suspicious.car.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/save")
    public String save(@RequestBody CarEntity carEntity) {
        return carService.save(carEntity);
    }

    @GetMapping("/all")
    public List<CarEntity> getAll() {
        return carService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<CarEntity> getById(@PathVariable(value = "id") int id) {
        return carService.findById(id);
    }

    @GetMapping("/{numberPlate}")
    public Optional<CarEntity> getByNumberPlate(@PathVariable(value = "numberPlate") String numberPlate) {
        return carService.findByNumberPlate(numberPlate);
    }

    @PostMapping("/suspicious-check")
    public ResponseEntity<AIResponse> checkSuspicious(@RequestParam("image") MultipartFile carImage) throws Exception {
        return carService.suspiciousCheck(carImage);
    }

}
