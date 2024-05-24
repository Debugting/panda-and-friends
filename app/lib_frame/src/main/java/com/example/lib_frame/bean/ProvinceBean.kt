package com.example.lib_frame.bean

import java.io.Serializable

class ProvinceBean : Serializable {
    var name: String? = null
    var city: List<CityDTO>? = null

    class CityDTO {
        var name: String? = null
        var area: List<String>? = null
    }
}
