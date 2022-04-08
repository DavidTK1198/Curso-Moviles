package com.sistema.LogicaNegocio;

import java.util.HashMap;
import java.util.Map;


public class UsuarioModel {

    private static UsuarioModel uniqueInstance;
    
    public static UsuarioModel instance(){
        if (uniqueInstance == null){
            uniqueInstance = new UsuarioModel();
        }
        return uniqueInstance;
    }
    

    static Map<String,Usuario> usuarios;

    
    private UsuarioModel(){
  
        
        usuarios = new HashMap<>();
        usuarios.put("001", new Usuario("001","001","Juan Perez","ADM"));
        usuarios.put("002", new Usuario("002","002","Ana Arburola","CLI"));           
    }


    
    public static Usuario get(Usuario id)throws Exception{
        Usuario result = usuarios.get(id.getId());
        if (result==null) throw new Exception("Usuario no existe");
        return result;
    }      
    
}
