package com.infoshareacademy.dreamteam.mapper;

import com.infoshareacademy.dreamteam.domain.api.dto.AuthorDto;
import com.infoshareacademy.dreamteam.domain.entity.Author;
import com.infoshareacademy.dreamteam.domain.view.AuthorView;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AuthorMapper {

    public AuthorView mapEntityToView(Author author) {
        AuthorView authorView = new AuthorView();
        authorView.setId(author.getId());
        authorView.setName(author.getName());
        return authorView;
    }

    public Author mapToEntity(AuthorDto authorDto) {
        Author author = new Author();
        author.setName(authorDto.getName());
        return author;
    }

}
