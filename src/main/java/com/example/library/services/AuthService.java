package com.example.library.services;

import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import com.vaadin.flow.component.Component;
import lombok.SneakyThrows;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public record AuthorizedRoute(String route, String name, Class<? extends Component> view){

    }
    public class AuthException extends Exception{

    }
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authericate(String username, String password) throws AuthException
    {
        User user = userRepository.getByUsername(username);

        if(user != null && user.chceckPassword(password))
        {

        } else {
            throw new AuthException();
        }
    }
}
