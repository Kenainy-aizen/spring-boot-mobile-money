package mg.edu.eni.mobilemoney.repository;

import com.fasterxml.jackson.annotation.OptBoolean;
import mg.edu.eni.mobilemoney.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer > {

    Optional<User> findByUsername(String username);
}
