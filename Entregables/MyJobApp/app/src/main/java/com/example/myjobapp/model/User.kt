package model
import java.io.Serializable
//https://www.youtube.com/watch?v=3p_Nwn-oD4k&t=291s
//referencia para serializar
class User (

    var username:String = "MasterKotlin",
    var password:String="123",
    var firstname:String="David",
    var lastname:String="Barrientos",
    var address1: String = "San Rafael de Alajuela, Costa Rica",
    var address2: String = "San Rafael",
    var email: String = "ddavidb09@gmail.com",
    var phoneNumber: String = "6079-7171",
    var position: String = "Backend Junior",
    var gender:String="H",
    val skills: MutableList<String> = mutableListOf()
    ): Serializable {

    }



