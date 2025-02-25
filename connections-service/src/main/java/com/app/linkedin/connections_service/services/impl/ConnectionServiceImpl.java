package com.app.linkedin.connections_service.services.impl;

import com.app.linkedin.connections_service.auth.UserContextHolder;
import com.app.linkedin.connections_service.entities.Person;
import com.app.linkedin.connections_service.events.AcceptConnectionRequestEvent;
import com.app.linkedin.connections_service.events.SendConnectionRequestEvent;
import com.app.linkedin.connections_service.exceptions.BadRequestException;
import com.app.linkedin.connections_service.repositories.PersonRepository;
import com.app.linkedin.connections_service.services.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

    private final PersonRepository personRepository;

    private final KafkaTemplate<Long, SendConnectionRequestEvent> sendRequestKafkaTemplate;
    private final KafkaTemplate<Long, AcceptConnectionRequestEvent> acceptRequestKafkaTemplate;

    @Override
    public List<Person> getMyFirstDegreeConnections() {
        Long userId = UserContextHolder.getCurrentUserId();
        return personRepository.getFirstDegreeConnections(userId);
    }

    @Override
    public List<Person> getMySecondDegreeConnections() {
        Long userId = UserContextHolder.getCurrentUserId();
        return personRepository.getSecondDegreeConnections(userId);
    }

    @Override
    public Boolean sendConnectionRequest(Long receiverId) {
        Long senderId = UserContextHolder.getCurrentUserId();
        log.info("Trying to send connection request, Sender {} ,Receiver {}",senderId,receiverId);

        if(senderId.equals(receiverId))
            throw new BadRequestException("Both sender and receiver are the same!");

        Boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);
        if (alreadyConnected)
            throw new BadRequestException("Already Connected Users! Cannot add connection request!");

        Boolean alreadySentRequest = personRepository.connectionRequestExists(senderId,receiverId);
        if(alreadySentRequest)
            throw new BadRequestException("Connection Request already exists! Cannot send again!");

        personRepository.addConnectionRequest(senderId,receiverId);
        log.info("Successfully sent the connection request!");
        SendConnectionRequestEvent sendConnectionRequestEvent = SendConnectionRequestEvent.builder()
                                                                                .senderId(senderId)
                                                                                .receiverId(receiverId)
                                                                                .build();
        sendRequestKafkaTemplate.send("send-connection-request-topic",sendConnectionRequestEvent);

        return true;
    }

    @Override
    public Boolean acceptConnectionRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        log.info("Attempting to accept connection, Sender {}, Receiver {} ",senderId,receiverId);
        Boolean connectionRequestExists = personRepository.connectionRequestExists(senderId,receiverId);

        if(!connectionRequestExists)
            throw new BadRequestException("No connection request exists!");

        personRepository.acceptConnectionRequest(senderId,receiverId);
        log.info("Connection request accepted!");
        AcceptConnectionRequestEvent acceptConnectionRequestEvent = AcceptConnectionRequestEvent.builder()
                                                                    .senderId(senderId)
                                                                    .receiverId(receiverId).build();
        acceptRequestKafkaTemplate.send("accept-connection-request-topic",acceptConnectionRequestEvent);
        return true;
    }

    @Override
    public Boolean rejectConnectionRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        log.info("Attempting to reject the connection Request!");
        Boolean connectionRequestExists = personRepository.connectionRequestExists(senderId,receiverId);
        if(!connectionRequestExists)
            throw new BadRequestException("No connection request exists!");

        personRepository.rejectConnectionRequest(senderId,receiverId);
        log.info("Connection Request rejected!");
        return true;
    }


}
