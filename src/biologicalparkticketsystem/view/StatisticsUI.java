/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 *
 * @author Luis Varela
 */
public class StatisticsUI {
    
    final private PieChart chart;
    
    public StatisticsUI(){
        //get data from controller
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Grapefruit", 13),new PieChart.Data("Oranges", 25),new PieChart.Data("Plums", 10), new PieChart.Data("Pears", 22), new PieChart.Data("Apples", 30));
        chart = new PieChart(pieChartData);
        chart.setTitle("Imported Fruits");
    }
    
    public PieChart getChart(){
        return this.chart;
    }
}
