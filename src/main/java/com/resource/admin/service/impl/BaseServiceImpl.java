package com.resource.admin.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseServiceImpl {

    @Autowired
    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected JPAQueryFactory queryFactory;

    @Bean
    public JPAQueryFactory queryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

}
