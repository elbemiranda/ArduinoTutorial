package arduinotutorialproject;

import br.pro.turing.javino.Javino;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class ArduinoTutorialProject {
    
    public static Javino javino = new Javino("/usr/bin");
    public static String port = "/dev/ttyACM1";
    
    public static void main(String[] args) {
        
        double[][] lightInitData = getArduinoData("getLight");
        double[][] tempInitData = getArduinoData("getTemp");

        // Create Light Chart
        final XYChart lightChart = QuickChart.getChart("Arduino Light Monitor", "Time", "Light", "light", lightInitData[0], lightInitData[1]);

        // Show Light Chart
        final SwingWrapper<XYChart> swLight = new SwingWrapper<XYChart>(lightChart);
        swLight.displayChart();

        // Create Temperature Chart
        final XYChart tempChart = QuickChart.getChart("Arduino Temperature Monitor", "Time", "Temp", "temp", tempInitData[0], tempInitData[1]);

        // Show Temperature Chart
        final SwingWrapper<XYChart> swTemp = new SwingWrapper<XYChart>(tempChart);
        swTemp.displayChart();
        
        while (true) {
            final double[][] lightData = getArduinoData("reqLight");
            lightChart.updateXYSeries("light", lightData[0], lightData[1], null);
            swLight.repaintChart();
            
            final double[][] TempData = getArduinoData("reqTemp");
            tempChart.updateXYSeries("temp", TempData[0], TempData[1], null);
            swTemp.repaintChart();
        }
    }
    
    private static double[][] getArduinoData(String command) {
        
        double[] xData = new double[100];
        double[] yData = new double[100];
        for (int i = 0; i < xData.length; i++) {
            javino.sendCommand(port, command);
            javino.listenArduino(port);
            String data = javino.getData();
            if (data != null) {
                xData[i] = i;
                yData[i] = Double.parseDouble(data);
                System.out.println(data);
            }
        }
        return new double[][]{xData, yData};
    }
}
