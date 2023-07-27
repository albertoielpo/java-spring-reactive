package net.ielpo.reactivestack.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import net.ielpo.reactivestack.dao.AccessLog;

/**
 * @author Alberto Ielpo
 */
@Repository
public interface AccessLogRepository extends ReactiveMongoRepository<AccessLog, String> {

}
