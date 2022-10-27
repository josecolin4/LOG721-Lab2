/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publishsubscribe.Interfaces;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author AP57630
 */
 

public interface ITopic extends Serializable {
    
public String getName();


    public List getPub();

    public List getSub();
    
}
