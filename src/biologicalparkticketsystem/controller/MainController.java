package biologicalparkticketsystem.controller;

import biologicalparkticketsystem.model.MainModel;
import biologicalparkticketsystem.model.course.PointOfInterest;
import biologicalparkticketsystem.view.IMainView;
import digraph.IVertex;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

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
    
    public void changePointOfInterest(IVertex<PointOfInterest> poi, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            this.model.addVisitPointOfInterest(poi.element());
            this.view.setGraphVertexColor(poi, Color.RED, Color.BLACK);
        } else {
            this.model.removeVisitPointOfInterest(poi.element());
            this.view.setGraphVertexColor(poi, Color.ORANGE, Color.CORAL);
        }
    }
    
    public void issueTicket() {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Personal information");
//        alert.setHeaderText("We may need more information about you, before purchasing the ticket");
//        alert.setContentText("Do you want your invoice with NIF?");
//        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
//        alert.showAndWait().ifPresent(type -> {
//            if (type == ButtonType.YES) {
//                Optional<Client> result = new ClientDialog().showAndWait();
//
//                result.ifPresent(client -> {
//                    generateDocuments(client);
//                });
//            } else {
//                generateDocuments(null);
//            }
//        });
    }
    
    public void calculatePath() {
//        CourseManager.Criteria criteria = (CourseManager.Criteria) pathComboBox.getSelectionModel().getSelectedItem();
//            
//        boolean navigability = (!((RadioButton )group.getSelectedToggle()).getText().equals("Foot"));
//
//        try {
//            courseManager.minimumCriteriaPath(criteria, navigability, this.selectedPois);
//        } catch (CourseManagerException ex) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle ("An error has occured while calculating the course");
//            alert.setHeaderText(null);
//            alert.setContentText(ex.getMessage());
//            alert.show();
//
//            return;
//        }
//
//        resetColors();
//        updateColors();
//
//        payBtn.setDisable(false);
//        if (courseManager.countCalculatedCourses() > 0) {
//            undoBtn.setDisable(false);
//        }
//
//        updateCosts();
    }
    
    public void undoCalculate() {
//        courseManager.undoCalculatedCourse();
//        resetColors();
//        updateColors();
//        updateCosts();
//
//        if (courseManager.countCalculatedCourses() == 0) {
//            undoBtn.setDisable(true);
//        }
    }
    
}
