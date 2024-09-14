package ai.suspicious.car.services;

import ai.suspicious.car.records.AIResponse;
import ai.suspicious.car.records.CarEntity;
import ai.suspicious.car.repositories.CarRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    private final ChatClient chatClient;

    @Value("classpath:/prompts/suspicious-check-prompt.st")
    private Resource suspiciousCheckPrompt;

    public CarService(CarRepository carRepository, ChatClient chatClient) {
        this.carRepository = carRepository;
        this.chatClient = chatClient;
    }

    public List<CarEntity> findAll() {
        return carRepository.findAll();
    }

    public Optional<CarEntity> findById(int id) {
        return carRepository.findById(id);
    }

    public Optional<CarEntity> findByNumberPlate(String numberPlate) {
        return carRepository.findByNumberPlate(numberPlate);
    }

    public String save(CarEntity carEntity) {
        if(carRepository.save(carEntity) == 0){
            return "NOT ACCEPTED";
        }
        return "ACCEPTED";
    }

    public String update(CarEntity carEntity) {
        if(carRepository.update(carEntity) == 0){
            return "NOT ACCEPTED";
        }
        return "ACCEPTED";
    }

    public String delete(int id) {
        if(carRepository.deleteById(id) == 0){
            return "NOT DELETED";
        }
        return "DELETED";
    }

    public ResponseEntity<AIResponse> suspiciousCheck(MultipartFile carImage) throws Exception {
        if (carImage.isEmpty()) {
           throw new Exception("IMAGE NOT PROVIDED");
        }
        var processedCarImageResource = new ByteArrayResource(carImage.getBytes());

        var response = chatClient.prompt()
                .user(promptUserSpec -> promptUserSpec
                        .text(suspiciousCheckPrompt)
                        .media(MimeTypeUtils.IMAGE_JPEG, processedCarImageResource))
                .call()
                .entity(AIResponse.class);
        return ResponseEntity.ok(response);
    }
}
