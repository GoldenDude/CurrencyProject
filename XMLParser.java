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

public class XMLParser implements Runnable {
    private CurrencyGUI gui;

    public XMLParser(CurrencyGUI gui){
        setGUI(gui);
    }

    public void run(){
        List<Currency> currList;
        currList = getCurrencyList(getNodeList());
        gui.createUI();
        gui.createList(currList);
    }

    CurrencyGUI getGui(){
        return gui;
    }

    public void setGUI(CurrencyGUI gui){
        this.gui = gui;
    }

    public NodeList getNodeList(){
        InputStream is = null;
        HttpsURLConnection con = null;
        NodeList list;

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

    public NodeList offlineNodeList(){
        File currencies = new File("Currencies.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        NodeList list;

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

    public List<Currency> getCurrencyList(NodeList list){
        int length = list.getLength();
        List<Currency> currList = new ArrayList<>();

        for(int i = 0; i < length; i++){

            String name = list.item(i).getChildNodes().item(1).getTextContent();
            int unit = Integer.parseInt(list.item(i).getChildNodes().item(3).getTextContent());
            String code = list.item(i).getChildNodes().item(5).getTextContent();
            String countryName = list.item(i).getChildNodes().item(7).getTextContent();
            double rate = Double.parseDouble(list.item(i).getChildNodes().item(9).getTextContent());
            double change = Double.parseDouble(list.item(i).getChildNodes().item(11).getTextContent());
            currList.add(new Currency(name, unit, code, countryName ,rate, change));
        }

        CurrencyMap currencyMap = new CurrencyMap();
        currencyMap.setMap(currList);
        gui.setCurrencyMap(currencyMap);
        return currList;
    }
}