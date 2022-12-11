package com.example.library.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = AppLayoutBasic.class)
@PageTitle("Vítejte")
public class MainView extends VerticalLayout {

    public MainView() {
        add(new H1("Vítejte na stránce knihovny!!"));
    }
}
