/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publishsubscribe.Interfaces;

import publishsubscribe.Classes.Publication;

/**
 *
 * @author AP57630
 */
public interface IPublisher extends IClient{
    public void advertise(ITopic t, IPublication.Format format);
    public void publish(ITopic t, Publication p);

    public void unadvertise(ITopic t, IPublication.Format format);
}
