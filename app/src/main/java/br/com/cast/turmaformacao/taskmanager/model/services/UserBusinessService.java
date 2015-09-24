package br.com.cast.turmaformacao.taskmanager.model.services;

import br.com.cast.turmaformacao.taskmanager.model.entities.User;
import br.com.cast.turmaformacao.taskmanager.model.persistence.user.UserRepository;

public final class UserBusinessService {

    private UserBusinessService() {
        super();
    }

    public static void save(User user) {
        UserRepository.save(user);
    }

    public static boolean verifyUser(User user) {
        return UserRepository.verifyUser(user);
    }

}
