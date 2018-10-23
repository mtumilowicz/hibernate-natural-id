# hibernate-natural-id
Exploring basic features of `@NaturalId` (hibernate annotation).

_Reference_: http://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#naturalid  
_Reference_: https://www.thoughts-on-java.org/naturalid-good-way-persist-natural-ids-hibernate/

# preface
Natural ids represent domain model unique identifiers that have a meaning 
in the real world too. Even if a natural id does not make a good primary 
key, it’s still useful to tell Hibernate about it.

Most often, it’s a better idea to generate numeric, surrogate keys. 
They are easier to manage, and most frameworks can handle them more 
efficiently than more complex natural identifiers.

A natural id may be mutable or immutable. By default the @NaturalId 
annotation marks an immutable natural id attribute. 

Within the Session, Hibernate maintains a mapping from natural id 
values to entity identifiers (PK) values. If natural ids values 
changed, it is possible for this mapping to become out of date 
until a flush occurs.

# manual
1. Put `@NaturalId` annotation over attributes in entity
1. We have two possibilities:
    * only one attribute with `@NaturalId`, then load entity using:
        ```
        Book book = session.bySimpleNaturalId(Book.class)
                .load("978-3-16-148410-0");
                
        Optional<Book> book = session.bySimpleNaturalId(Book.class)
                .loadOptional("978-3-16-148410-0");
                
        Book book = session.bySimpleNaturalId(Book.class)
                .getReference("978-3-16-148410-0");                         
        ```
        
    * any given number of `@NaturalId`
        ```
        Computer computer = session.byNaturalId(Computer.class)
                .using("macAddress", "12-34-56-78-9A-BC")
                .using("serialNumber", 111L)
                .load();
                
        Optional<Computer> computerOptional = session.byNaturalId(Computer.class)
                .using("macAddress", "12-34-56-78-9A-BC")
                .using("serialNumber", 111L)
                .loadOptional();
                
                
        Computer computerOptional = session.byNaturalId(Computer.class)
                .using("macAddress", "12-34-56-78-9A-BC")
                .using("serialNumber", 111L)
                .getReference();                        
        ```
        
    * **If the entity does not define a natural id, or if 
              the natural id is not of a "simple" type, an exception will 
              be thrown there.**
# internals
Hibernate performs 2 queries:
```
Hibernate: select book_.id as id1_0_ from book book_ where book_.isbn=?
Hibernate: select book0_.id as id1_0_0_, book0_.isbn as isbn2_0_0_ from book book0_ where book0_.id=?
```
1. We find PK for entity using natural id
1. We find entity for PK found in (1.)

Hibernate performs that simple action in two steps because of
L1, L2 cache (based on PK).

# project description
* Entity with only one `@NaturalId`
```
public class Book {
    @Id
    Integer id;
    
    @NaturalId
    String isbn;
}
```

* Entity with two `@NaturalId`
```
public class Computer {
    @Id
    Integer id;
    
    @NaturalId
    Long serialNumber;
    
    @NaturalId
    String macAddress;
}
```

* In `NaturalIdTest` we give examples of loading entities.