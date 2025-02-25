package com.app.linkedin.connections_service.services;

import com.app.linkedin.connections_service.entities.Person;

import java.util.List;

public interface ConnectionService {

    List<Person> getMyFirstDegreeConnections();
    List<Person> getMySecondDegreeConnections();

    Boolean sendConnectionRequest(Long userId);

    Boolean acceptConnectionRequest(Long userId);

    Boolean rejectConnectionRequest(Long userId);
}
