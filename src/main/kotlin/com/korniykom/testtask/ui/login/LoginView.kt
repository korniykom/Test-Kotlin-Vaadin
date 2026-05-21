package com.korniykom.testtask.ui.login

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.loginForm
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route("login")
@PageTitle("Login")
class LoginView : KComposite() {

    private val root = ui {
        verticalLayout {
            setSizeFull()
            alignItems = FlexComponent.Alignment.CENTER
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            loginForm()
        }
    }
}

