
public class Preset {

	private String title;
	private double sale,
		shipping_price,
		cost_per_item,
		qty,
		ad_rate,
		shipping_cost;
	private boolean discount;
	public Preset() {
		
	}
	public Preset(String title){
		this.title = title;
	}
	
	public Preset(String title, double sale, double shipping_price, double cost_per_item, double qty, double ad_rate, double shipping_cost, boolean discount) {
		this.title = title;
		this.sale = sale;
		this.shipping_price = shipping_price;
		this.cost_per_item = cost_per_item;
		this.qty = qty;
		this.ad_rate = ad_rate;
		this.shipping_cost = shipping_cost;
		this.discount = discount;
	}
	
	public String get_title() {
		return new String(title);
	}
	
	public double get_sale() {
		return sale;
	}
	
	public double get_shipping_price() {
		return shipping_price;
	}
	
	public double get_cost_per_item() {
		return cost_per_item;
	}
	
	public double get_qty() {
		return qty;
	}
	
	public double get_ad_rate() {
		return ad_rate;
	}
	
	public double get_shipping_cost() {
		return shipping_cost;
	}
	
	public boolean get_discount() {
		return discount;
	}
	
	public void set_title(String new_title){
		title = new_title;
	}
	
	public void set_sale(double new_sale) {
		sale = new_sale;
	}
	
	public void set_shipping_price(double new_shipping_price) {
		shipping_price = new_shipping_price;
	}
	
	public void set_cost_per_item(double new_cost_per_item) {
		cost_per_item = new_cost_per_item;
	}
	
	public void set_qty(double new_qty) {
		qty = new_qty;
	}
	
	public void set_ad_rate(double new_ad_rate) {
		ad_rate = new_ad_rate;
	}
	
	public void set_shipping_cost(double new_shipping_cost) {
		shipping_cost = new_shipping_cost;
	}
	
	public void set_discount(boolean new_discount) {
		discount = new_discount;
	}
	
	public String toFileString() {
		StringBuilder ret = new StringBuilder();
		ret.append(title+",");
		ret.append(sale+",");
		ret.append(shipping_price+",");
		ret.append(cost_per_item+",");
		ret.append(qty+",");
		ret.append(ad_rate+",");
		ret.append(shipping_cost+",");
		ret.append(discount);
		return ret.toString();
	}
	
	public String toString() {
		return get_title();
	}

	public boolean equals(Object other) {
		if (other instanceof Preset)
			return this.equals((Preset) other);
		return false;
	}
	private boolean equals(Preset other){
		return this.toFileString().equals(other.toFileString());
	}
	
}
