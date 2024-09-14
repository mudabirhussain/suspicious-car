package ai.suspicious.car.records;

import java.util.List;

public record AIResponse(List<Car> cars, int notRecognized) {
}
