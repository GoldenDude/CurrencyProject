package com.azranozeri.finalproject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class is responsible to get an XML file from Bank Of Israel, containing information about currency exchange rates.
 * Once the XML was retrieved, the class saves in to a local file, to be used in Offline Mode, in case there's no internet connection
 * or the BOI service is down.
 * The class parses the XML, that was received online or offline, into a List of Currencies, which then are send to the CurrencyGUI to be added to the table.
 * It holds a reference to the GUI in order to send data to it.
 * It implements Runnable and runs in the EDT Thread.
 * @see CurrencyGUI
 */
public class XMLParser implements Runnable {

    /**
     * a Reference to the CurrencyGUI.
     */
    private CurrencyGUI gui;

    /**
     * XMLParser Constructor.
     * gets a reference to CurrencyGUI in order to invoke methods and send data to it.
     * @param gui   a Reference to the client's CurrencyGUI
     */
    public XMLParser(CurrencyGUI gui){
        setGUI(gui);
    }

    public void run(){
        List<Currency> currList;
        currList = getCurrencyList(getNodeList());
        gui.createUI();
        gui.createList(currList);
    }

    /**
     * GUI Getter.
     * @return CurrencyGUI a reference to the GUI.
     * @see CurrencyGUI
     */
    CurrencyGUI getGui(){
        return gui;
    }

    /**
     * GUI Setter
     * @param gui a reference to the CurrencyGUI.
     * @see CurrencyGUI
     */
    public void setGUI(CurrencyGUI gui){
        this.gui = gui;
    }

    /**
     * This method gets XML data from an outer service, saves it to a local file and parses it into a list of Nodes.
     * If the connection fails, an exception will be thrown and caught, invoking offlineNodeList() method.
     * @return NodeList a list of nodes parsed from the XML file\XML query.
     */
    public NodeList getNodeList(){
        InputStream is = null;
        HttpsURLConnection con = null;
        NodeList list;

        /* Setting up a connection to the BOI Currency URL and parsing the XML file received */
        try {
            URL url = new URL("https://www.boi.org.il/currency.xml");
            con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            is = con.getInputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            File currencies = new File("Currencies.xml");
            transformer.transform(new DOMSource(doc), new StreamResult(currencies));
            gui.getDate().setText(doc.getElementsByTagName("LAST_UPDATE").item(0).getFirstChild().getNodeValue());
            list = doc.getElementsByTagName("CURRENCY");
        }

        catch(SAXException | ParserConfigurationException | IOException | TransformerException e){
            e.printStackTrace();
            list = offlineNodeList();
        }

        finally {
            if(is != null){
                try {
                    is.close();
                }

                catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(con!=null){
                con.disconnect();
            }
        }

        return list;
    }

    /**
     * This method gets XML data from a local file, and parses it into a list of Nodes.
     * @return NodeList a list of nodes parsed from the XML file\XML query.
     */
    public NodeList offlineNodeList(){
        File currencies = new File("Currencies.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        NodeList list;

        /* If exception will be thrown, the app will shut down as there is nothing to show/work with */
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(currencies);
            doc.getDocumentElement().normalize();
            list = doc.getElementsByTagName("CURRENCY");
            gui.getDate().setText(doc.getElementsByTagName("LAST_UPDATE").item(0).getFirstChild().getNodeValue());
        }

        catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            System.exit(-1);
            return null;
        }

        return list;
    }

    /**
     * This method makes a List from NodeList received.
     * The List is a list of Currencies.
     * @param   list    NodeList received from getNodeList() or offlineNodeList().
     * @return          a List of Currency, parsed from the NodeList.
     * @see     Currency
     */
    public List<Currency> getCurrencyList(NodeList list){
        int length = list.getLength();
        List<Currency> currList = new ArrayList<>();

        /* This loop goes through the NodeList and for each node, creates a Currency object that is inserted into the list */
        for(int i = 0; i < length; i++){
            String name = list.item(i).getChildNodes().item(1).getTextContent();
            int unit = Integer.parseInt(list.item(i).getChildNodes().item(3).getTextContent());
            String code = list.item(i).getChildNodes().item(5).getTextContent();
            String countryName = list.item(i).getChildNodes().item(7).getTextContent();
            double rate = Double.parseDouble(list.item(i).getChildNodes().item(9).getTextContent());
            double change = Double.parseDouble(list.item(i).getChildNodes().item(11).getTextContent());
            currList.add(new Currency(name, unit, code, countryName ,rate, change));
        }

        /* Creates and updates the Map */
        CurrencyMap currencyMap = new CurrencyMap();
        currencyMap.setMap(currList);
        gui.setCurrencyMap(currencyMap);
        return currList;
    }
}