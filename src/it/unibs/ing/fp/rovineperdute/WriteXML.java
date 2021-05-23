package it.unibs.ing.fp.rovineperdute;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class WriteXML {

    public static final String FILE_NAME = "Routes.xml";

    /**
     * Method for the creation of the xml file
     * @author Rossi Mirko
     */

    //TODO finire

    public void writeXML(){

        //writing encoding and xml file version

        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;

        try{

            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(FILE_NAME), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");

        } catch (Exception e){

            System.out.println("Error: ");
            System.out.println(e.getMessage());

        }

        //writing information

        try {
            xmlw.writeStartElement("routes");

            //tag "routes" close
            xmlw.writeEndElement();




            //closing document
            xmlw.writeEndDocument();

            //eptying buffer and writing the document
            xmlw.flush();

            //closing document and resources used
            xmlw.close();

            //Crea lo stesso file ma indentato.
            try {
                indentaFile();
            }catch (Exception e){
                System.err.println(e);
            }

        }
        catch (Exception e){
            System.out.println("Writing error: ");
            System.out.println(e.getMessage());
        }
    }

    //TODO mettere in english
    public void indentaFile(){
        try {

            DocumentBuilderFactory dbFactory;
            DocumentBuilder dBuilder;
            Document original = null;

            try {
                dbFactory = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactory.newDocumentBuilder();

                //leggo il file originale
                original = dBuilder.parse(new InputSource(new InputStreamReader(new FileInputStream(FILE_NAME))));
            } catch (SAXException | IOException | ParserConfigurationException e) {
                e.printStackTrace();
            }

            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory tf = TransformerFactory.newInstance();

            //Transformer per indentazione
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            //Gli passo cio' che ho letto dal file originale
            transformer.transform(new DOMSource(original), xmlOutput);

            //Dopo formattazione scrivo su file
            FileWriter writer;
            writer=new FileWriter("RoutesFormattato.xml");

            BufferedWriter bufferedWriter;
            bufferedWriter=new BufferedWriter (writer);

            bufferedWriter.write(xmlOutput.getWriter().toString());

            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
