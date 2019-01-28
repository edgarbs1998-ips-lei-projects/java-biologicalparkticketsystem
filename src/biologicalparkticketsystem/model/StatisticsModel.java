package biologicalparkticketsystem.model;

import biologicalparkticketsystem.DaoManager;
import biologicalparkticketsystem.model.course.PointOfInterest;
import java.util.Map;

/**
 * Class responsable to control the data of statistics view
 */
public class StatisticsModel {
    
    /**
     * Method to return the sold tickets price average
     * @return price average
     */
    public String getSoldTicketsPriceAverage() {
        return String.format("€%.2f", DaoManager.getInstance().getStatisticsDao().getSoldTicketsPriceAverage());
    }
    
    /**
     * Method to return the top 10 visits pois
     * @return a map of type poi, visits
     */
    public Map<PointOfInterest, Integer> getTop10VisitedPois() {
        return DaoManager.getInstance().getStatisticsDao().getTop10VisitedPois();
    }
    
    /**
     * Method to return the sold bike tickets
     * @return sold bike tickets
     */
    public int getSoldBikeTickets() {
        return DaoManager.getInstance().getStatisticsDao().getSoldBikeTickets();
    }
    
    /**
     * Method to return the sold foot tickets
     * @return sold foor tickets
     */
    public int getSoldFootTickets() {
        return DaoManager.getInstance().getStatisticsDao().getSoldFootTickets();
    }
    
}
