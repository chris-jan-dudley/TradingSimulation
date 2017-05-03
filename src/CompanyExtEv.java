/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tradingsimulation;

import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author sjb56
 */
public class CompanyExtEv extends ExternalEvent {
    
    /**
     *
     * @param eventsFile
     */
    public CompanyExtEv(ArrayList<String> eventsFile) throws ParseException {
        super(eventsFile);
    }
    
}
