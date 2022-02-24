package io.trabe.jooqplayground.repository;

import java.util.List;

import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;

import io.trabe.jooqplayground.generated.public_.tables.records.BookRecord;
import io.trabe.jooqplayground.pojos.MyBookRecord;

public interface BooksRepository {
    List<String> authorsWithE();

    List<MyBookRecord> bookFrom1948NoGeneration();

    List<MyBookRecord> bookFrom1948NoGenerationFetch();

    List<MyBookRecord> bookFrom1948Generation();

    Result<Record3<String, String, String>> bookFrom1948GenerationFetch();

    List<MyBookRecord> bookFrom1948GenerationFetchInto();

    Result<BookRecord> bookFrom1948GenerationFetchRecord();

    Result<Record> booksFrom1948SqlExecutor();

    void insertBookTwice(Integer id, String title, Integer publishedIn, Integer authorId, Integer languageId);

    Long insertBookStore(String name);

    String findBookStoreNameById(Long id);

    void updateAuthor();

    void deleteAuthors();
}
