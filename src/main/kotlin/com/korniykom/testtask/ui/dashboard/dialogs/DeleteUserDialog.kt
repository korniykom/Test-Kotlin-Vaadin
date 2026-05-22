package com.korniykom.testtask.ui.dashboard.dialogs

import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.p
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.korniykom.testtask.domain.models.User
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dialog.Dialog

class DeleteUserDialog(
    private val user: User,
    private val onConfirm: () -> Unit
) : Dialog() {

    init {
        setHeaderTitle("Delete User")

        verticalLayout {
            isPadding = false
            p("Are you sure you want to delete ${user.name}? This action cannot be undone.")
        }.also { add(it) }

        footer.add(
            button("Cancel") {
                addThemeVariants(ButtonVariant.LUMO_TERTIARY)
                addClickListener { close() }
            },
            button("Delete") {
                addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR)
                addClickListener {
                    onConfirm()
                    close()
                }
            }
        )
    }
}