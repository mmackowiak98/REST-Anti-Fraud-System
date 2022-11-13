package antifraud.repository;

import antifraud.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);

    void deleteByUsernameIgnoreCase(String username);


    List<User> getByOrderByIdAsc();




    @Override
    <S extends User> S save(S entity);
}
