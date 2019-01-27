package biologicalparkticketsystem.controller;

import biologicalparkticketsystem.model.MainModel;
import biologicalparkticketsystem.model.course.CourseManager;
import biologicalparkticketsystem.model.course.CourseManagerException;
import biologicalparkticketsystem.model.course.PointOfInterest;
import biologicalparkticketsystem.model.document.Client;
import biologicalparkticketsystem.view.ClientDialog;
import biologicalparkticketsystem.view.IMainView;
import digraph.IVertex;
import javafx.scene.control.ButtonType;

public class MainController {
    
    private MainModel model;
    private IMainView view;
    
    public MainController(MainModel model, IMainView view) {
        this.model = model;
        this.view = view;
        this.view.setTriggers(this);
        this.model.addObserver(view);
    }
    
    public void openStatistics() {
//        LoggerManager.getInstance().log(LoggerManager.Component.STATISTICS_CHECKS);
//            
//        StatisticsUI statisticsWindow = new StatisticsUI();
//        Stage statisticsStage = new Stage();
//        statisticsStage.setTitle("Statistics");
//        statisticsStage.setScene(new Scene(statisticsWindow, 600, 400));
//        statisticsStage.show();
    }
    
    public void changePointOfInterest(IVertex<PointOfInterest> poi, boolean oldValue, boolean newValue) {
        if (newValue) {
            this.model.addVisitPointOfInterest(poi.element());
            this.view.markPoiToVisit(poi);
        } else {
            this.model.removeVisitPointOfInterest(poi.element());
            this.view.unmarkPoiToVisit(poi);
        }
    }
    
    public void issueTicket() {
        this.view.showNifQuestionDialog(this);
    }
    
    public void nifDialogResponse(ButtonType type) {
        if (type == ButtonType.YES) {
            new ClientDialog().showAndWait().ifPresent(client -> {
                this.generateDocuments(client);
            });
        } else {
            this.generateDocuments(null);
        }
    }
    
    private void generateDocuments(Client client) {
        this.model.generateDocuments(client);
        this.view.showSuccess("Your ticket has been issued!");
        
        this.model.clearCalculatedCourses();
        this.view.resetInput();
    }
    
    public void calculatePath() {
        try {
            CourseManager.Criteria criteria = this.view.getCriteriaComboBox();
            boolean navigability = this.view.getNavigability();
            this.model.calculatePath(criteria, navigability);
        } catch (CourseManagerException ex) {
            this.view.showError(ex.getMessage());
        }
    }
    
    public void undoCalculate() {
        this.model.undoCalculatedCourse();
    }
    
}
