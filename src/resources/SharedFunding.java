package resources;

public class SharedFunding {
	int maxFunding = 100;
	int funding = 100;
	
	public SharedFunding(){
		
	}
	
	public int getFunding(){
		return funding;
	}
	
	public int takeFunding(int amount){
		if((funding - amount) >= 0){
			funding -= amount;
		}
//		System.out.println("Funding taken: " + amount);
//		System.out.println("Funding current: " + funding);
		return funding;
	}
	
	public int putFunding(int amount){
		if((funding + amount) <= maxFunding){
			funding += amount;
		}
//		System.out.println("Funding put: " + amount);
//		System.out.println("Funding current: " + funding);
		return funding;
	}
	
	public void setMaxFunding(int funding){
//		System.out.println("Max funding: " + funding);
//		System.out.println("Current funding: " + funding);
		int delta = funding - maxFunding;
		maxFunding = funding;
		this.funding += delta;
	}
	
	public int getMax(){
		return maxFunding;
	}
}
