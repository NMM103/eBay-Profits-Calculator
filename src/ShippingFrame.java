//import java.awt.BorderLayout;
import java.awt.Desktop;
import java.net.URL;
import javax.swing.*;
//import com.ebay.sdk.ApiContext;

public class ShippingFrame {
	static final String SHIPPING_CALC = "https://www.ebay.com/shp/Calculator";
	JFrame f;
	JPanel loading;
	public ShippingFrame() {
		
		f = new JFrame();
		loading= new JPanel();
		loading.add(new JLabel("Loading..."));	
		
		f.add(loading);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setTitle("");
		f.pack();
		f.setVisible(true);
		f.setResizable(false);			//prevents resizability
		f.setLocationRelativeTo(null);	//opens f in the center of the monitor
		
		openShippingCalculator();
		}
	
	private void openShippingCalculator() {
	    try {
	        Desktop.getDesktop().browse(new URL(SHIPPING_CALC).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
