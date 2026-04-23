package mg.edu.eni.mobilemoney.service.user;

import mg.edu.eni.mobilemoney.model.User;

public interface IUserService {
    void addUser(User user);
    User searchByUsername(String username);

}