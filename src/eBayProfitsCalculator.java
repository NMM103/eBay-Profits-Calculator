import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.*;

 
public class eBayProfitsCalculator {
	static final String SHIPPING_CALC = "https://www.ebay.com/shp/Calculator";
	final double DISCOUNTED_FVF_RATE = 0.09,
			NORMAL_FVF_RATE = 0.10;

	double sale_price, 
		shipping_price, 
		cost_per_item, 
		qty, 
		final_val_fee, 
		ad_fee, 
		ad_rate,
		paypal_fee, 
		shipping_cost, 
		profit, 
		profit_margin,
		break_even;
	
	boolean final_val_discount;
	
	JFrame f;
	JSplitPane inner_home_sp;
	JSplitPane outer_home_sp;
	JSplitPane right_pane_sp;
	JPanel calc_p;
	JPanel title_p;
	JPanel stats_p;
	JPanel preset_p;
	
	JTextField sale_price_tf, 
		shipping_price_tf, 
		cost_per_item_tf, 
		qty_tf,  
		ad_rate_tf, 
		shipping_cost_tf; 
	
	JLabel final_val_fee_l, 
		ad_fee_l, 
		paypal_fee_l, 
		profit_l, 
		profit_margin_l,
		break_even_l;
	
	JCheckBox final_val_discount_cb = new JCheckBox();
	
	JButton update_b,
		shipping_calc_b,
		delete_preset_b,
		save_preset_b,
		create_preset_b;
	
	JComboBox<Preset> preset_selector_cb;
	
	UpdateResults updater = new UpdateResults();
	
