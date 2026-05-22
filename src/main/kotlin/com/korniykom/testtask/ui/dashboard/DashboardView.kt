package com.korniykom.testtask.ui.dashboard

import com.github.mvysny.karibudsl.v10.*
import com.korniykom.testtask.domain.models.Role
import com.korniykom.testtask.domain.models.User
import com.korniykom.testtask.service.UserService
import com.korniykom.testtask.ui.dashboard.dialogs.CreateUserDialog
import com.korniykom.testtask.ui.dashboard.dialogs.DeleteUserDialog
import com.korniykom.testtask.ui.dashboard.dialogs.EditUserDialog
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.provider.CallbackDataProvider
import com.vaadin.flow.data.provider.DataProvider
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import jakarta.annotation.security.PermitAll
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder

@Route("")
@PageTitle("Dashboard")
@PermitAll
class DashboardView(
    private val userService: UserService,
) : KComposite() {

    private val isAdmin: Boolean = run {
        val name = SecurityContextHolder.getContext().authentication?.name
        if (name != null) {
            userService.findByName(name)?.role == Role.ADMIN
        } else false
    }

    private lateinit var nameFilter: TextField
    private lateinit var emailFilter: TextField
    private lateinit var userGrid: UserGrid

    private lateinit var dataProvider: CallbackDataProvider<User, Void>


    private val root = ui {
        verticalLayout {
            setSizeFull()
            isPadding = true
            isSpacing = true
            style.set("padding-left", "2rem")
            style.set("padding-right", "2rem")

            horizontalLayout {
                setWidthFull()
                alignItems = FlexComponent.Alignment.BASELINE
                isSpacing = true

                nameFilter = textField("Search by name") {
                    isClearButtonVisible = true
                    addValueChangeListener { refreshGrid() }
                }

                emailFilter = textField("Search by email") {
                    isClearButtonVisible = true
                    addValueChangeListener { refreshGrid() }
                }

                val spacer = span()
                expand(spacer)

                if (isAdmin) {
                    button("Create User") {
                        addThemeVariants(ButtonVariant.LUMO_PRIMARY)
                        addClickListener {
                            CreateUserDialog { name, email, password ->
                                try {
                                    userService.createUser(name, email, password)
                                    refreshGrid()
                                    Notification.show("User created")
                                } catch (e: Exception) {
                                    Notification.show("Error: ${e.message}")
                                }
                            }.open()
                        }
                    }
                }
            }

            userGrid = UserGrid(
                isAdmin,
                onEdit = { user ->
                    EditUserDialog(user) { name, email ->
                        try {
                            userService.updateUser(user.id!!, name, email)
                            refreshGrid()
                            Notification.show("User updated")
                        } catch (e: Exception) {
                            Notification.show("Error: ${e.message}")
                        }
                    }.open()
                },
                onDelete = { user ->
                    DeleteUserDialog(user) {
                        try {
                            userService.deleteUser(user.id!!)
                            refreshGrid()
                            Notification.show("User deleted")
                        } catch (e: Exception) {
                            Notification.show("Error: ${e.message}")
                        }
                    }.open()
                }
            )
            add(userGrid)
        }
    }

    init {
        dataProvider = buildDataProvider()
        userGrid.dataProvider = dataProvider
    }

    private fun refreshGrid() {
        dataProvider.refreshAll()
    }

    private fun buildDataProvider(): CallbackDataProvider<User, Void> {
        return DataProvider.fromCallbacks(
            { query ->
                val name = nameFilter.value.trim()
                val email = emailFilter.value.trim()
                val sort = if (query.sortOrders.isNotEmpty()) {
                    val order = query.sortOrders.first()
                    Sort.by(
                        if (order.direction == com.vaadin.flow.data.provider.SortDirection.ASCENDING)
                            Sort.Direction.ASC else Sort.Direction.DESC,
                        order.sorted
                    )
                } else {
                    Sort.by(Sort.Direction.ASC, "name")
                }
                val page = PageRequest.of(query.offset / query.limit, query.limit, sort)
                userService.getUsers(name.ifBlank { null }, email.ifBlank { null }, page)
                    .content.stream()
            },
            { _ ->
                val name = nameFilter.value.trim()
                val email = emailFilter.value.trim()
                userService.getUsers(name.ifBlank { null }, email.ifBlank { null }, PageRequest.of(0, 1))
                    .totalElements.toInt()
            }
        )
    }
}