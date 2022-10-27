package publishsubscribe.Classes;

import publishsubscribe.Interfaces.IPublication;
import org.json.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AP57630
 */
public class Publication implements IPublication {

    private Topic topic;
    private String content;

    public Publication(Topic topic, String content) {
        this.topic = topic;
        this.content = content;
    }

    public Topic getTopic() {
        return this.topic;
    }

    public String getContent() {
        return content;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void fromXMLtoCanonical(Document xmlDocument) {
        xmlDocument.getDocumentElement().normalize();
        NodeList listPublication = xmlDocument.getElementsByTagName("message");
        Element elementPublication = (Element) listPublication.item(0);

        this.topic = new Topic(elementPublication.getElementsByTagName("topicname").item(0).getTextContent());
        this.content = elementPublication.getElementsByTagName("content").item(0).getTextContent();
    }

    @Override
    public Document fromCanonicaltoXML() {
        Document xmlFile = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            xmlFile = db.newDocument();

            Element publication = xmlFile.createElement("publication");
            xmlFile.appendChild(publication);

            Element message1 = xmlFile.createElement("message");
            publication.appendChild(message1);
            message1.setAttribute("messageno", "1");

            Element topicname1 = xmlFile.createElement("topicname");
            topicname1.appendChild(xmlFile.createTextNode(this.topic.getName()));
            message1.appendChild(topicname1);

            Element content1 = xmlFile.createElement("content");
            content1.appendChild(xmlFile.createTextNode(this.content));
            message1.appendChild(content1);

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return xmlFile;
    }

    @Override
    public JSONObject fromCanonicaltoJSON() {
        JSONObject jso = new JSONObject();
        JSONArray pubArr = new JSONArray();
        JSONObject messageObj = new JSONObject();
        messageObj.put("topic", this.topic.getName());
        messageObj.put("content", this.content);
        pubArr.put(messageObj);
        jso.put("Publication", pubArr);
        return jso;
    }

    public void fromJSONtoCanonical(JSONObject json) {

        JSONArray jsonArray = json.getJSONArray("Publication");
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonobject = jsonArray.getJSONObject(i);
            String content = jsonobject.get("content").toString();
            setTopic(new Topic(jsonobject.get("topic").toString()));
            setContent(content);
        }
    }

}
