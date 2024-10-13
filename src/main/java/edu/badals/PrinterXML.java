package edu.badals;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

public class PrinterXML {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la ruta de un archivo xml: ");
        String archivo = sc.nextLine();
        if (archivo.endsWith(".xml")) {
            try{
                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder Builder = Factory.newDocumentBuilder();
                Document doc = Builder.parse(new File(archivo));
                doc.getDocumentElement();
                NodeList nList = doc.getChildNodes();
                printNodes(nList, 0);
            } catch (Exception e) {
                System.out.println("Error al leer el XML" + e.getMessage());;
            }
        }else{
            System.out.println("La ruta no es un archivo XML");
        }
    }

        public static void printNodes(NodeList nodeList, int nivel){
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String indentacion = "\t".repeat(nivel);
                    System.out.print(indentacion + "<" + element.getNodeName() + ">");
                    if (element.hasChildNodes()) {
                        NodeList sons = element.getChildNodes();
                        boolean tieneSoloTexto = true;
                        for (int j = 0; j < sons.getLength(); j++) {
                            if (sons.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                tieneSoloTexto = false;
                                break;
                            }
                        }
                        if (tieneSoloTexto) {
                            System.out.print(element.getTextContent().trim());
                            System.out.println("</" + element.getNodeName() + ">");
                        } else {
                            System.out.println();
                            printNodes(sons, nivel + 1);
                            System.out.println(indentacion + "</" + element.getNodeName() + ">");
                        }
                    } else {
                        System.out.println("</" + element.getNodeName() + ">");
                    }
                }
            }
        }
}
