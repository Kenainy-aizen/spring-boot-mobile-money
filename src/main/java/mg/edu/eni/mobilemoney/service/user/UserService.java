package mg.edu.eni.mobilemoney.service.user;

import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.model.User;
import mg.edu.eni.mobilemoney.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User searchByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
