package com.korniykom.testtask.ui.dashboard.dialogs

import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.p
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.korniykom.testtask.ui.dashboard.UserRow
import com.vaadin.flow.component.dialog.Dialog

class DeleteUserDialog(
    private val user: UserRow,
    private val onConfirm: () -> Unit
) : Dialog() {
    init {
        headerTitle = "Delete User"

        verticalLayout {
            p("Are you sure you want to delete ${user.name}?")
        }.also { add(it) }

        footer.add(
            button("Cancel") {
                addClickListener { close() }
            },
            button("Delete") {
                addClickListener {
                    onConfirm()
                    close()
                }
            }
        )
    }
}