import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.util.Arrays;

public class ChartOperations {

    private static int[] xAxis = {500,1000,2000,4000,8000,16000,32000,64000,128000,250000};

    public static void generateCharts() {
        showAndSaveChartAllSortings("Sorting Tests on Random Data (I)",xAxis, Experiments.sortingRandomDataRunningTimes);
        showAndSaveChartSortings("Sorting Tests on Random Data (II)",xAxis,Experiments.sortingRandomDataRunningTimes);
        showAndSaveChartAllSortings("Sorting Tests on Ascending Sorted Data",xAxis, Experiments.sortingAscendingDataRunningTimes);
        showAndSaveChartAllSortings("Sorting Tests on Descending Sorted Data",xAxis, Experiments.sortingDescendingDataRunningTimes);

        showAndSaveChartSearch(xAxis, Experiments.searchRunningTimes);

    }

    public static void showAndSaveChartSortings(String title, int[] xAxis, double[][] yAxis) {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Quick Sort", doubleX, yAxis[1]);
        chart.addSeries("Bucket Sort", doubleX, yAxis[2]);

        // Save the chart as PNG
        try {
            BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            System.out.println("An exception occured while generating" + title + ".png");
        }

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }


    public static void showAndSaveChartSearch(int[] xAxis, double[][] yAxis) {

        String title = "Search Tests";

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Nanoseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Linear Search Random Data", doubleX, yAxis[0]);
        chart.addSeries("Linear Search Ascending Sorted data", doubleX, yAxis[1]);
        chart.addSeries("Binary Search (Ascending Sorted Data)", doubleX, yAxis[2]);


        // Save the chart as PNG
        try {
            BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            System.out.println("An exception occured while generating" + title+".png");
        }

        // Show the chart
        new SwingWrapper(chart).displayChart();


    }

    public static void showAndSaveChartAllSortings(String title, int[] xAxis, double[][] yAxis)  {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Selection Sort", doubleX, yAxis[0]);
        chart.addSeries("Quick Sort", doubleX, yAxis[1]);
        chart.addSeries("Bucket Sort", doubleX, yAxis[2]);

        // Save the chart as PNG
        try {
            BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            System.out.println("An exception occured while generating" + title+".png");
        }

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }

}
