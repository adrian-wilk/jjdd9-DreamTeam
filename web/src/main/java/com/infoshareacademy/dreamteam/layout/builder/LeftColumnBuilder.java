package com.infoshareacademy.dreamteam.layout.builder;

import java.util.HashMap;
import java.util.Map;

public class LeftColumnBuilder {

    public static Map<String, Object> build() {
        Map<String, Object> leftColumn = new HashMap<>();
        leftColumn.put("main_page", LeftColumnMenuList.MAIN_PAGE.getLabel());
        leftColumn.put("browse", LeftColumnMenuList.BROWSE.getLabel());
        leftColumn.put("search", LeftColumnMenuList.SEARCH.getLabel());
        leftColumn.put("genres", LeftColumnMenuList.GENRES.getLabel());
        leftColumn.put("favourites", LeftColumnMenuList.FAVOURITES.getLabel());
        leftColumn.put("reservations", LeftColumnMenuList.RESERVATIONS.getLabel());
        leftColumn.put("stats", LeftColumnMenuList.STATS.getLabel());
        leftColumn.put("manage", LeftColumnMenuList.MANAGE.getLabel());
        return leftColumn;
    }

}