import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class PresetDialog{
	
	private String title;
	private double sale_price,
		shipping_price,
		cost_per_item,
		qty,
		ad_rate,
		shipping_cost;
	private boolean discount;
	
	JFrame f;
	JSplitPane sp;
	JPanel title_p;
	JPanel input_p;
	
	JTextField title_tf = new JTextField(),
		sale_price_tf = new JTextField(),
		shipping_price_tf = new JTextField(),
		cost_per_item_tf = new JTextField(),
		qty_tf = new JTextField(),
		ad_rate_tf = new JTextField(),
		shipping_cost_tf = new JTextField();
	JCheckBox discount_cb = new JCheckBox();
	JButton save_b = new JButton("Save");
	
	public PresetDialog(String title, double sale, double shipping_price, double cost_per_item, double qty, double ad_rate, double shipping_cost, boolean discount) {
		initValues(title, sale, shipping_price, cost_per_item, qty, ad_rate, shipping_cost, discount);		
		
		
		save_b.addActionListener(new SavePreset());
		
		//Setting up title_p
		//-----------------------------------------------------------------------
		title_p = new JPanel();
		title_p.add(new JLabel("Create a preset"));
		
		
		//Setting up input_p
		//-----------------------------------------------------------------------
		input_p = new JPanel();
		input_p.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		input_p.setLayout(new GridLayout(10,2));
		
		input_p.add(new JLabel("Title: "));
		input_p.add(title_tf);		

		input_p.add(new JLabel("Sale price: "));
		input_p.add(sale_price_tf);
		
		input_p.add(new JLabel("Shipping price: "));
		input_p.add(shipping_price_tf);
		
		input_p.add(new JLabel(""));
		input_p.add(new JLabel(""));

		input_p.add(new JLabel("Cost/Item: "));
		input_p.add(cost_per_item_tf);

		input_p.add(new JLabel("Quantity: "));
		input_p.add(qty_tf);

		input_p.add(new JLabel("Ad rate: "));
		input_p.add(ad_rate_tf);

		input_p.add(new JLabel("Shipping cost: "));
		input_p.add(shipping_cost_tf);

		input_p.add(new JLabel("Final Val Discount?: "));
		discount_cb.setHorizontalAlignment(SwingConstants.CENTER);
		input_p.add(discount_cb);
		
		input_p.add(new JLabel(""));
		input_p.add(save_b);
		
		//Setting up sp
		//-----------------------------------------------------------------------
		sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, title_p, input_p);
		
		
		//Setting up frame
		//-----------------------------------------------------------------------
		f = new JFrame();
		f.add(sp);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setTitle("Preset Creator");
		f.pack();
		f.setVisible(true);
		f.setResizable(false);			//prevents resizability
		f.setLocationRelativeTo(null);	//opens f in the center of the monitor
		
	}
	
	private void initValues(String title, double sale, double shipping_price, double cost_per_item, double qty, double ad_rate, double shipping_cost, boolean discount){
		this.title = title;
		this.sale_price = sale;
		this.shipping_price = shipping_price;
		this.cost_per_item = cost_per_item;
		this.qty = qty;
		this.ad_rate = ad_rate;
		this.shipping_cost = shipping_cost;
		this.discount = discount;
		
		title_tf.setText(title);
		sale_price_tf.setText(sale+"");
		shipping_price_tf.setText(shipping_price+"");
		cost_per_item_tf.setText(cost_per_item+"");
		qty_tf.setText(qty+"");
		ad_rate_tf.setText(ad_rate+"");
		shipping_cost_tf.setText(shipping_cost+"");
		discount_cb.setSelected(discount);
	}

	
	
	private void syncValues() {
		title=title_tf.getText();
		
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
		
		discount = discount_cb.isSelected();
	}
	
	
	
	class SavePreset implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			syncValues();
			Preset new_preset = new Preset(title, sale_price, shipping_price, cost_per_item, qty, ad_rate, shipping_cost, discount);
			FileEditor temp = new FileEditor("C:\\Users\\Nathan\\eclipse-workspace\\eBay Profits Calculator\\src\\presets.txt", "presets");
			temp.appendText(new_preset.toFileString());
			f.dispose();
		}
		
	}
}


