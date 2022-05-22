package com.example.uiexamples

class Users private constructor() {

    private var users: ArrayList<User> = ArrayList<User>()

    init{
        addPersona(User("mar", "123","Marcos", R.drawable.foto01,"Standard"))
        addPersona(User("jul", "123", "Julia", R.drawable.foto02,"Administrador"))

    }

    private object HOLDER {
        val INSTANCE = Users()
    }

    companion object {
        val instance: Users by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addPersona(user: User){
        users?.add(user)
    }

    fun getPersona(nombre: String): User? {
        for (p: User in users!!){
            if(p.nombre.equals(nombre)){
                return p;
            }
        }
        return null;
    }

    fun getPersonas(): ArrayList<User>{
        return this.users!!
    }

    fun login(user: String?, password: String?): Boolean{
        for(p: User in users!!){
            if(p.user.equals(user) && p.password.equals(password)){
                return true
            }
        }
        return false
    }

    fun loginP(user: String?, password: String?): User?{
        for(p: User in users!!){
            if(p.user.equals(user) && p.password.equals(password)){
                return p
            }
        }
        return null
    }

    fun deletePerson(position: Int){
        users!!.removeAt(position)
    }

    fun editPerson(p: User, position: Int){
        var aux = users!!.get(position)
        aux.password = p.password
        aux.nombre = p.nombre
        aux.user = p.user
    }
}