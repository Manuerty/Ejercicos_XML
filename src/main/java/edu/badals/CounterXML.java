package edu.badals;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CounterXML {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un archivo xml: ");
        String archivo = sc.nextLine();
        if (archivo.endsWith(".xml")) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new File(archivo));
                NodeList nodeList = document.getChildNodes();
                Map<String, Integer> etiquetas = new HashMap<>();
                etiquetas = contarElementos(nodeList,etiquetas);
                System.out.println("El xml contiene: " + etiquetas.size() + " etiquetas diferentes.");
                printarEtiquetas(etiquetas);
            } catch (Exception e) {
                System.out.println("Error al leer xml" + e.getMessage());
            }
        }else{
            System.out.println("El archivo no es un xml");
        }
    }

    public static Map<String, Integer> contarElementos(NodeList nodeList, Map<String, Integer> etiquetas){
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                etiquetas.putIfAbsent(element.getTagName(), 0);
                etiquetas.computeIfPresent(element.getTagName(), (k, v) -> v + 1);
                if (element.hasChildNodes()){
                    NodeList elementChilds = element.getChildNodes();
                    contarElementos(elementChilds,etiquetas);
                }
            }
        }
        return etiquetas;
    }

    public static void printarEtiquetas(Map<String, Integer> etiquetas){
        for (Map.Entry<String, Integer> entry : etiquetas.entrySet()){
            System.out.println("La etiqueta " + entry.getKey() + " aparece " + entry.getValue() + " veces.");
        }
    }
}
