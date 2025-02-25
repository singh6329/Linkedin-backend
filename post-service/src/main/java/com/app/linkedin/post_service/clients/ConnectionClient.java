package com.app.linkedin.post_service.clients;

import com.app.linkedin.post_service.dtos.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "connections-service",path = "/connections")
public interface ConnectionClient {

    @GetMapping("core/firstDegree")
    List<PersonDto> getFirstDegreeConnections();
}
