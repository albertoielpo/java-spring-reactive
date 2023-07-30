package net.ielpo.reactivestack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ielpo.reactivestack.dao.AccessLog;
import net.ielpo.reactivestack.dto.BaseRes;
import net.ielpo.reactivestack.factory.ResponseFactory;
import net.ielpo.reactivestack.service.AccessLogService;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@RestController
@RequestMapping(path = "/access-log")
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseRes<List<AccessLog>>> getAll() {
        // collect flux
        Mono<List<AccessLog>> accessLogs = accessLogService.findAll().collectList();
        return ResponseFactory.build(accessLogs);

    }

}
