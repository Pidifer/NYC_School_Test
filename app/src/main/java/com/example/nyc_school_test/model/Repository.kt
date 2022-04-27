package com.example.nyc_schools_test.model

import com.example.nyc_schools_test.model.remote.SchoolListResponse
import com.example.nyc_schools_test.model.remote.SchoolSatResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun useCaseSchoolList():Flow<UIState>
    fun useCaseSchoolSatByDBN(dbn:String):Flow<UIState>
}
