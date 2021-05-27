package it.unibs.ing.fp.rovineperdute;

import it.unibs.ing.fp.pathfinding.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.*;

/**
 * @author Thomas Causetti
 */
public class ReadXML {

    //========================================================================================================
    //Constant
    private static final String READ_DOC = " Start Read Doc ";
    public static final String ERROR_INIZ = "Error during reader initialization:";

    //========================================================================================================
    /**
     * Method that generate an instance of XMLStreamReader
     * @param file_name String
     * @return xmlr XMLStreamReader
     */
    public XMLStreamReader xmlStreamReaderGenerator(String file_name){
        XMLInputFactory xmlif;
        XMLStreamReader xmlr = null;
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(file_name, new FileInputStream(file_name));
        } catch (Exception e) {
            System.out.println(ERROR_INIZ);
            System.out.println(e.getMessage());
        }
        return xmlr;
    }

    //========================================================================================================

    /**
     * @author Thomas Causetti
     */
    public void readCities(ArrayList<City> cities, String filename) {

        XMLStreamReader xmlr=xmlStreamReaderGenerator(filename);
        try {
            while (xmlr.hasNext()) { // It continue to read until the last event (End of the file)

                //--- Start Switch --------------------------------------
                // switch sul tipo di evento
                switch (xmlr.getEventType()) {

                    // Start of the document
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println(READ_DOC + filename);
                        break;

                    // Start of the element
                    case XMLStreamConstants.START_ELEMENT:
                        //Attributes
                        //========================================================
                        ArrayList<String> read_att=new ArrayList<>();
                        for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                            if (xmlr.getLocalName().equals("city")) {
                                read_att.add(xmlr.getAttributeValue(i) + "");
                            }
                        }
                        //========================================================

                        if (read_att.size()!=0) {
                            //Add links
                            //========================================================

                            ArrayList<Link> read_link =new ArrayList<>();
                            continueToStart(xmlr);
                            while(xmlr.getLocalName().equals("link")) {

                                read_link.add(new Link(Integer.parseInt(xmlr.getAttributeValue(0))));
                                int check=continueToStart(xmlr);
                                if(check==-1){
                                    break;
                                }
                            }
                            cities.add(new City(Integer.parseInt(read_att.get(0)), read_att.get(1), (new Coordinates(Integer.parseInt(read_att.get(2)), Integer.parseInt(read_att.get(3)), Integer.parseInt(read_att.get(4)))), read_link));

                        }
                        break;

                    default:
                        break;
                }
                //--- End Switch --------------------------------------

                //Continue if xmlr.hasNext(), else not needed it will end anyway with the while cycle.
                if(xmlr.hasNext()) {
                    if(xmlr.getEventType()== XMLStreamConstants.START_ELEMENT) {
                        if (!xmlr.getLocalName().equals("city")) {
                            xmlr.next();
                        }
                    }
                    else {
                        xmlr.next();
                    }
                }
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }

    //========================================================================================================
    /**
     * Method that find the next getEventType() --> XMLStreamConstants.CHARACTERS
     * @param xmlr XMLStreamReader
     * @throws XMLStreamException throws exception
     */
    private int continueToStart(XMLStreamReader xmlr) throws XMLStreamException {
        do{
            if(xmlr.hasNext()) {
                xmlr.next();
            }
            else {
                return -1;
            }
        }while(xmlr.getEventType()!= XMLStreamConstants.START_ELEMENT);
        return 0;
    }
}

