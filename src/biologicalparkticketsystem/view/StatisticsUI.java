/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.view;

import biologicalparkticketsystem.controller.DaoManager;
import biologicalparkticketsystem.model.PointOfInterest;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 *
 * @author Luis Varela
 */
public class StatisticsUI extends TabPane {
    
    final private PieChart chart;
    private int totalSoldTickets = 0;
    
    public StatisticsUI(){
        
        int soldBikeTickets = DaoManager.getInstance().getStatisticsDao().getSoldBikeTickets();
        int soldFootTickets = DaoManager.getInstance().getStatisticsDao().getSoldFootTickets();
        totalSoldTickets += soldBikeTickets;
        totalSoldTickets += soldFootTickets;
        
        //get data from controller
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Sold Bike Tickets", soldBikeTickets),
                new PieChart.Data("Sold Foot Tickets", soldFootTickets)
        );
        chart = new PieChart(pieChartData) {
            @Override
            protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
                if (getLabelsVisible()) {
                    getData().forEach(d -> {
                        Optional<Node> opTextNode = chart.lookupAll(".chart-pie-label").stream().filter(n -> n instanceof Text && ((Text) n).getText().contains(d.getName())).findAny();
                        if (opTextNode.isPresent()) {
                            ((Text) opTextNode.get()).setText(d.getName() + " [" + (int)d.getPieValue() + " ticket(s), " + (int)(d.getPieValue() * 100 / totalSoldTickets) + "%]");
                        }
                    });
                }
                super.layoutChartChildren(top, left, contentWidth, contentHeight);
            }
        };
        chart.setTitle("Sold Tickets");
        
        Tab tabChart = new Tab();
        tabChart.setText("Sold Tickets");
        tabChart.setContent(chart);
        
        Tab tabBarChart = new Tab();
        tabBarChart.setText("Top 10 Visited POIs");
         
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
        bc.setTitle("Top 10 Visited POIs");
        xAxis.setLabel("Points of Interest");       
        yAxis.setLabel("Visits");
        
        Map<PointOfInterest, Integer> top10VisitedPois = DaoManager.getInstance().getStatisticsDao().getTop10VisitedPois();
        for (PointOfInterest poi : top10VisitedPois.keySet()) {
            XYChart.Series series1 = new XYChart.Series();
            series1.setName(poi.toString());
            series1.getData().add(new XYChart.Data("", top10VisitedPois.get(poi)));
            bc.getData().addAll(series1);
        }
        
        tabBarChart.setContent(bc);
        
        Tab tabOthers = new Tab();
        tabOthers.setText("Others");
        
        HBox averageCostHBox = new HBox();
        Label averageCostLabel = new Label("Average Cost of Sold Tickets:");
        averageCostLabel.setStyle("-fx-font-weight: bold");
        Label averageCostLabelValue = new Label("€" + Double.toString(DaoManager.getInstance().getStatisticsDao().getSoldTicketsPriceAverage()));
        averageCostHBox.getChildren().addAll(averageCostLabel, averageCostLabelValue);
        averageCostHBox.setAlignment(Pos.TOP_LEFT);
        averageCostHBox.setPadding(new Insets(20));
        averageCostHBox.setSpacing(5);
        
        tabOthers.setContent(averageCostHBox);
        
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        getTabs().addAll(tabChart, tabBarChart, tabOthers);
    }
}
