/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publishsubscribe.Interfaces;

/**
 *
 * @author AP57630
 */
public interface ISubscriber extends IClient {
    public void subscribe(ITopic t);
    public void unsubscribe(ITopic t);
    public void listentoBroker();
}
