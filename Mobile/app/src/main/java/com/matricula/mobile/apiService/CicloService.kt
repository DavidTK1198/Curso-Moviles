package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder

interface CicloService {
    companion object {
        private var cicloService : CicloService? = null
        fun getInstance() : CicloService {
            if (cicloService == null) {
                cicloService = ApiBuilder.buildService(CicloService::class.java)
            }
            return cicloService!!
        }
    }
}