package com.example.library.views.admin;

import com.example.library.entity.Book;
import com.example.library.services.BookService;
import com.example.library.views.AppLayoutBasic;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;

@PageTitle("Knihy")
@Route(value = "admin", layout = AppLayoutBasic.class)
public class AdminView extends VerticalLayout {

    private final GridCrud<Book> crud = new GridCrud<>(Book.class);

    private TextField filter = new TextField();
    private final BookService bookService;

    public AdminView(BookService bookService)
    {
        this.bookService = bookService;

        filter.setPlaceholder("Filter by book");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        setupCrud();
        setCrudOperations();
        add(crud);

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
        crud.getGrid().addColumns("bookId","name","author","dateRelease","pageNumber","image");

        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("name","author","dateRelease","pageNumber","image");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "name","author","dateRelease","pageNumber","image");
        crud.setSizeFull();

    }
}
