package net.ielpo.reactivestack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ielpo.reactivestack.dao.AccessLog;
import net.ielpo.reactivestack.repository.AccessLogRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@Service
public class AccessLogService {

    @Autowired
    private AccessLogRepository accessLogRepository;

    public Mono<AccessLog> save(AccessLog s) {
        return accessLogRepository.save(s);
    }

    public Flux<AccessLog> findAll() {
        return accessLogRepository.findAll();
    }
}
