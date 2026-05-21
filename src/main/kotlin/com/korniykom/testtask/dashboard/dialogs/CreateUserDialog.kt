package com.korniykom.testtask.dashboard.dialogs

import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.textField
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.textfield.TextField

class CreateUserDialog(
    private val onSave: (name: String, email: String, password: String) -> Unit
) : Dialog() {
    private lateinit var nameField: TextField
    private lateinit var emailField: TextField
    private lateinit var passwordField: TextField

    init {
        headerTitle = "Create User"

        verticalLayout {
            isSpacing = true

            nameField = textField("Name")
            emailField = textField("Email")
            passwordField = textField("Password")
        }.also { add(it) }

        footer.add(
            button("Cancel") {
                addClickListener { close() }
            },
            button("Save") {
                addClickListener {
                    onSave(nameField.value, emailField.value, passwordField.value)
                    close()
                }
            }
        )
    }
}