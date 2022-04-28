package com.example.nyc_school_test.common

class DataManager {
    val isStale: Boolean
    get() = verifyLastUpdate()

    private fun verifyLastUpdate(): Boolean {
        // todo
        // create the shared preference and check
        // last network update.
        return false
    }

    fun updateLastFetch() {
        //todo save current time in SP

    }
}