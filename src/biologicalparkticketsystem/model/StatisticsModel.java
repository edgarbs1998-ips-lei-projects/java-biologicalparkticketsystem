package biologicalparkticketsystem.model;

import biologicalparkticketsystem.DaoManager;
import biologicalparkticketsystem.model.course.PointOfInterest;
import java.util.Map;

public class StatisticsModel {
    
    public String getSoldTicketsPriceAverage() {
        return String.format("€%.2f", DaoManager.getInstance().getStatisticsDao().getSoldTicketsPriceAverage());
    }
    
    public Map<PointOfInterest, Integer> getTop10VisitedPois() {
        return DaoManager.getInstance().getStatisticsDao().getTop10VisitedPois();
    }
    
    public int getSoldBikeTickets() {
        return DaoManager.getInstance().getStatisticsDao().getSoldBikeTickets();
    }
    
    public int getSoldFootTickets() {
        return DaoManager.getInstance().getStatisticsDao().getSoldFootTickets();
    }
    
}
