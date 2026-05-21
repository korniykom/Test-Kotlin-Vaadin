package com.korniykom.testtask.dashboard.dialogs

import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.textField
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.korniykom.testtask.dashboard.UserRow
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.textfield.TextField

class EditUserDialog(
    private val user: UserRow,
    private val onSave: (name: String, email: String) -> Unit
) : Dialog() {

    private lateinit var nameField: TextField
    private lateinit var emailField: TextField

    init {
        headerTitle = "Edit User"

        verticalLayout {
            isSpacing = true

            nameField = textField("Name") { value = user.name }
            emailField = textField("Email") { value = user.email }
        }.also { add(it) }

        footer.add(
            button("Cancel") {
                addClickListener { close() }
            },
            button("Save") {
                addClickListener {
                    onSave(nameField.value, emailField.value)
                    close()
                }
            }
        )
    }
}