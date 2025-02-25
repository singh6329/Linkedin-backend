package com.app.linkedin.connections_service.consumers;

import com.app.linkedin.connections_service.repositories.PersonRepository;
import com.app.linkedin.user_service.events.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCreatedEventConsumer {

    private final PersonRepository personRepository;

    @KafkaListener(topics = "user-created-topic")
    public void handleUserCreatedTopic(UserCreatedEvent userCreatedEvent)
    {
        log.info("Adding new person in the connections db....");
        personRepository.addPerson(userCreatedEvent.getUserId(), userCreatedEvent.getName(), userCreatedEvent.getEmail());
        log.info("New person added....");
    }

}
