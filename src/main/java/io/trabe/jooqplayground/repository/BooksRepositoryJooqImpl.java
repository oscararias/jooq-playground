package io.trabe.jooqplayground.repository;

import java.util.List;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import io.trabe.jooqplayground.generated.public_.tables.records.BookRecord;
import io.trabe.jooqplayground.pojos.MyBookRecord;
import lombok.extern.slf4j.Slf4j;

import static io.trabe.jooqplayground.generated.public_.Tables.BOOK;
import static io.trabe.jooqplayground.generated.public_.Tables.BOOK_STORE;
import static io.trabe.jooqplayground.generated.public_.tables.Author.AUTHOR;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Slf4j
@Repository
public class BooksRepositoryJooqImpl implements BooksRepository {

    private final DSLContext dslContext; // Examples use "create" as attribute name
    private final JdbcTemplate jdbcTemplate;

    public BooksRepositoryJooqImpl(DSLContext dslContext, DataSource dataSource) {
        this.dslContext = dslContext;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<String> authorsWithE() {
        return this.dslContext.selectFrom(AUTHOR)
                .where(AUTHOR.FIRST_NAME.contains("e"))
                .fetch(AUTHOR.FIRST_NAME);
    }


    @Override
    public List<MyBookRecord> bookFrom1948NoGeneration() {
        Query query = dslContext.select(field("BOOK.TITLE"), field("AUTHOR.FIRST_NAME"), field("AUTHOR.LAST_NAME"))
                .from(table("BOOK"))
                .join(table("AUTHOR"))
                .on(field("BOOK.AUTHOR_ID").eq(field("AUTHOR.ID")))
                .where(field("BOOK.PUBLISHED_IN").eq(1948));

        String sql = query.getSQL();
        log.debug("SQL to execute: {}", sql);
        List<Object> bindValues = query.getBindValues();
        log.debug("SQL params {}", bindValues);

        //Alternatively
        // String sql = query.getSQL(ParamType.INLINED);

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new MyBookRecord(rs.getString(1), rs.getString(2), rs.getString(3)),
                bindValues.toArray());
    }

    @Override
    public List<MyBookRecord> bookFrom1948NoGenerationFetch() {
        return dslContext.select(field("BOOK.TITLE"), field("AUTHOR.FIRST_NAME"), field("AUTHOR.LAST_NAME"))
                .from(table("BOOK"))
                .join(table("AUTHOR"))
                .on(field("BOOK.AUTHOR_ID").eq(field("AUTHOR.ID")))
                .where(field("BOOK.PUBLISHED_IN").eq(1948))
                .fetchInto(MyBookRecord.class);
    }


    @Override
    public List<MyBookRecord> bookFrom1948Generation() {
        Query query = dslContext.select(BOOK.TITLE, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
                .from(BOOK)
                .join(AUTHOR)
                .on(BOOK.AUTHOR_ID.eq(AUTHOR.ID))
                .where(BOOK.PUBLISHED_IN.eq(1948));

        String sql = query.getSQL();
        log.debug("SQL to execute: {}", sql);
        List<Object> bindValues = query.getBindValues();
        log.debug("SQL params {}", bindValues);

        //Alternatively
        // String sql = query.getSQL(ParamType.INLINED);

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new MyBookRecord(rs.getString(1), rs.getString(2), rs.getString(3)),
                bindValues.toArray());
    }



    @Override
    public Result<Record3<String, String, String>> bookFrom1948GenerationFetch() {
        return dslContext.select(BOOK.TITLE, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
                .from(BOOK)
                .join(AUTHOR)
                .on(BOOK.AUTHOR_ID.eq(AUTHOR.ID))
                .where(BOOK.PUBLISHED_IN.eq(1948))
                .fetch();
    }


    @Override
    public List<MyBookRecord> bookFrom1948GenerationFetchInto() {
        return dslContext.select(BOOK.TITLE, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
                .from(BOOK)
                .join(AUTHOR)
                .on(BOOK.AUTHOR_ID.eq(AUTHOR.ID))
                .where(BOOK.PUBLISHED_IN.eq(1948))
                .fetchInto(MyBookRecord.class);
    }


    @Override
    public Result<BookRecord> bookFrom1948GenerationFetchRecord() {
        return dslContext.selectFrom(BOOK)
                .where(BOOK.PUBLISHED_IN.ge(1948))
                .fetch();
    }


    @Override
    public Result<Record> booksFrom1948SqlExecutor() {
        String sql = "SELECT title, first_name, last_name FROM book JOIN author ON book.author_id = author.id " +
                "WHERE book.published_in = 1948";
        // Fetch results using jOOQ
        return  dslContext.fetch(sql);
    }


    @Override
    public void insertBookTwice(Integer id, String title, Integer publishedIn, Integer authorId, Integer languageId) {
        dslContext.insertInto(BOOK, BOOK.ID, BOOK.TITLE, BOOK.PUBLISHED_IN, BOOK.AUTHOR_ID, BOOK.LANGUAGE_ID)
//                .values(id, title, publishedIn, authorId, "aa") compilation error
                .values(id, title, publishedIn, authorId, languageId)
                .values(id+1, title + "-2", publishedIn, authorId, languageId)
                .execute();
    }


    @Override
    public Long insertBookStore(String name) {
        return  dslContext.insertInto(BOOK_STORE, BOOK_STORE.NAME)
                .values(name)
                .returning(BOOK_STORE.ID)
                .fetchOne().getId();
    }

    @Override
    public String findBookStoreNameById(Long id) {
        return dslContext.selectFrom(BOOK_STORE)
                .where(BOOK_STORE.ID.eq(id))
                .fetchOne(BOOK_STORE.NAME);
    }

    @Override
    public void updateAuthor() {
        dslContext.update(AUTHOR)
                .set(AUTHOR.FIRST_NAME, "Hermann")
                .set(AUTHOR.LAST_NAME, "Hesse")
                .where(AUTHOR.ID.eq(3))
                .execute();
    }


    @Override
    public void deleteAuthors() {
        dslContext.deleteFrom(AUTHOR)
                .where(AUTHOR.YEAR_OF_BIRTH.ge(1900));
    }

}
