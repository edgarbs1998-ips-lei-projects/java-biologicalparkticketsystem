package biologicalparkticketsystem.view;

import biologicalparkticketsystem.model.StatisticsModel;
import biologicalparkticketsystem.model.course.PointOfInterest;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * Statistics view class where the components of the statistics view get initialized
 */
public class StatisticsView implements IStatisticsView {
    
    // Scene
    private Scene scene;
    
    // Model
    private StatisticsModel statisticsModel;
    
    public StatisticsView(StatisticsModel model) {
        this.statisticsModel = model;
        
        TabPane tabPane = new TabPane();
        
        Tab soldTicketsTab = initSoldTicketsTab();
        tabPane.getTabs().add(soldTicketsTab);
        Tab topVisitedPoisTab = initTopVisitedPoisTab();
        tabPane.getTabs().add(topVisitedPoisTab);
        Tab othersTab = initOthersTab();
        tabPane.getTabs().add(othersTab);
        
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        this.scene = new Scene(tabPane, 600, 400);
    }

    private Tab initSoldTicketsTab() {
        Tab tab = new Tab();
        tab.setText("Sold Tickets");
        
        int soldBikeTickets = this.statisticsModel.getSoldBikeTickets();
        int soldFootTickets = this.statisticsModel.getSoldFootTickets();
        int totalSoldTickets = soldBikeTickets + soldFootTickets;
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Sold Bike Tickets", soldBikeTickets),
                new PieChart.Data("Sold Foot Tickets", soldFootTickets)
        );
        PieChart pieChart = new PieChart(pieChartData);
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName(), " [", (int)data.getPieValue(), " ticket(s), ", (int)(data.getPieValue() * 100 / totalSoldTickets), "%]")
                )
        );
        pieChart.setTitle("Sold Tickets");
        
        tab.setContent(pieChart);
        return tab;
    }
    
    private Tab initTopVisitedPoisTab() {
        Tab tab = new Tab();
        tab.setText("Top 10 Visited POIs");
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Top 10 Visited POIs");
        xAxis.setLabel("Points of Interest");       
        yAxis.setLabel("Visits");
        
        Map<PointOfInterest, Integer> top10VisitedPois = this.statisticsModel.getTop10VisitedPois();
        for (PointOfInterest poi : top10VisitedPois.keySet()) {
            XYChart.Series series1 = new XYChart.Series();
            series1.setName(poi.toString());
            series1.getData().add(new XYChart.Data("", top10VisitedPois.get(poi)));
            barChart.getData().addAll(series1);
        }
        
        tab.setContent(barChart);
        return tab;
    }
    
    private Tab initOthersTab() {
        Tab tab = new Tab();
        tab.setText("Others");
        
        VBox content = new VBox();
        content.setSpacing(10);
        
        HBox averageCostHBox = new HBox();
        Label averageCostLabel = new Label("Average Cost of Sold Tickets:");
        averageCostLabel.setStyle("-fx-font-weight: bold");
        Label averageCostLabelValue = new Label(this.statisticsModel.getSoldTicketsPriceAverage());
        averageCostHBox.getChildren().addAll(averageCostLabel, averageCostLabelValue);
        averageCostHBox.setPadding(new Insets(20));
        averageCostHBox.setSpacing(5);
        content.getChildren().add(averageCostHBox);
        
        tab.setContent(content);
        return tab;
    } 
    /**
     * Method to get the scene
     */
    @Override
    public Scene getScene() {
        return this.scene;
    }
    
}
