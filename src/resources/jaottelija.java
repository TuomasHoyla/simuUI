package resources;

public class jaottelija {
	
	double kokopotti = 0;
	double tasaraha = 0;
	double haettavaRaha = 0;
	
	
	public void jaaTasaraha(Researcher researcher, int populaationKoko) {
		researcher.consumeMoney();
		researcher.setMoney((tasaraha/populaationKoko)+researcher.getResourcesNeededToBeEfective());
		haettavaRaha -= researcher.getResourcesNeededToBeEfective();
	
	}	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
