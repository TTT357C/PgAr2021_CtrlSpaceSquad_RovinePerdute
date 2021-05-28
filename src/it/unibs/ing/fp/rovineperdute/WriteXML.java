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
     public void writeXML(Vehicle vehicle_a, Vehicle vehicle_b){

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


            xmlw.writeStartElement("route");
            xmlw.writeAttribute("team", vehicle_a.getTeam_name());
            xmlw.writeAttribute("cost", String.valueOf(vehicle_a.getFuel()));
            xmlw.writeAttribute("cities", String.valueOf(vehicle_a.getTouched_cities().size()));



            for(int i=0; i<vehicle_a.getTouched_cities().size(); i++){

                xmlw.writeStartElement("city");
                xmlw.writeAttribute("id", String.valueOf(vehicle_a.getTouched_cities().get(i).getId()));
                xmlw.writeAttribute("name", vehicle_a.getTouched_cities().get(i).getName());

                //tag "city" close
                xmlw.writeEndElement();
            }

            //tag "route" close
            xmlw.writeEndElement();


            xmlw.writeStartElement("route");
            xmlw.writeAttribute("team", vehicle_b.getTeam_name());
            xmlw.writeAttribute("cost", String.valueOf(vehicle_b.getFuel()));
            xmlw.writeAttribute("cities", String.valueOf(vehicle_b.getTouched_cities().size()));

            for(int i=0; i<vehicle_b.getTouched_cities().size(); i++){

                xmlw.writeStartElement("city");
                xmlw.writeAttribute("id", String.valueOf(vehicle_b.getTouched_cities().get(i).getId()));
                xmlw.writeAttribute("name", vehicle_b.getTouched_cities().get(i).getName());

                //tag "city" close
                xmlw.writeEndElement();
            }

            //tag "route" close
            xmlw.writeEndElement();

            //tag "routes" close
            xmlw.writeEndElement();

            //closing document
            xmlw.writeEndDocument();

            //eptying buffer and writing the document
            xmlw.flush();

            //closing document and resources used
            xmlw.close();

            //Creation of a clone-file but indented
            try {
                indentFile();
            }catch (Exception e){
                System.err.println(e);
            }

        }
        catch (Exception e){
            System.out.println("Writing error: ");
            System.out.println(e.getMessage());
        }
    }

    public void indentFile(){
        try {

            DocumentBuilderFactory dbFactory;
            DocumentBuilder dBuilder;
            Document original = null;

            try {
                dbFactory = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactory.newDocumentBuilder();

                //reading the original file
                original = dBuilder.parse(new InputSource(new InputStreamReader(new FileInputStream(FILE_NAME))));
            } catch (SAXException | IOException | ParserConfigurationException e) {
                e.printStackTrace();
            }

            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory tf = TransformerFactory.newInstance();

            //Transformer for indentation
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            //passing what is read from the original file
            transformer.transform(new DOMSource(original), xmlOutput);

            //After the formatting process, instructions for file writing
            FileWriter writer;
            writer=new FileWriter("RoutesFormatted.xml");

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
