package br.ufpr.tads.msbantadsauth.Infrastructure.Persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.ufpr.tads.msbantadsauth.Domain.Entities.User;

public interface UserRepository extends MongoRepository<User, String>{

    @Query("{email:'?0', blocked:false}")
    User findUserByLogin(String email);

    @Query("{cpf:'?0'}")
    User findUserByCPF(String cpf);
}
