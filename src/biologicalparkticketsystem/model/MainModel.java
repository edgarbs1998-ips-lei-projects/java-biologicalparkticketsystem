package biologicalparkticketsystem.model;

import biologicalparkticketsystem.ConfigManager;
import biologicalparkticketsystem.DaoManager;
import biologicalparkticketsystem.LoggerManager;
import biologicalparkticketsystem.model.course.Connection;
import biologicalparkticketsystem.model.course.CourseManager;
import biologicalparkticketsystem.model.course.CourseManagerException;
import biologicalparkticketsystem.model.course.ICriteriaStrategy;
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

/**
 * Class responsable to control the data of main view
 */
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
            mapManager = new MapManager();
            mapManager.loadMapFile(config.getProperties().getProperty("map"));
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
    
    /**
     * Method to return the mapmanager digraph instance
     * @return digraph instance
     */
    public DiGraph<PointOfInterest, Connection> getGraph() {
        return this.mapManager.getDiGraph();
    }
    
    /**
     * Method to return the mapmanager start vertex
     * @return vertex
     */
    public IVertex<PointOfInterest> getStartVertex() {
        return this.mapManager.getStartVertex();
    }
    
    /**
     * Method to return if has calculated courses to undo
     * @return true if has
     */
    public boolean hasUndoCalculatedCourse() {
        return this.courseManager.countCalculatedCourses() > 0;
    }
    
    /**
     * Method to add a point of interest to must visit list
     * @param poi point of interest to add
     */
    public void addVisitPointOfInterest(PointOfInterest poi) {
        mustVisitPois.add(poi);
    }
    
    /**
     * Method to remove a point of interest to must visit list
     * @param poi point of interest to remove
     */
    public void removeVisitPointOfInterest(PointOfInterest poi) {
        mustVisitPois.remove(poi);
    }
    
    /**
     * Method to calculate the path based on a crtieria and navigability
     * @param criteria criteria strategy
     * @param navigability true if on bike
     * @throws CourseManagerException
     */
    public void calculatePath(ICriteriaStrategy criteria, boolean navigability) throws CourseManagerException {
        this.courseManager.minimumCriteriaPath(criteria, navigability, this.mustVisitPois);
        this.setChanged();
        this.notifyObservers(this.courseManager.getCalculatedPath());
    }
    
    /**
     * Method to undo the calculated course
     */
    public void undoCalculatedCourse() {
        this.courseManager.undoCalculatedCourse();
        this.setChanged();
        this.notifyObservers(this.courseManager.getCalculatedPath());
    }
    
    /**
     * Method to generate the documents
     * @param client client isntance with data
     */
    public void generateDocuments(Client client) {
        this.documentManager.generateDocuments(this.courseManager.getCalculatedPath(), client);
    }
    
    /**
     * Method to clear calculated courses historic
     */
    public void clearCalculatedCourses() {
        this.courseManager.clearCalculatedCourses();
    }
    
}
