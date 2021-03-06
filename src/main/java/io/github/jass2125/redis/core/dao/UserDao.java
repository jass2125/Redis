/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.redis.core.dao;

import io.github.jass2125.redis.core.entity.UserPrincipal;
import io.github.jass2125.redis.core.exceptions.LoginInvalidException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * @author <a href="mailto:jair_anderson_bs@hotmail.com">Anderson Souza</a>
 * @since Aug 4, 2017 10:34:01 AM
 */
public class UserDao {

    @Inject
    private EntityManager em;

    public UserPrincipal searchByEmailAndPassword(String email, String password) throws LoginInvalidException {
        try {
            return em.createQuery("SELECT U FROM UserPrincipal U WHERE U.email = :email AND U.password = :password", UserPrincipal.class).
                    setParameter("email", email).
                    setParameter("password", password).
                    getSingleResult();
        } catch (NoResultException e) {
            throw new LoginInvalidException(e, "Os dados estão inválidos!");
        }
    }

    public UserPrincipal searchById(Long id) {
        try {
            return em.createQuery("SELECT U FROM UserPrincipal U WHERE U.id = :id ", UserPrincipal.class).
                    setParameter("id", id).
                    getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
