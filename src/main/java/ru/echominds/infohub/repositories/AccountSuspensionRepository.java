package ru.echominds.infohub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.echominds.infohub.domain.AccountSuspension;
import ru.echominds.infohub.domain.User;

import java.util.Optional;

@Repository
public interface AccountSuspensionRepository extends JpaRepository<AccountSuspension, Long> {
    Optional<AccountSuspension> findByUser(User user);
}
