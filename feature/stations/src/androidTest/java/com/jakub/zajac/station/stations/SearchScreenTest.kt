package com.jakub.zajac.station.stations

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.jakub.zajac.station.stations.presentation.search.SearchScreen
import com.jakub.zajac.station.stations.presentation.search.SearchState
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @JvmField
    @Rule
    val composeRule = createComposeRule()


    @Test
    fun showErrorMessageIfStateHasError() {
        val searchState = SearchState(

            errorMessage = "custom Error"
        )

        composeRule.setContent {
            SearchScreen(type = "source", state = searchState, event = {}, navigationEvent = {})
        }

        composeRule.onNodeWithTag("error message").assertIsDisplayed()
        composeRule.onNodeWithTag("refresh button").assertIsDisplayed()
    }

    @Test
    fun isLoadingDisplayed(){
        val searchState = SearchState(
            isLoading = true
        )
        composeRule.setContent {
            SearchScreen(type = "source", state = searchState, event = {}, navigationEvent = {})
        }


        composeRule.onNodeWithTag("loading progress").assertIsDisplayed()
    }
}