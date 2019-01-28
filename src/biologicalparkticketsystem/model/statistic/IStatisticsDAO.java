package biologicalparkticketsystem.model.statistic;

import biologicalparkticketsystem.model.document.Ticket;
import biologicalparkticketsystem.model.course.CalculatedPath;
import biologicalparkticketsystem.model.course.PointOfInterest;
import java.util.Map;

/**
 * Interface to define statistics dao behavior
 */
public interface IStatisticsDAO {
    
    /**
     * Method to save a ticket instance with respective calculated path
     * @param ticket ticket instance
     * @param calculatedPath calculated path instance
     * @return
     */
    public boolean insertTicket(Ticket ticket, CalculatedPath calculatedPath);
    
    /**
     * Method to retrieve sold tickets for bike courses
     * @return sold bike tickets
     */
    public int getSoldBikeTickets();
    
    /**
     * Method to retrieve sold tickets for foot courses
     * @return sold foot tickets
     */
    public int getSoldFootTickets();
    
    /**
     * Method to retrieve sold tickets average price
     * @return sold tickets pirce average
     */
    public double getSoldTicketsPriceAverage();
    
    /**
     * Method to retrieve top ten visited pois
     * @return map of points of interest
     */
    public Map<PointOfInterest, Integer> getTop10VisitedPois();
    
}
