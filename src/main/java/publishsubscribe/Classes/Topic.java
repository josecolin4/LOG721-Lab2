package publishsubscribe.Classes;

import publishsubscribe.Interfaces.ITopic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AP57630
 */
public class Topic implements ITopic {
    private String name;
    private List<Identity> pub, sub;

    public Topic(String name) {
        this.name = name;
        pub = new ArrayList<>();
        sub = new ArrayList<>();
    }

    public void addPublisher(Identity id) {
        pub.add(id);
    }

    public void addSubscriber(Identity id) {
        sub.add(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Topic topic = (Topic) o;

        return Objects.equals(name, topic.name);
    }

    public boolean isCompatible(String otherTopicName) {
        String topicName = this.getName();
        String[] topicElements = topicName.split("\\.");
        String[] otherTopicElements = otherTopicName.split("\\.");
        for (int i = 0; i < otherTopicElements.length; i++) {
            if (i >= topicElements.length) {
                return false;
            }
            if (otherTopicElements[i].equals("#")) {
                return true;
            }
            if (otherTopicElements[i].equals("*")) {
                continue;
            }

            if (!topicElements[i].equals(otherTopicElements[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Identity> getPub() {
        return pub;
    }

    @Override
    public List<Identity> getSub() {
        return sub;
    }

    public String toString() {
        return name + "pub : " + pub.toString() + " / sub : " + sub.toString();
    }
}
