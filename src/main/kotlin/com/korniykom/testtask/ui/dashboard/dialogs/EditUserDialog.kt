package com.korniykom.testtask.ui.dashboard.dialogs

import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.emailField
import com.github.mvysny.karibudsl.v10.textField
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.korniykom.testtask.domain.models.User
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.TextField

class EditUserDialog(
    private val user: User,
    private val onSave: (name: String, email: String) -> Unit
) : Dialog() {

    private lateinit var nameField: TextField
    private lateinit var emailField: EmailField

    init {
        setHeaderTitle("Edit User")

        verticalLayout {
            isPadding = false
            isSpacing = true
            nameField = textField("Name") { value = user.name }
            emailField = emailField("Email") { value = user.email }
        }.also { add(it) }

        footer.add(
            button("Cancel") {
                addThemeVariants(ButtonVariant.LUMO_TERTIARY)
                addClickListener { close() }
            },
            button("Save") {
                addThemeVariants(ButtonVariant.LUMO_PRIMARY)
                addClickListener {
                    onSave(nameField.value, emailField.value)
                    close()
                }
            }
        )
    }
}