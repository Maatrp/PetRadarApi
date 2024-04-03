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

//    @Override
//    public void updateByUserName(String userName, User user) {
//        Query query = new Query(Criteria.where("userName").is(userName));
//        Update update = new Update()
//                .set("userName", user.getUserName()) // Actualiza userName
//                .set("name", user.getName()) // Actualiza name
//                .set("field3", user.getField3()) // Agrega más campos según sea necesario
//                .set("lastModifiedDate", new Date()); // Ejemplo: establece la fecha de modificación
//        mongoTemplate.updateFirst(query, update, User.class);
//    }


    void deleteByUserName(String userName);

}