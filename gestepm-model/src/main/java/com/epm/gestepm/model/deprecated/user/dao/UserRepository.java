package com.epm.gestepm.model.deprecated.user.dao;

import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {

	@Query("SELECT u FROM User u WHERE u.signingId IN :ids")
	List<User> findBySigningIds(@Param("ids") List<Long> ids);

	User findUsuarioByEmailAndPassword(String email, String password);

}
