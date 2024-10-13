package edu.badals;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class BinToXML {

    private static final String BIN_FILE = "src/main/resources/personas.bin";
    private static final String XML_FILE = "src/main/resources/personas.xml";

    public static void main(String[] args) {
        List<Persona> personas = readBin();
        createXML(personas);
        List<Persona> printPersonas = readXML();
        for (Persona persona : printPersonas) {
            System.out.println(persona);
            }
    }

    public static List<Persona> readBin() {
        List<Persona> personas = new ArrayList<Persona>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BIN_FILE))) {
            while (true) {
                Persona persona = (Persona) ois.readObject();
                personas.add(persona);
            }
        } catch (EOFException e) {
            System.out.println("Fin de fichero");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer " + e.getMessage());
        }

        return personas;
    }

    public static void createXML(List<Persona> personas){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "personas", null);
            document.setXmlVersion("1.0");
            Element root = document.getDocumentElement();

            for (Persona persona : personas) {
                Element ElementPersona = document.createElement("persona");
                root.appendChild(ElementPersona);

                Element name = document.createElement("nombre");
                ElementPersona.appendChild(name);
                Text nameText = document.createTextNode(persona.getNombre());
                name.appendChild(nameText);

                Element age = document.createElement("edad");
                ElementPersona.appendChild(age);

                Text ageText = document.createTextNode(String.valueOf(persona.getEdad()));
                age.appendChild(ageText);

            }
            Source source = new DOMSource(document);
            Result resultado = new StreamResult(new File(XML_FILE));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, resultado);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static  List<Persona> readXML(){
        List<Persona> personas = new ArrayList<>();
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(XML_FILE));
            document.getDocumentElement();

            NodeList nodesPersona = document.getElementsByTagName("persona");

            for(int i = 0; i < nodesPersona.getLength(); i++){
                Element elementPersona = (Element) nodesPersona.item(i);
                Element elementName = (Element) elementPersona.getElementsByTagName("nombre").item(0);
                String name = elementName.getTextContent();
                Element elementAge = (Element) elementPersona.getElementsByTagName("edad").item(0);
                String age = elementAge.getTextContent();
                personas.add(new Persona(name, Integer.parseInt(age)));
            }


        } catch (Exception e) {
            System.out.println("No se puede leer el archivo XML " + e.getMessage());
        }
        return personas;
    }

}
