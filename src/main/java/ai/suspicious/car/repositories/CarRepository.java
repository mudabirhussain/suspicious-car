package ai.suspicious.car.repositories;

import ai.suspicious.car.records.CarEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository {
    private final JdbcTemplate jdbcTemplate;

    public CarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CarEntity> carRowMapper = (rs, rowNum) -> new CarEntity(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("numberPlate"),
            rs.getString("fraud"),
            rs.getBytes("image")
    );


    public int save(CarEntity carEntity) {
        String sql = "INSERT INTO cars (id, name, numberPlate, fraud, image) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, carEntity.id(), carEntity.name(), carEntity.numberPlate(), carEntity.fraud(), carEntity.image());
    }


    public List<CarEntity> findAll() {
        String sql = "SELECT * FROM cars";
        return jdbcTemplate.query(sql, carRowMapper);
    }

    public Optional<CarEntity> findById(int id) {
        String sql = "SELECT * FROM cars WHERE id = ?";
        return jdbcTemplate.query(sql, carRowMapper, id)
                .stream().findFirst();
    }

    public Optional<CarEntity> findByNumberPlate(String numberPlate) {
        String sql = "SELECT * FROM cars WHERE numberPlate = ?";
        return jdbcTemplate.query(sql, carRowMapper, numberPlate)
                .stream().findFirst();
    }

    public int update(CarEntity carEntity) {
        String sql = "UPDATE cars SET name = ?, numberPlate = ?, fraud = ?, image = ? WHERE id = ?";
        return jdbcTemplate.update(sql, carEntity.name(), carEntity.numberPlate(), carEntity.fraud(), carEntity.image(), carEntity.id());
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM cars WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
