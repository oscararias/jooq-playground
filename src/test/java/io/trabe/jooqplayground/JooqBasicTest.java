package io.trabe.jooqplayground;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import io.trabe.jooqplayground.generated.public_.tables.daos.AuthorDao;
import io.trabe.jooqplayground.generated.public_.tables.pojos.Author;
import io.trabe.jooqplayground.repository.BooksRepository;
import lombok.extern.slf4j.Slf4j;

//@JooqTest Injects configured DslContext
@SpringBootTest(classes = JooqPlaygroundApplication.class)
@Slf4j
public class JooqBasicTest {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorDao authorDao;

    @Test
    public void testQuery() {
        log.debug("Authors containing E: {}", booksRepository.authorsWithE());
        Assertions.assertTrue(true);
    }

    @Test
    public void testJdbcQueryWithoutCodeGeneration() {
        log.debug("Books from 1948: {}", booksRepository.bookFrom1948NoGeneration());
        Assertions.assertTrue(true);
    }

    @Test
    public void testJdbcQueryWithCodeGeneration() {
        log.debug("Books from 1948: {}", booksRepository.bookFrom1948Generation());
        Assertions.assertTrue(true);
    }

    @Test
    public void testCodeGenerationAndFetch() {
        log.debug("Books from 1948: \n{}", booksRepository.bookFrom1948GenerationFetch());
        Assertions.assertTrue(true);
    }

    @Test
    public void testCodeGenerationAndFetchInto() {
        log.debug("Books from 1948: \n{}", booksRepository.bookFrom1948GenerationFetchInto());
        Assertions.assertTrue(true);
    }

    @Test
    public void testCodeGenerationAndFetchRecord() {
        log.debug("Books from 1948: \n{}", booksRepository.bookFrom1948GenerationFetchRecord().stream().toList());
        Assertions.assertTrue(true);
    }

    @Test
    public void testSqlExecution() {
        log.debug("Books from 1948: \n{}", booksRepository.booksFrom1948SqlExecutor());
        Assertions.assertTrue(true);
    }

    @Test
    public void testFindByUnknownId() {
        Assertions.assertNull(booksRepository.findBookStoreNameById(1000l));
    }

    @Test
    public void testInsertAndReturnId() {
        String newName = "Shakespeare & Co.";
        Long newId = booksRepository.insertBookStore(newName);
        Assertions.assertEquals(newName, booksRepository.findBookStoreNameById(newId));
    }

    @Test
    public void testDAO() {
        List<Author> authors = authorDao.fetchRangeOfYearOfBirth(1900, 1950);
        log.debug("Authors from 1900 to 1950: {}", authors);
        Assertions.assertEquals(2, authors.size());
    }


    @Test
    public void testDAOUpdate() {
        Author author = authorDao.fetchOneById(1);
        author.setLastName(author.getLastName() + " - Updated");
        authorDao.update(author);
        author = authorDao.fetchOneById(author.getId());
        log.debug("Updated author: {}", author);
        Assertions.assertTrue(author.getLastName().endsWith(" - Updated"));
    }

    @Test
    public void testDAODuplicatedSpring() {
        Author author = new Author();
        author.setId(1);
        author.setLastName("Test Last Name");
        Assertions.assertThrows(DuplicateKeyException.class, () -> authorDao.insert(author));
    }
}
