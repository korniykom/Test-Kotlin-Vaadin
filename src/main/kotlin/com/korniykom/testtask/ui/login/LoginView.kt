package com.korniykom.testtask.ui.login

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.loginForm
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
class LoginView : KComposite() {

    private val root = ui {
        verticalLayout {
            setSizeFull()
            alignItems = FlexComponent.Alignment.CENTER
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            loginForm() {
                action = "login"
                addLoginListener {
                    UI.getCurrent().navigate("dashboard")
                }
            }
        }
    }
}

