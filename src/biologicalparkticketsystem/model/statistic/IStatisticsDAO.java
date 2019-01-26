package biologicalparkticketsystem.model.statistic;

import biologicalparkticketsystem.model.document.Ticket;
import biologicalparkticketsystem.model.course.CalculatedPath;
import biologicalparkticketsystem.model.course.PointOfInterest;
import java.util.Map;

public interface IStatisticsDAO {
    
    public boolean insertTicket(Ticket ticket, CalculatedPath calculatedPath);
    public int getSoldBikeTickets();
    public int getSoldFootTickets();
    public double getSoldTicketsPriceAverage();
    public Map<PointOfInterest, Integer> getTop10VisitedPois();
    
}
