package it.unibs.ing.fp.rovineperdute;

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
    public void leggiPersone(ArrayList<City> cities, String filename) {

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
                        ArrayList<String> letto_att=new ArrayList<>();
                        for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                            letto_att.add(xmlr.getAttributeName(i)+"");
                        }
                        //========================================================

                        //Add links
                        //========================================================
                        ArrayList<String> letto=new ArrayList<>();
                        leggiOggettiXml(letto,xmlr, "city");
                        if(letto.size()!=0){

                            cities.add(new City(Integer.parseInt(letto_att.get(0)),letto_att.get(1),(new Coordinates(Integer.parseInt(letto_att.get(2)),Integer.parseInt(letto_att.get(3)),Integer.parseInt(letto_att.get(4))))));
                        }
                        break;

                    default:
                        break;
                }
                //--- End Switch --------------------------------------

                xmlr.next();
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
    private void continuaFinoCaratteri(XMLStreamReader xmlr) throws XMLStreamException {
        do{
            xmlr.next();
        }while(xmlr.getEventType()!= XMLStreamConstants.CHARACTERS);
    }


    //========================================================================================================
    /**
     * Metodo che ritorna un ArrayList di stringhe contenente i diversi attributi di un oggetto.
     * @param letto Arraylist dove vengono salvate le strighe (Passaggio per riferimento)
     * @param xmlr XMLStreamReader attuale
     * @param tag_input e' la stringa del nome del tag xml che contiene tutti gli attributi dell' oggetto (esempio: comune)
     */
    public void leggiOggettiXml(ArrayList<String> letto, XMLStreamReader xmlr, String tag_input){
        boolean fine;
        //solo se stringa
        if(xmlr.getLocalName().equals(tag_input)){
            fine=false;
            //va avanti finché non trova un area di testo
            do {
                //Continua a leggere finche' non trova un getEventType() == XMLStreamConstants.CHARACTERS
                try {
                    continuaFinoCaratteri(xmlr);
                } catch (XMLStreamException e) {
                    System.err.println(e.getMessage());
                }

                //Se ci sono caratteri li aggiunge ad array
                if(xmlr.getText().trim().length() > 0) {
                    letto.add(xmlr.getText());
                }

                //Avanza di uno (Passa a quello dopo CHARACTERS)
                try {
                    xmlr.next();
                } catch (XMLStreamException e) {
                    System.out.println(e.getMessage());
                }

                //Controlla se e' un END_ELEMENT
                if(xmlr.getEventType()==XMLStreamConstants.END_ELEMENT){

                    //Controlla se e' l'END_ELEMENT di tag_input
                    if(xmlr.getLocalName().equalsIgnoreCase(tag_input)) {
                        fine = true;
                    }
                }

            }while(!fine);

        }
    }
    //========================================================================================================
}

