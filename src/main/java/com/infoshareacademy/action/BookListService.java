package com.infoshareacademy.action;

import com.infoshareacademy.Book;
import com.infoshareacademy.ConsoleColors;
import com.infoshareacademy.menu.Menu;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class BookListService implements ConsoleColors{
    private static final Logger STDOUT = LoggerFactory.getLogger("CONSOLE_OUT");
    private static final Scanner scanner = new Scanner(System.in);
    private static final String WRONG_NUMBER = "Proszę wpisać odpowiednią cyfrę.";
    private int input;
    private int positionsPerPage;
    private int currentPageNumber;
    private int numberOfPages;

    public BookListService() {
        this.currentPageNumber = 1;
        this.numberOfPages = 2;
        this.input = 0;
    }

    public int getUserInput() {
        String lineInput = scanner.nextLine();
        if (NumberUtils.isCreatable(lineInput)) {
            input = Integer.parseInt(lineInput);
        } else {
            STDOUT.info(WRONG_NUMBER);
        }
        return input;
    }

    public void run() {
        STDOUT.info("\n Proszę wpisać ile rekordów ma być wyświetlane na ekranie: \n");
        positionsPerPage = getUserInput();
        while (true) {
            getBooksList();
            input = getUserInput();
            if (input == 1 && currentPageNumber < numberOfPages) {
                currentPageNumber = currentPageNumber + 1;
            }
            if (input == 2 && currentPageNumber > 1) {
                currentPageNumber = currentPageNumber - 1;
            }
            if (input == 0) {
                break;
            }
            input = 5;
        }
    }

    public void getBooksList() {
        Menu menu = new Menu();
        menu.cleanTerminal();
        Book book = new Book();
        ArrayList<Book> books = book.getBook();
        if (positionsPerPage != 0) {
            numberOfPages = (int) Math.ceil((double) books.size() / positionsPerPage);
        }
        int firstPositionOnPage = (currentPageNumber - 1) * positionsPerPage;
        int lastPositionOnPage = firstPositionOnPage + positionsPerPage;
        for (int i = 0; i < positionsPerPage; i++) {
            if (lastPositionOnPage > books.size()) {
                lastPositionOnPage = lastPositionOnPage - 1;
            }
        }
        for (int i = firstPositionOnPage; i < lastPositionOnPage; i++) {
            int positionNumber = i + 1;
            STDOUT.info("{}{}.Tytuł: {}{}{}{} \n", ConsoleColors.BLACK_BOLD, positionNumber, ConsoleColors.RESET, ConsoleColors.RED, books.get(i).getTitle(), ConsoleColors.RESET);
            STDOUT.info(" {} Autor: {}{}{}{} \n\n", ConsoleColors.BLACK_BOLD, ConsoleColors.RESET, ConsoleColors.BLUE, books.get(i).getAuthor(), ConsoleColors.RESET);
        }
        if (currentPageNumber == numberOfPages) {
            STDOUT.info("\n To ostatnia strona. Wybierz 2 aby zobaczyć poprzednią stronę lub 0 aby wyjść do głównego menu. \n\n");
        } else {
            STDOUT.info("\n Wybierz 1 aby zobaczyć następną stronę, 2 aby zobaczyć poprzednią lub 0 aby wyjść do głównego menu. \n\n");
        }
        STDOUT.info("\n{}Strona {} z {}.{}\n", ConsoleColors.BLACK_UNDERLINED, currentPageNumber, numberOfPages, ConsoleColors.RESET);
    }
}