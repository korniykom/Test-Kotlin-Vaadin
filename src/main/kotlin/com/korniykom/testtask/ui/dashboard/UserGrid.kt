package com.korniykom.testtask.ui.dashboard

import com.github.mvysny.karibudsl.v10.button
import com.korniykom.testtask.domain.models.User
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import java.time.LocalDateTime

data class UserRow(
    val id: String,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

class UserGrid(
    isAdmin: Boolean,
    private val onEdit: ((User) -> Unit)? = null,
    private val onDelete: ((User) -> Unit)? = null,
) : Grid<User>(User::class.java, false) {

    init {
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS)
        setWidthFull()
        height = "100%"

        addColumn(User::name).setHeader("Name").setSortable(true).setKey("name").setAutoWidth(true)
        addColumn(User::email).setHeader("Email").setSortable(true).setKey("email").setAutoWidth(true)
        addColumn(User::createdAt).setHeader("Created At").setSortable(true).setKey("createdAt").setAutoWidth(true)
        addColumn(User::updatedAt).setHeader("Updated At").setSortable(true).setKey("updatedAt").setAutoWidth(true)

        if (isAdmin) {
            addComponentColumn { user ->
                HorizontalLayout().apply {
                    isSpacing = true
                    add(button("Edit") {
                        addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY)
                        addClickListener { onEdit?.invoke(user) }
                    })
                    add(button("Delete") {
                        addThemeVariants(
                            ButtonVariant.LUMO_SMALL,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY
                        )
                        addClickListener { onDelete?.invoke(user) }
                    })
                }
            }.setHeader("Actions").setAutoWidth(true)
        }
    }
}