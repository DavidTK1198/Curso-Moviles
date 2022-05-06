package model
import java.io.Serializable
//https://www.youtube.com/watch?v=3p_Nwn-oD4k&t=291s
//referencia para serializar
class User (

    var username:String = "MasterKotlin",
    var password:String="123",
    var firstname:String="David",
    val lastname:String="Barrientos",
    var address1: String = "La Perla",
    var address2: String = "San Rafael",
    var city: String = "Alajuela",
    var state: String = "Alajuela",
    var code: String = "20108",
    var country: String = "Costa Rica",
    var email: String = "ddavidb09@gmail.com",
    var phoneNumber: String = "6079-7171",
    var area:String="506",
    var position: String = "Backend Junior",
    var startDate: String="01/01/2022",
    val skills: MutableList<String> = mutableListOf()
    ): Serializable {

    }


