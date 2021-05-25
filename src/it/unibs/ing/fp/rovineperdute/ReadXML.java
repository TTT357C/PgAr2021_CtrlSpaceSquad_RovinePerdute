package it.unibs.ing.fp.rovineperdute;

import it.unibs.ing.fp.pathfinding.Link;

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
    //Costanti
    private static final String READ_DOC = " Start Read Doc ";
    public static final String ERRORE_NELL_INIZ = "Errore nell' inizializzazione del reader:";

    //========================================================================================================
    /**
     * Metodo che genera un XMLStreamReader
     * @param nome_file Stringa contenente il nome del file
     * @return xmlr XMLStreamReader
     */
    public XMLStreamReader xmlStreamReaderGenerator(String nome_file){
        XMLInputFactory xmlif;
        XMLStreamReader xmlr = null;
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(nome_file, new FileInputStream(nome_file));
        } catch (Exception e) {
            System.out.println(ERRORE_NELL_INIZ);
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
            while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione

                //--- Inizio Switch --------------------------------------
                // switch sul tipo di evento
                switch (xmlr.getEventType()) {

                    // inizio del documento: stampa che inizia il documento
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println(READ_DOC + filename);
                        break;

                    // inizio di un elemento: stampa il nome del tag e i suoi attributi
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

                            ArrayList<Link> read_links=new ArrayList<>();
                            continueToStart(xmlr);
                            int cont=0;
                            while(xmlr.getLocalName().equals("link")) {
                                System.out.println(xmlr.getLocalName());
                                read_links.add(new Link(Double.parseDouble(xmlr.getAttributeValue(0))));
                                int check=continueToStart(xmlr);
                                if(check==-1){
                                    break;
                                }
                            }
                            cities.add(new City(Integer.parseInt(read_att.get(0)), read_att.get(1), (new Coordinates(Integer.parseInt(read_att.get(2)), Integer.parseInt(read_att.get(3)), Integer.parseInt(read_att.get(4)))),read_links));
                        }
                        break;

                    default:
                        break;
                }
                //--- End Switch --------------------------------------

                //Continue if xmlr.hasNext(), else not needed it will end anyway with the while cycle.
                if(xmlr.hasNext()) {
                    xmlr.next();
                }
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }

    //========================================================================================================
    /**
     * Metodo che continua a ciclare fino al prossimo getEventType() --> XMLStreamConstants.CHARACTERS
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

