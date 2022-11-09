/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publishsubscribe.Interfaces;

import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.Serializable;

/**
 *
 * @author AP57630
 */
public interface IPublication extends Serializable {
    enum Format{
        XML, JSON;
    }
}
