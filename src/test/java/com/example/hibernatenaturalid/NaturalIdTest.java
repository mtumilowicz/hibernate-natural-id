package com.example.hibernatenaturalid;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

/**
 * Created by mtumilowicz on 2018-10-22.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class NaturalIdTest {

    @PersistenceContext
    private EntityManager em;
    
    @Test
    @Transactional
    public void loadBySimpleNaturalId() {
//        given
        Session session = em.unwrap(Session.class);

//        when
        Book book = session.bySimpleNaturalId(Book.class)
                .load("978-3-16-148410-0");

//        then
        assertThat(book.getId(), is(1));
        assertThat(book.getIsbn(), is("978-3-16-148410-0"));
        
        session.close();
    }

    @Test
    @Transactional
    public void loadOptionalBySimpleNaturalId() {
//        given
        Session session = em.unwrap(Session.class);

//        when
        Optional<Book> book = session.bySimpleNaturalId(Book.class)
                .loadOptional("978-3-16-148410-0");

//        then
        assertTrue(book.isPresent());
        assertThat(book.get().getId(), is(1));
        assertThat(book.get().getIsbn(), is("978-3-16-148410-0"));

        session.close();
    }

    @Test
    @Transactional
    public void proxyBySimpleNaturalId() {
//        given
        Session session = em.unwrap(Session.class);

//        when
        Book book = session.bySimpleNaturalId(Book.class)
                .getReference("978-3-16-148410-0");

//        then
        assertThat(book.getId(), is(1));
        assertThat(book.getIsbn(), is("978-3-16-148410-0"));

        session.close();
    }
}
