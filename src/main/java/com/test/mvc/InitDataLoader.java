package com.test.mvc;

import com.test.mvc.dao.BusDAO;
import com.test.mvc.dao.UserDAO;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rurik on 10/9/14.
 */
public class InitDataLoader {
    public static final String INITIAL_DATA_FILE = "initial_data.xml";

    public static final String BUS = "bus";
    public static final String NAME = "name";
    public static final String COST = "cost";
    public static final String USER = "user";
    public static final String LOGIN = "login";
    public static final String MONEY = "money";

    private BusDAO busDAO;
    private UserDAO userDAO;

    public void setBusDAO(BusDAO busDAO) {
        this.busDAO = busDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostConstruct
    public void initData() {

        if(busDAO.list().size()==0){
            ClassPathResource cpr = new ClassPathResource(INITIAL_DATA_FILE);
            try {
                InputStream is = cpr.getInputStream();
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);
                doc.getDocumentElement().normalize();

                NodeList busList = doc.getElementsByTagName(BUS);
                for (int temp = 0; temp < busList.getLength(); temp++) {
                    Node nNode = busList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String name = eElement.getElementsByTagName(NAME).item(0).getTextContent();
                        String cost = eElement.getElementsByTagName(COST).item(0).getTextContent();
                        busDAO.addNewBus(name, Integer.parseInt(cost));
                    }
                }

                NodeList userList = doc.getElementsByTagName(USER);
                for (int temp = 0; temp < userList.getLength(); temp++) {
                    Node nNode = userList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String login = eElement.getElementsByTagName(LOGIN).item(0).getTextContent();
                        String money = eElement.getElementsByTagName(MONEY).item(0).getTextContent();
                        userDAO.addUser(login, Integer.parseInt(money));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }

    }
}
