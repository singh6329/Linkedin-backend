package com.app.linkedin.connections_service.repositories;

import com.app.linkedin.connections_service.entities.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends Neo4jRepository<Person,Long>{

    @Query(value = "MATCH (personA:Person) - [:CONNECTED_TO]- (personB:Person)" +
            " WHERE personA.userId = $userId " +
            "RETURN personB")
    List<Person> getFirstDegreeConnections(Long userId);

    @Query(value = "match (personA:Person)-[:CONNECTED_TO*2]->(personC:Person) " +
            "where personA.userId=$userId AND NOT(personA)-[:CONNECTED_TO]->(personC) " +
            "return personC;")
    List<Person> getSecondDegreeConnections(Long userId);

    @Query("Match(p1:Person)-[r:REQUESTED_TO]->(p2:Person) " +
            "WHERE p1.userId= $senderId AND p2.userId= $receiverId " +
            "return count(r) > 0")
    Boolean connectionRequestExists(Long senderId,Long receiverId);

    @Query("Match(p1:Person)-[r:CONNECTED_TO]-(p2:Person) " +
            "WHERE p1.userId=$senderId AND p2.userId=$receiverId " +
            "return count(r) > 0")
    Boolean alreadyConnected(Long senderId,Long receiverId);

    @Query("Match(p1:Person),(p2:Person) " +
            "where p1.userId=$senderId AND p2.userId=$receiverId " +
            "create (p1)-[r:REQUESTED_TO]->(p2)")
    void addConnectionRequest(Long senderId,Long receiverId);

    @Query("Match(p1:Person)-[r:REQUESTED_TO]->(p2:Person) " +
            "where p1.userId=$senderId AND p2.userId=$receiverId " +
            "delete r " +
            "CREATE (p1)-[:CONNECTED_TO]->(p2)")
    void acceptConnectionRequest(Long senderId,Long receiverId);

    @Query("Match(p1:Person)-[r:REQUESTED_TO]->(p2:Person) " +
            "where p1.userId=$senderId AND p2.userId=$receiverId " +
            "delete r")
    void rejectConnectionRequest(Long senderId,Long receiverId);

    @Query("CREATE(p1:Person{userId:$userId,name:$name,email:$email}) ")
    void addPerson(Long userId,String name,String email);
}
