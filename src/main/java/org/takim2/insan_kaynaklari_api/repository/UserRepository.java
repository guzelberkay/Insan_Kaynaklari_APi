package org.takim2.insan_kaynaklari_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.takim2.insan_kaynaklari_api.Vw.UserView;
import org.takim2.insan_kaynaklari_api.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select new org.takim2.insan_kaynaklari_api.Vw.UserView (u.id,u.firstName,u.lastName,u.email,u.userStatus,u.userRole) from User u where u.id in :companyManagersUserIds")
    List<UserView> findAllByIds(List<Long> companyManagersUserIds);
}
