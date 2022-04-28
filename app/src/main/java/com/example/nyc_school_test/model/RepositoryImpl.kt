package com.example.nyc_schools_test.model

import com.example.nyc_school_test.common.DataManager
import com.example.nyc_school_test.model.local.NYCDao
import com.example.nyc_school_test.model.local.NycSchoolEntity
import com.example.nyc_schools_test.common.NetworkManager
import com.example.nyc_schools_test.model.remote.NycApi
import com.example.nyc_schools_test.model.remote.SchoolListResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(private val networkManager: NetworkManager,
                     private val nycDao: NYCDao,
                     private val dataManager: DataManager) :
    Repository {

    private val service = NycApi.initRetrofit()

    override fun useCaseSchoolList(): Flow<UIState> {

        /*
        * single source of truth
        * step offline check
        * if offline
        *   step check data from DB
        *   verify if local data is STALE
        *   if STALE
        *      show UIState.Error
        *   else
        *      show UIState.Response
        * else
        *     step check data from DB
        *     verify if local data is STALE
        *     if STALE
        *        fetch from remote
        *        update local
        *        show UIState.Response
        *     else
        *        show UIState.Response
         */
        return flow {
            emit(UIState.Loading(true))
            delay(500)

            if (networkManager.isConnected) {
                if (dataManager.isStale) {
                    val response = service.getSchoolList()
                    if (response.isSuccessful)
                        response.body()?.let {remote->
                            remote.map {
                                NycSchoolEntity(
                                    dbn = it.dbn,
                                    school_name = it.school_name,
                                    location = it.location,
                                    latitude = it.latitude,
                                    longitude = it.longitude
                                )
                            }.forEach { school ->
                                nycDao.update(
                                    school
                                )
                            }
                        }
                    emit(
                        UIState.ResponseListSchool(
                            nycDao.getListSchools().map {
                                SchoolListResponse(
                                    dbn = it.dbn,
                                    school_name = it.school_name,
                                    location = it.location,
                                    latitude = it.latitude,
                                    longitude = it.longitude
                                )
                            }
                        )
                    )
                    dataManager.updateLastFetch()
                } else {
                    emit(
                        UIState.ResponseListSchool(
                            nycDao.getListSchools().map {
                                SchoolListResponse(
                                    dbn = it.dbn,
                                    school_name = it.school_name,
                                    location = it.location,
                                    latitude = it.latitude,
                                    longitude = it.longitude
                                )
                            }
                        )
                    )

                }
            } else {
                if (dataManager.isStale) {
                    emit(UIState.Error("No internet connection"))
                } else {
                    emit(
                        UIState.ResponseListSchool(
                            nycDao.getListSchools().map {
                                SchoolListResponse(
                                    dbn = it.dbn,
                                    school_name = it.school_name,
                                    location = it.location,
                                    latitude = it.latitude,
                                    longitude = it.longitude
                                )
                            }
                        )
                    )

                }
            }

            /*val response = service.getSchoolList()
            emit(UIState.Loading())

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(
                        UIState.ResponseListSchool(it)
                    )
                } ?: emit(
                    UIState.Error(response.message())
                )
            } else {
                emit(
                    UIState.Error(response.message())
                )
            }*/
        }
    }

    override fun useCaseSchoolSatByDBN(dbn: String): Flow<UIState> {
        TODO("Not yet implemented")
    }
}