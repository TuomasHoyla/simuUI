package resources;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;

public class LimitedBoundedRangeModel extends DefaultBoundedRangeModel {

    BoundedRangeModel limit;
    SharedFunding funding;
    int lastN = 0;

    public LimitedBoundedRangeModel(BoundedRangeModel limit, SharedFunding funding) {
        this.limit = limit;
        this.funding = funding;
    }

    /** 
     * @inherited <p>
     */
    @Override
    public void setRangeProperties(int newValue, int newExtent, int newMin,
            int newMax, boolean adjusting) {
       // if (limit != null) {
       //     int combined = newValue + limit.getValue();
     //       if (combined > newMax) {
      //          newValue = newMax - limit.getValue();
      //      }
      //  }
        super.setRangeProperties(newValue, newExtent, newMin, newMax, adjusting);
    }

	@Override
	public int getMaximum() {
		return 100;
	}

	@Override
	public void setValue(int n) {
		int delta = n - lastN;
		if(delta >= 0 && funding.getFunding() > 0){
			if((funding.getFunding() - delta) >= 0){
				super.setValue(n);
				funding.takeFunding(delta);
				lastN = n;
			}
			else{
				super.setValue(lastN);
			}
		}
		else{
			if(delta < 0){
				if((funding.getFunding() + -delta) <= funding.getMax()){
					super.setValue(n);
					funding.putFunding(-delta);
					lastN = n;
				}
				else{
					super.setValue(lastN);
				}
			}
		}
	}
}