package publishsubscribe.Classes;

import kmoyenne.MapReduce;
import kmoyenne.MapResult;
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
    private MapResult content;

    public Publication(Topic topic, MapResult content) {
        this.topic = topic;
        this.content = content;
    }

    public Topic getTopic() {
        return this.topic;
    }

    public MapResult getContent() {
        return content;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setContent(MapResult content) {
        this.content = content;
    }
}
