package com.infoshareacademy.dreamteam.mapper;

import com.infoshareacademy.dreamteam.domain.api.*;
import com.infoshareacademy.dreamteam.domain.api.dto.*;
import com.infoshareacademy.dreamteam.domain.entity.*;
import com.infoshareacademy.dreamteam.domain.view.BookView;
import com.infoshareacademy.dreamteam.service.AuthorService;
import com.infoshareacademy.dreamteam.service.EpochService;
import com.infoshareacademy.dreamteam.service.GenreService;
import com.infoshareacademy.dreamteam.service.KindService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class BookMapper {

    private static final String DEFAULT_COVER = "/images/missing-cover.png";
    @Inject
    private AuthorService authorService;
    @Inject
    private EpochService epochService;
    @Inject
    private GenreService genreService;
    @Inject
    private KindService kindService;
    @Inject
    private AuthorMapper authorMapper;
    @Inject
    private EpochMapper epochMapper;
    @Inject
    private GenreMapper genreMapper;
    @Inject
    private KindMapper kindMapper;


    public BookView mapEntityToView(Book book) {
        BookView bookView = new BookView();
        bookView.setId(book.getId());
        bookView.setTitle(book.getTitle());
        bookView.setAudio(book.getAudio());
        bookView.setFragment(book.getFragment());
        bookView.setIsbn(book.getIsbn());
        bookView.setTranslators(book.getTranslators());
        if (book.getCover() == null || book.getCover().isEmpty()) {
            bookView.setCover(DEFAULT_COVER);
        } else {
            bookView.setCover(book.getCover());
        }
        return bookView;
    }

    public Book mapPlainAudioToEntity(BookPlain bookPlain, Book book) {
        if (bookPlain.getAudio() != null) {
            book.setAudio(bookPlain.getAudio());
        } else {
            book.setAudio(false);
        }
        return book;
    }

    public Book mapPlainToEntity(BookDetailsPlain bookDetailsPlain) {
        Book book = new Book();
        book.setId(bookDetailsPlain.getId());
        book.setTitle(bookDetailsPlain.getTitle());
        if (bookDetailsPlain.getFragmentData() != null) {
            book.setFragment(bookDetailsPlain.getFragmentData().getHtml());
        }
        book.setIsbn(bookDetailsPlain.getIsbn());
        book.setTranslators(bookDetailsPlain.getTranslators()
                .stream()
                .map(TranslatorPlain::getName)
                .collect(Collectors.joining(", ")));
        if (bookDetailsPlain.getCover() == null || bookDetailsPlain.getCover().isEmpty()) {
            book.setCover(DEFAULT_COVER);
        } else {
            book.setCover(bookDetailsPlain.getCover());
        }
        List<Author> authors = new ArrayList<>();
        for (AuthorPlain a : bookDetailsPlain.getAuthors()) {
            Author author = authorService.findByName(a.getName());
            author.getBooks().add(book);
            authors.add(author);
        }
        book.setAuthors(authors);

        List<Epoch> epochs = new ArrayList<>();
        for (EpochPlain e : bookDetailsPlain.getEpochs()) {
            Epoch epoch = epochService.findByName(e.getName());
            epoch.getBooks().add(book);
            epochs.add(epoch);
        }
        book.setEpochs(epochs);

        List<Genre> genres = new ArrayList<>();
        for (GenrePlain g : bookDetailsPlain.getGenres()) {
            Genre genre = genreService.findByName(g.getName());
            genre.getBooks().add(book);
            genres.add(genre);
        }
        book.setGenres(genres);

        List<Kind> kinds = new ArrayList<>();
        for (KindPlain k : bookDetailsPlain.getKinds()) {
            Kind kind = kindService.findByName(k.getName());
            kind.getBooks().add(book);
            kinds.add(kind);
        }
        book.setKinds(kinds);

        return book;
    }

    public Book mapToEntity(BookDto bookDto) {
        Book book = new Book();
        return updateBookEntity(book, bookDto);
    }

    public Book updateBookEntity(Book book, BookDto bookDto) {
        book.setTitle(bookDto.getTitle());
        book.setAudio(bookDto.getAudio());
        book.setIsbn(bookDto.getIsbn());
        book.setFragment(bookDto.getFragmentData());
        book.setTranslators(bookDto.getTranslators()
                .stream()
                .map(TranslatorDto::getName)
                .collect(Collectors.joining(", ")));

        if (book.getCover() == null) {
            if (bookDto.getCover() == null || bookDto.getCover().isEmpty()) {
                book.setCover(DEFAULT_COVER);
            } else {
                book.setCover(bookDto.getCover());
            }
        }

        List<Author> authors = new ArrayList<>();
        for (AuthorDto authorDto : bookDto.getAuthors()) {
            Author author = authorService.findByName(authorDto.getName());
            if (author == null) {
                author = authorMapper.mapToEntity(authorDto);
            }
            if (!author.getBooks().contains(book)) {
                author.getBooks().add(book);
            }
            authors.add(author);
        }
        for (Author author : book.getAuthors()) {
            author.getBooks().remove(book);
        }
        book.setAuthors(authors);
        for (Author author : book.getAuthors()) {
            List<Book> books = author.getBooks();
            if (!books.contains(book)) {
                author.getBooks().add(book);
            }
        }

        List<Epoch> epochs = new ArrayList<>();
        for (EpochDto epochDto : bookDto.getEpochs()) {
            Epoch epoch = epochService.findByName(epochDto.getName());
            if (epoch == null) {
                epoch = epochMapper.mapToEntity(epochDto);
            }
            if (!epoch.getBooks().contains(book)) {
                epoch.getBooks().add(book);
            }
            epochs.add(epoch);
        }
        for (Epoch epoch : book.getEpochs()) {
            epoch.getBooks().remove(book);
        }
        book.setEpochs(epochs);
        for (Epoch epoch : book.getEpochs()) {
            List<Book> books = epoch.getBooks();
            if (!books.contains(book)) {
                epoch.getBooks().add(book);
            }
        }

        List<Genre> genres = new ArrayList<>();
        for (GenreDto genreDto : bookDto.getGenres()) {
            Genre genre = genreService.findByName(genreDto.getName());
            if (genre == null) {
                genre = genreMapper.mapToEntity(genreDto);
            }
            if (!genre.getBooks().contains(book)) {
                genre.getBooks().add(book);
            }
            genres.add(genre);
        }
        for (Genre genre : book.getGenres()) {
            genre.getBooks().remove(book);
        }
        book.setGenres(genres);
        for (Genre genre : book.getGenres()) {
            List<Book> books = genre.getBooks();
            if (!books.contains(book)) {
                genre.getBooks().add(book);
            }
        }

        List<Kind> kinds = new ArrayList<>();
        for (KindDto kindDto : bookDto.getKinds()) {
            Kind kind = kindService.findByName(kindDto.getName());
            if (kind == null) {
                kind = kindMapper.mapToEntity(kindDto);
            }
            if (!kind.getBooks().contains(book)) {
                kind.getBooks().add(book);
            }
            kinds.add(kind);
        }
        for (Kind kind : book.getKinds()) {
            kind.getBooks().remove(book);
        }
        book.setKinds(kinds);
        for (Kind kind : book.getKinds()) {
            List<Book> books = kind.getBooks();
            if (!books.contains(book)) {
                kind.getBooks().add(book);
            }
        }
        return book;
    }
}
