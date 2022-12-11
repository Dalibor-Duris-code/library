package com.example.library.views.user;

import com.example.library.entity.Book;
import com.example.library.services.BookService;
import com.example.library.views.AppLayoutBasic;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "UserView", layout = AppLayoutBasic.class)
@PageTitle("Uživatel")
public class UserView extends VerticalLayout {

    private final Grid<Book> gridBook = new Grid<>(Book.class,false);
    private final BookService bookService;
    private TextField filterText;
    public UserView(BookService service) {

        this.bookService = service;

        setGrid();
        setFilter();
        gridBook.asSingleSelect();

        add(new H1("Ponuka kníh"),
                filterText,
                gridBook
        );
    }

    private void setGrid()
    {
        gridBook.addColumn(Book::getName).setHeader("Názov");
        gridBook.addColumn(Book::getAuthor).setHeader("Autor");
        gridBook.addColumn(Book::getDateRelease).setHeader("Dátum vydania");
        gridBook.addColumn(Book::getPageNumber).setHeader("Počet strán");
        gridBook.addColumn(Book::getImage).setHeader("Obal");

        gridBook.setItems(bookService.findAll());
    }

    private void setFilter()
    {
        filterText = new TextField("Názov knihy");
        filterText.setPlaceholder("Zadajte názov..");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> gridBook.setItems(bookService.findByNameContainingIgnoreCase(filterText.getValue())));
    }
}
