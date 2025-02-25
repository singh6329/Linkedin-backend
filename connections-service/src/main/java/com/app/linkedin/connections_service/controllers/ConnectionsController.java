package com.app.linkedin.connections_service.controllers;

import com.app.linkedin.connections_service.entities.Person;
import com.app.linkedin.connections_service.services.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionsController {

    private final ConnectionService connectionService;

    @GetMapping("/firstDegree")
    ResponseEntity<List<Person>> getFirstDegreeConnections()
    {
        return ResponseEntity.ok(connectionService.getMyFirstDegreeConnections());
    }

    @GetMapping("/secondDegree")
    ResponseEntity<List<Person>> getSecondDegreeConnections()
    {
        return ResponseEntity.ok(connectionService.getMySecondDegreeConnections());
    }

    @PostMapping("/send-request/{userId}")
    public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable Long userId)
    {
        return ResponseEntity.ok(connectionService.sendConnectionRequest(userId));
    }

    @PostMapping("/accept-request/{userId}")
    public ResponseEntity<Boolean> acceptConnectionRequest(@PathVariable Long userId)
    {
        return ResponseEntity.ok(connectionService.acceptConnectionRequest(userId));
    }

    @PostMapping("/reject-request/{userId}")
    public ResponseEntity<Boolean> rejectConnectionRequest(@PathVariable Long userId)
    {
        return ResponseEntity.ok(connectionService.rejectConnectionRequest(userId));
    }
}
