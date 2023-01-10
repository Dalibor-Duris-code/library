package com.example.library.views.admin;

import com.example.library.entity.Book;
import com.example.library.services.BookService;
import com.example.library.views.AppLayoutBasic;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;

@PageTitle("Knihy")
@Route(value = "admin", layout = AppLayoutBasic.class)
public class AdminView extends VerticalLayout {

    private final GridCrud<Book> crud = new GridCrud<>(Book.class);

    private TextField filter;
    private TextField authorFilter;
    private TextField dateFilter;
    private final BookService bookService;

    public AdminView(BookService bookService)
    {
        this.bookService = bookService;

        //filter.setPlaceholder("Filter by book");
        //filter.setClearButtonVisible(true);
        //crud.getCrudLayout().addToolbarComponent(filter);

        this.setSizeFull();

        setupFilters();
        setupCrud();
        setCrudOperations();

        crud.getCrudLayout().addFilterComponents(filter, authorFilter, dateFilter);

        add(new H1("Správa kníh"),
                crud);

    }

    public void setCrudOperations()
    {
        crud.setFindAllOperationVisible(false);

        crud.setOperations(
                () -> bookService.findByNameContainingIgnoreCase(filter.getValue()),
                bookService::add,
                bookService::update,
                bookService::delete
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
    }

    public void setupCrud()
    {
        crud.getGrid().removeAllColumns();
        crud.getGrid().addColumn("bookId").setHeader("IdKnihy");
        crud.getGrid().addColumn("name").setHeader("Názov");
        crud.getGrid().addColumn("author").setHeader("Autor");
        crud.getGrid().addColumn("dateRelease").setHeader("Dátum vydania");
        crud.getGrid().addComponentColumn(this::getThumbnail).setHeader("Obal");

        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("name","author","dateRelease","pageNumber","image");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "name","author","dateRelease","pageNumber","image");
        crud.setSizeFull();
    }

    public void setupFilters()
    {
        filter = new TextField();
        filter.setPlaceholder("Názov knihy..");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> crud.getGrid().setItems(bookService.findByNameContainingIgnoreCase(filter.getValue())));

        authorFilter = new TextField();
        authorFilter.setPlaceholder("Názov autora..");
        authorFilter.setClearButtonVisible(true);
        authorFilter.setValueChangeMode(ValueChangeMode.LAZY);
        authorFilter.addValueChangeListener(e -> crud.getGrid().setItems(bookService.findByAuthorContainingIgnoreCase(authorFilter.getValue())));

        dateFilter = new TextField();
        dateFilter.setPlaceholder("Dátum vydania..");
        dateFilter.setClearButtonVisible(true);
        dateFilter.setValueChangeMode(ValueChangeMode.LAZY);
        dateFilter.addValueChangeListener(e -> crud.getGrid().setItems(bookService.findByDateReleaseContainingIgnoreCase(dateFilter.getValue())));
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
}
