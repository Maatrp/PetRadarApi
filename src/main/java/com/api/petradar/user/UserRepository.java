package com.api.petradar.user;


import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByUserName(String userName);

    @Query("{'userName' : ?0}")
    User findUserByUserName(String userName);

    @Query("{'email' : ?0}")
    User findUserByEmail(String email);

    @Query("{ 'userName' : ?0, 'password' : ?1}")
    User findUserByUserNamePassword(String userName, String password);

    void deleteByUserName(String userName);

}