package com.spring.hrrecruitmentbackend.gate;


import com.spring.hrrecruitmentbackend.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class WebSocketController {


    private final UsersService usersService;

    @MessageMapping("/read")
    @SendTo("/topic/events")
    public Event entryGate(@Payload Event event){
        event.validate();
        boolean ifUserExists = usersService.getActivatedUserByEmail(event.getEmail());

        if (!ifUserExists) {
            event.setIsUserAvailable(false);
        }
        return event;


    }
}
