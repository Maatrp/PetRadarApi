package com.api.petradar.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByUserName(String userName);

    @Query("{'userName' : ?0}")
    User findUserByUserName(String userName);

    @Query("{'_id' : ?0}")
    User findUserById(String id);

    @Query("{'email' : ?0}")
    User findUserByEmail(String email);

    @Query("{ 'userName' : ?0, 'password' : ?1}")
    User findUserByUserNamePassword(String userName, String password);

    default void updateByUserName(String userName, User user) {
        User existingUser = findUserByUserName(userName);
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            save(existingUser);
        }
    }


    void deleteByUserName(String userName);

}