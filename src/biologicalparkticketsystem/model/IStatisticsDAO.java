/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.model;

import java.util.Map;

/**
 *
 * @author goldspy98
 */
public interface IStatisticsDAO {
    
    public boolean insertTicket(Ticket ticket, CalculatedPath calculatedPath);
    public int getSoldBikeTickets();
    public int getSoldFootTickets();
    public double getSoldTicketsPriceAverage();
    public Map<PointOfInterest, Integer> getTop10VisitedPois();
    
}
