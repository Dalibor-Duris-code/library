package com.example.library.views.user;

import com.example.library.entity.Book;
import com.example.library.services.BookService;
import com.example.library.views.AppLayoutBasic;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
    private TextField filterAuthor;
    private TextField filterDate;

    public UserView(BookService service) {

        this.bookService = service;

        HorizontalLayout filtersLayout = new HorizontalLayout();

        setGrid();
        setFilter();
        gridBook.asSingleSelect();

        filtersLayout.add(filterText,filterAuthor,filterDate);

        add(new H1("Ponuka kníh"),
                filtersLayout,
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

        filterAuthor = new TextField("Meno autora");
        filterAuthor.setPlaceholder("Zadajte meno..");
        filterAuthor.setClearButtonVisible(true);
        filterAuthor.setValueChangeMode(ValueChangeMode.LAZY);
        filterAuthor.addValueChangeListener(e -> gridBook.setItems(bookService.findByAuthorContainingIgnoreCase(filterAuthor.getValue())));

        filterDate = new TextField("Dátum vydania");
        filterDate.setPlaceholder("Zadajte dátum..");
        filterDate.setClearButtonVisible(true);
        filterDate.setValueChangeMode(ValueChangeMode.LAZY);
        filterDate.addValueChangeListener(e -> gridBook.setItems(bookService.findByDateReleaseContainingIgnoreCase(filterDate.getValue())));
    }
}
