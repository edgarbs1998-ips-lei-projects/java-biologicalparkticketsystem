package biologicalparkticketsystem.model;

import biologicalparkticketsystem.ConfigManager;
import biologicalparkticketsystem.DaoManager;
import biologicalparkticketsystem.LoggerManager;
import biologicalparkticketsystem.model.course.Connection;
import biologicalparkticketsystem.model.course.CourseManager;
import biologicalparkticketsystem.model.course.MapManager;
import biologicalparkticketsystem.model.course.MapManagerException;
import biologicalparkticketsystem.model.course.PointOfInterest;
import biologicalparkticketsystem.model.document.Client;
import biologicalparkticketsystem.model.document.DocumentManager;
import digraph.DiGraph;
import digraph.IVertex;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MainModel extends Observable {
    
    private List<PointOfInterest> mustVisitPois;
    private MapManager mapManager;
    private CourseManager courseManager;
    private DocumentManager documentManager;
    
    public MainModel() {
        mustVisitPois = new ArrayList<>();
        
        ConfigManager config = ConfigManager.getInstance();
        config.init();
        
        LoggerManager logger = LoggerManager.getInstance();
        logger.init();
        
        try {
            mapManager = new MapManager(config.getProperties().getProperty("map"));
        } catch (MapManagerException ex) {
            LoggerManager.getInstance().log(ex);
            return;
        }
        
        Client companyData = new Client(
                config.getProperties().get("company.name").toString(),
                config.getProperties().get("company.nif").toString()
        );
        Client.Address companyAddress = companyData.new Address(
                config.getProperties().get("company.address.adress").toString(),
                config.getProperties().get("company.address.postal_code").toString(),
                config.getProperties().get("company.address.location").toString(),
                config.getProperties().get("company.address.country").toString()
        );
        companyData.setAddress(companyAddress);
        
        DaoManager daoManager = DaoManager.getInstance();
        daoManager.init(config, mapManager);
        
        documentManager = new DocumentManager(
                config.getProperties().getProperty("documents.folder"),
                companyData,
                Double.parseDouble(config.getProperties().get("documents.vat").toString())
        );
        
        courseManager = new CourseManager(mapManager);
    }
    
    public DiGraph<PointOfInterest, Connection> getGraph() {
        return this.mapManager.getDiGraph();
    }
    
    public Iterable<IVertex<PointOfInterest>> getVertices() {
        return this.mapManager.getDiGraph().vertices();
    }
    
    public PointOfInterest getStartPointOfInterest() {
        return this.mapManager.getStartPoint();
    }
    
    public void addVisitPointOfInterest(PointOfInterest poi) {
        mustVisitPois.add(poi);
    }
    
    public void removeVisitPointOfInterest(PointOfInterest poi) {
        mustVisitPois.remove(poi);
    }
    
}
