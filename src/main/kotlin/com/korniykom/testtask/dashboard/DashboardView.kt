package com.korniykom.testtask.dashboard

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import java.util.Locale.getDefault

@Route("dashboard")
@PageTitle("Dashboard")
class DashboardView : KComposite() {
    private val isAdmin = true

    private lateinit var nameFilter: TextField
    private lateinit var emailFilter: TextField
    private lateinit var userGrid: UserGrid

    private val root = ui {
        verticalLayout {
            setSizeFull()
            isPadding = true
            isSpacing = true

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

                        }
                    }
                }
            }

            userGrid = UserGrid(isAdmin).apply {
                setItems(sampleData())
            }
            add(userGrid)
        }
    }

    private fun filterGrid() {
        val name = nameFilter.value.trim().lowercase(getDefault())
        val email = emailFilter.value.trim().lowercase(getDefault())

        userGrid.setItems(sampleData().filter {
            it.name.lowercase(getDefault()).contains(name) && it.email.lowercase(getDefault()).contains(email)
        })
    }

    private fun sampleData() = listOf(
        UserRow(
            1,
            "Alice Smith",
            "alice@example.com",
            java.time.LocalDateTime.now().minusDays(10),
            java.time.LocalDateTime.now()
        ),
        UserRow(
            2,
            "Bob Jones",
            "bob@example.com",
            java.time.LocalDateTime.now().minusDays(5),
            java.time.LocalDateTime.now()
        ),
        UserRow(
            3,
            "Carol White",
            "carol@example.com",
            java.time.LocalDateTime.now().minusDays(1),
            java.time.LocalDateTime.now()
        ),
    )

}