	public eBayProfitsCalculator() throws IOException {	
		f = new JFrame();
		title_p = new JPanel();
		calc_p = new JPanel();
		stats_p = new JPanel();
		preset_p = new JPanel();
						
		
		sale_price_tf = new JTextField("");
		sale_price_tf.setHorizontalAlignment(SwingConstants.RIGHT);
		shipping_price_tf = new JTextField("");
		shipping_price_tf.setHorizontalAlignment(SwingConstants.RIGHT);
		cost_per_item_tf = new JTextField(""); 
		cost_per_item_tf.setHorizontalAlignment(SwingConstants.RIGHT);
		qty_tf = new JTextField(""); 
		qty_tf.setHorizontalAlignment(SwingConstants.RIGHT);
		ad_rate_tf = new JTextField("");
		ad_rate_tf.setHorizontalAlignment(SwingConstants.RIGHT);
		shipping_cost_tf = new JTextField("");
		shipping_cost_tf.setHorizontalAlignment(SwingConstants.RIGHT);
		final_val_discount_cb.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		final_val_fee_l = new JLabel("-"); 
		final_val_fee_l.setHorizontalAlignment(SwingConstants.RIGHT);
		ad_fee_l = new JLabel("-");
		ad_fee_l.setHorizontalAlignment(SwingConstants.RIGHT);
		paypal_fee_l = new JLabel("-");
		paypal_fee_l.setHorizontalAlignment(SwingConstants.RIGHT);
		profit_l = new JLabel("-");
		profit_l.setHorizontalAlignment(SwingConstants.RIGHT);
		profit_margin_l = new JLabel("-%");
		profit_margin_l.setHorizontalAlignment(SwingConstants.RIGHT);
		break_even_l = new JLabel("-");
		break_even_l.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		
		//Setting up all buttons
		//---------------------------------------------------------------
		update_b = new JButton();
		update_b.addActionListener(new UpdateResults());
		update_b.setText("Update");
		
		shipping_calc_b = new JButton();
		shipping_calc_b.addActionListener(new OpenShippingCalculator());
		shipping_calc_b.setText("Calculate");
		
		save_preset_b = new JButton();
		save_preset_b.addActionListener(new SavePreset());
		save_preset_b.setText("Save Preset");
		
		delete_preset_b = new JButton();
		delete_preset_b.addActionListener(new DeletePreset());
		delete_preset_b.setText("Delete Preset");
		
		create_preset_b = new JButton();
		create_preset_b.addActionListener(new OpenPresetWindow());
		create_preset_b.setText("Create Preset");
		
		
		//Setting up title_p
		//---------------------------------------------------------------
		ImageIcon logo = new ImageIcon("C:\\Users\\Nathan\\eclipse-workspace\\eBay Profits Calculator\\src\\ebay_profits_calculator.png");
		title_p.add(new JLabel(logo));

		
		
		
		//Setting up calc_p
		//---------------------------------------------------------------
		calc_p.setBorder(BorderFactory.createEmptyBorder(50,50,50,35));
		calc_p.setLayout(new GridLayout(11,3));
		calc_p.add(new JLabel("Sale: "));
		calc_p.add(sale_price_tf);
		calc_p.add(new JLabel(""));
		calc_p.add(new JLabel("(+ shipping: )"));
		calc_p.add(shipping_price_tf);
		calc_p.add(new JLabel(""));
		
		calc_p.add(new JLabel(""));
		calc_p.add(new JLabel(""));
		calc_p.add(new JLabel(""));
		
		calc_p.add(new JLabel("Cost/Item "));
		calc_p.add(cost_per_item_tf);
		calc_p.add(new JLabel(""));
		calc_p.add(new JLabel("Qty: "));
		calc_p.add(qty_tf);
		calc_p.add(new JLabel(""));
		calc_p.add(new JLabel("eBay Final Val.: "));
		calc_p.add(final_val_fee_l);
		calc_p.add(final_val_discount_cb);
		calc_p.add(new JLabel("eBay Ad Fees:"));
		calc_p.add(ad_fee_l);
		calc_p.add(ad_rate_tf);		
		calc_p.add(new JLabel("PayPal Fee: "));
		calc_p.add(paypal_fee_l);
		calc_p.add(new JLabel(""));
		calc_p.add(new JLabel("Shipping Cost: "));
		calc_p.add(shipping_cost_tf);
		calc_p.add(shipping_calc_b);
		calc_p.add(new JLabel(""));
		calc_p.add(new JLabel(""));
		calc_p.add(new JLabel(""));
		calc_p.add(new JLabel(""));
		calc_p.add(update_b);
		calc_p.add(new JLabel(""));
		
		
		
		//Setting up stats_p
		//---------------------------------------------------------------
		stats_p.setBorder(BorderFactory.createEmptyBorder(50,50,125,50));
		stats_p.setLayout(new GridLayout(4,2));
		stats_p.add(new JLabel("Profit: "));
		stats_p.add(profit_l);
		stats_p.add(new JLabel("Margin: "));
		stats_p.add(profit_margin_l);
		stats_p.add(new JLabel(""));
		stats_p.add(new JLabel(""));
		stats_p.add(new JLabel("Break Even Price: "));
		stats_p.add(break_even_l);
		
		
		
		//Setting up preset_p
		//---------------------------------------------------------------
		preset_p.setLayout(new GridBagLayout());
		preset_selector_cb = new JComboBox<Preset>();
		preset_selector_cb.addActionListener(new PresetLoader());
		//preset_selector_cb.addItem(new Preset(""));
		updatePresets(); 										//adds saved presets
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);	//buffer between items
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		preset_p.add(preset_selector_cb, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		preset_p.add(save_preset_b, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		preset_p.add(delete_preset_b, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 2;
		gbc.gridy = 1;
		preset_p.add(create_preset_b, gbc);
		
		
		
		
		
		//Setting up split panes
		//---------------------------------------------------------------
		right_pane_sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, stats_p, preset_p);
		inner_home_sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, calc_p, right_pane_sp);
		outer_home_sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, title_p, inner_home_sp);

		
		
		
		
		
		f.add(outer_home_sp, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("eBay Profits Calculator");
		f.pack();
		f.setVisible(true);
		f.setResizable(false);			//prevents resizability
		f.setLocationRelativeTo(null);	//opens f in the center of the monitor
	}
	
	
	
	
	
	
	public void updatePresets() throws IOException {
		FileReader fr = new FileReader("C:\\Users\\Nathan\\eclipse-workspace\\eBay Profits Calculator\\src\\presets.txt");
		Scanner scan = new Scanner(fr);
		scan.useDelimiter(",");
		
		preset_selector_cb.removeAllItems();
		preset_selector_cb.addItem(new Preset(""));
		while(scan.hasNext()) {	
			preset_selector_cb.addItem(new Preset(
					scan.next(),
					Double.parseDouble(scan.next()),
					Double.parseDouble(scan.next()), 
					Double.parseDouble(scan.next()), 
					Double.parseDouble(scan.next()), 
					Double.parseDouble(scan.next()), 
					Double.parseDouble(scan.next()), 
					Boolean.parseBoolean(scan.nextLine().replace(',', ' ').trim()))
			);	
		}
		fr.close();
	}
	
	
	
	
	public static void main (String[] args) throws Exception {
		new eBayProfitsCalculator();
	}
	
		
	
	
	
	
	
	
	
	
	
