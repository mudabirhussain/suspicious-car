package ai.suspicious.car.records;

public record CarEntity(
        int id,
        String name,
        String numberPlate,
        String fraud,
        byte[] image) {
}
