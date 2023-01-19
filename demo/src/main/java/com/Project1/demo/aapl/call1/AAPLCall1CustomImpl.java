package com.Project1.demo.aapl.call1;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
//@Transactional(readOnly = true)
//@Transactional
public class AAPLCall1CustomImpl implements AAPLCall1Custom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Call1> getCallsUsingDeltaRange(float low, float high) {
        Query query = entityManager.createNativeQuery("SELECT * FROM Call1", Call1.class);
       // query.setParameter(1, firstName + "%");

        return query.getResultList();
    }
}
