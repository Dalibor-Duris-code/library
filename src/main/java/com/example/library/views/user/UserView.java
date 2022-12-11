package com.example.library.views.user;

import com.example.library.views.AppLayoutBasic;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "UserView", layout = AppLayoutBasic.class)
@PageTitle("Uživatel")
public class UserView extends VerticalLayout {

    public UserView() {

        add(new H1("Toto je rozhranie užívatela"));
    }
}