	class UpdateResults implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			update();
		}
		/*
		 * updates all calc_p
		 */
		public void update() {
			//parsing tf's...
			try {sale_price=Double.parseDouble(sale_price_tf.getText());}
			catch (NumberFormatException er){sale_price=0;}
			
			try {shipping_price=Double.parseDouble(shipping_price_tf.getText());}
			catch (NumberFormatException er){shipping_price=0;}
			
			try {cost_per_item=Double.parseDouble(cost_per_item_tf.getText());}
			catch (NumberFormatException er){cost_per_item=0;}
			
			try {qty=Double.parseDouble(qty_tf.getText());}
			catch (NumberFormatException er){qty=0;}
			
			try {shipping_cost=Double.parseDouble(shipping_cost_tf.getText());}
			catch (NumberFormatException er){shipping_cost=0;}
			
			try {ad_rate=Double.parseDouble(ad_rate_tf.getText());}
			catch (NumberFormatException er){ad_rate=0;}
			
			final_val_discount = final_val_discount_cb.isSelected();
			
			
			
			
			
			//calculating...
			double rate;
			if(final_val_discount)
				rate = DISCOUNTED_FVF_RATE;
			else
				rate = NORMAL_FVF_RATE;
			
			
			final_val_fee = Math.round((sale_price + shipping_price) * rate * 100) / 100.0;
			
			
			double bep = sale_price + shipping_price;
			for(double prev_bep=0; Math.abs(prev_bep-bep) > 0.005;)
			{
				prev_bep=bep;
				bep = (cost_per_item*qty) + 0.30 + (ad_rate/100*bep) + (.029*bep) + shipping_cost + (rate * bep);
			}
			break_even = bep;
			
			
			
			ad_fee = Math.round((sale_price + shipping_price) * ad_rate) / 100.0;
			paypal_fee = Math.round((sale_price + shipping_price) * 2.9)/100.0 + 0.3;
			profit = Math.round(100*(sale_price + shipping_price - (qty * cost_per_item) - final_val_fee - ad_fee - paypal_fee - shipping_cost))/100.0;
			profit_margin = Math.round(profit / (sale_price + shipping_price) * 10000.0)/100.0;

			
			
			
			
			//setting text...
			final_val_fee_l.setText(""+final_val_fee);
			ad_fee_l.setText(""+ad_fee);
			paypal_fee_l.setText(""+paypal_fee);
			profit_l.setText("$"+profit);
			profit_margin_l.setText(profit_margin+"%");
			break_even_l.setText("$"+ Math.round(100*(break_even-shipping_price))/100.0 +" + $"+ shipping_price +" shipping");
			
			
		}
	}
	
	
	
	
	
	
	
	
	
	/*
	 * Opens link to eBay's shipping calculator
	 */
	class OpenShippingCalculator implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//new ShippingFrame(); //Implement using eBay's API later
			
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
	
	
	
	class DeletePreset implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			FileEditor temp = new FileEditor("C:\\Users\\Nathan\\eclipse-workspace\\eBay Profits Calculator\\src\\presets.txt", "presets");
			Preset curr = (Preset)preset_selector_cb.getSelectedItem();
			temp.replaceText(curr.toFileString()+System.lineSeparator(), "");
			preset_selector_cb.removeItem(curr);
	
		}
		
	}
	
	
	
	/*
	 * Saves current preset
	 */
	class SavePreset implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int sel_index;
			updater.update();
			FileEditor temp = new FileEditor("C:\\Users\\Nathan\\eclipse-workspace\\eBay Profits Calculator\\src\\presets.txt", "presets");
			temp.readAllText();
			
			Preset curr = (Preset)preset_selector_cb.getSelectedItem();
			sel_index = preset_selector_cb.getSelectedIndex();
			
			Preset new_preset = new Preset(curr.toString(), sale_price, shipping_price, cost_per_item, qty,  ad_rate,  shipping_cost, final_val_discount);
			
			temp.replaceText(curr.toFileString()+System.lineSeparator(), new_preset.toFileString());
			try {updatePresets();} catch (IOException e1) {e1.printStackTrace();}
			
			preset_selector_cb.setSelectedIndex(sel_index);
			
		}
		
	}
	
	/*
	 * Loads selected preset values
	 */
	class PresetLoader implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Preset curr = (Preset)preset_selector_cb.getSelectedItem();
			if(curr == null)
				return;
			
			//prevents an empty preset from being able to be deleted
			delete_preset_b.setEnabled(true);
			if(curr.equals(new Preset("")))
				delete_preset_b.setEnabled(false);
			
			sale_price_tf.setText(""+curr.get_sale());
			shipping_price_tf.setText(""+curr.get_shipping_price());
			cost_per_item_tf.setText(""+curr.get_cost_per_item());
			qty_tf.setText(""+curr.get_qty());
			final_val_discount_cb.setSelected(curr.get_discount());
			ad_rate_tf.setText(""+curr.get_ad_rate());
			shipping_cost_tf.setText(""+curr.get_shipping_cost());
			updater.update();
			
		}		
	}
	
	
	/*
	 * Opens page to create a new preset
	 */
	class OpenPresetWindow implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new PresetDialog("", sale_price, shipping_price,  cost_per_item, qty,  ad_rate,  shipping_cost, final_val_discount);
			try {
				updatePresets();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
	
	
