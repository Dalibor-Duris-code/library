package com.example.library.views.user;

import com.example.library.entity.Book;
import com.example.library.services.BookService;
import com.example.library.views.AppLayoutBasic;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
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
        this.setSizeFull();

        HorizontalLayout filtersLayout = new HorizontalLayout();

        setupGrid();
        setupFilters();
        filtersLayout.add(filterText,filterAuthor,filterDate);

        add(new H3("Ponuka kníh"),
                filtersLayout,
                gridBook
        );
    }

    private void setupGrid()
    {
        gridBook.setSelectionMode(Grid.SelectionMode.MULTI);

        gridBook.addComponentColumn(this::getThumbnail).setHeader("Obal");
        gridBook.addColumn(Book::getName).setHeader("Názov");
        gridBook.addColumn(Book::getAuthor).setHeader("Autor");
        gridBook.addColumn(Book::getDateRelease).setHeader("Dátum vydania");
        gridBook.addColumn(Book::getPageNumber).setHeader("Počet strán");

        gridBook.setItems(bookService.findAll());
    }

    private Image getThumbnail(Book book)
    {
        var image = new Image(book.getImage(), book.getName() +"Cover");
        image.setHeight("70px");
        image.addClickListener(e -> showCover(book));
        return image;
    }

    private void showCover(Book book)
    {
        var image = new Image(book.getImage() ,"Cover");
        image.setSizeFull();

        var dialog = new Dialog(image);
        dialog.setHeight("90%");
        dialog.open();
    }

    private void setupFilters()
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
