package com.familywebshop.stylet.repository.user;

import com.familywebshop.stylet.model.user.SubscribedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribedUserRepository extends JpaRepository<SubscribedUser, Long> {
}
