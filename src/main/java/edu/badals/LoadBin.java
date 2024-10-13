package edu.badals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LoadBin {
    public static void main(String[] args) {
        Persona manu = new Persona("Manuel", 30);
        Persona luis = new Persona("Luis", 25);
        Persona maria = new Persona("Maria", 28);
        Persona ana = new Persona("Ana", 35);

        List<Persona> personas = new ArrayList<>();
        personas.add(manu);
        personas.add(luis);
        personas.add(maria);
        personas.add(ana);

        createBin(personas);
    }

    public static void createBin(List<Persona> personas) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/personas.bin"))){
            for (Persona persona : personas){
                oos.writeObject(persona);
            }
        }catch (IOException e){
            System.out.println("Error al guardar " + e.getMessage());
        }
    }
}
