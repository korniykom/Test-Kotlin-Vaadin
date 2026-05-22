package com.korniykom.testtask.ui.dashboard

import com.github.mvysny.karibudsl.v10.*
import com.korniykom.testtask.service.UserService
import com.korniykom.testtask.ui.dashboard.dialogs.CreateUserDialog
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.provider.DataProvider
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import org.springframework.data.domain.PageRequest
import java.util.Locale.getDefault

@Route("dashboard")
@PageTitle("Dashboard")
@AnonymousAllowed
class DashboardView(
    private val userService: UserService,
) : KComposite() {
    private val isAdmin = true

    private lateinit var nameFilter: TextField
    private lateinit var emailFilter: TextField
    private lateinit var userGrid: UserGrid

    private val root = ui {
        verticalLayout {
            setSizeFull()
            isPadding = true
            isSpacing = true

            style.set("padding-left", "12rem")
            style.set("padding-right", "12rem")
            style.set("padding-top", "8rem")
            style.set("padding-bottom", "4rem")

            horizontalLayout {
                setWidthFull()
                alignItems = FlexComponent.Alignment.BASELINE
                isSpacing = true

                nameFilter = textField("Search by name") {
                    isClearButtonVisible = true
                    addValueChangeListener {
                        println("filter triggered: ${it.value}")
                        filterGrid()
                    }
                }

                emailFilter = textField("Search by email") {
                    isClearButtonVisible = true
                    addValueChangeListener { filterGrid() }
                }

                val spacer = span()
                expand(spacer)

                if (isAdmin) {
                    button("Create User") {
                        addClickListener {
                            CreateUserDialog { name, email, password ->

                            }.open()
                        }
                    }
                }
            }

            userGrid = UserGrid(isAdmin).apply {
                dataProvider = createDataProvider()
            }
            add(userGrid)
        }
    }

    private fun filterGrid() {
        userGrid.dataProvider.refreshAll()
    }

    private fun createDataProvider(): DataProvider<UserRow, Void> {

        return DataProvider.fromCallbacks(
            { query ->

                val page = userService.getUsers(
                    nameFilter.value,
                    emailFilter.value,
                    PageRequest.of(query.page, query.pageSize)
                )

                page.content.map {
                    UserRow(
                        it.id,
                        it.name,
                        it.email,
                        it.createdAt,
                        it.updatedAt
                    )
                }.stream()
            },

            { _ ->
                userService.getUsers(
                    nameFilter.value,
                    emailFilter.value,
                    PageRequest.of(0, 20)
                ).totalElements.toInt()
            }
        )
    }

}