package com.korniykom.testtask.ui.dashboard

import com.github.mvysny.karibudsl.v10.button
import com.korniykom.testtask.ui.dashboard.dialogs.DeleteUserDialog
import com.korniykom.testtask.ui.dashboard.dialogs.EditUserDialog
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import java.time.LocalDateTime

data class UserRow(
    val id: String,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)


class UserGrid(isAdmin: Boolean):  Grid<UserRow>(UserRow::class.java, false) {

    init {
        setWidthFull()
        addColumn(UserRow::name).setHeader("Name").setSortable(true).setAutoWidth(true)
        addColumn(UserRow::email).setHeader("Email").setSortable(true).setAutoWidth(true)
        addColumn(UserRow::createdAt).setHeader("Created at").setSortable(true).setAutoWidth(true)
        addColumn(UserRow::updatedAt).setHeader("Updated at").setSortable(true).setAutoWidth(true)

        if(isAdmin) {
            addComponentColumn { user ->
                HorizontalLayout().apply {
                    isSpacing = true
                    add(button("Edit") {
                        addClickListener {
                            EditUserDialog(user){ name, email ->

                            }.open()
                        }
                    })
                    add(button("Delete") {
                        addClickListener {
                            DeleteUserDialog(user) {

                            }.open()
                        }
                    })
                }
            }.setHeader("Actions").setAutoWidth(true)
        }
    }
}