package com.infoshareacademy.dreamteam.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshareacademy.dreamteam.domain.api.BookDetailsPlain;
import com.infoshareacademy.dreamteam.domain.api.BookPlain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileParser {
    private static final Logger logger = LoggerFactory.getLogger(FileParser.class.getName());

    public List<BookDetailsPlain> readBookList(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file, new TypeReference<List<BookDetailsPlain>>() {
            });
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return List.of();
        }
    }
}
