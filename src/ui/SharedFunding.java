

package ui;

import resources.Researcher;

public class SharedFunding {
    double varmaFunding = 5.5;
    double funding = 5.5;
    
    public SharedFunding(){
        
    }
    
    public double getFunding(){
        return funding;
    }
    
    public double takeFunding(double amount){
        if((funding - amount) >= 0){
            funding -= amount;
        }
//        System.out.prdoubleln("Funding taken: " + amount);
//        System.out.prdoubleln("Funding current: " + funding);
        return funding;
    }
    
    
    public double jaaRahaa(double haluttu) {
        
        double palautettavaa = 0;
        
        if (funding >= haluttu) {
        funding -= haluttu;
        palautettavaa = haluttu;
        }
        else {
            palautettavaa = funding;
            funding = 0;
        }
   //     System.out.println(palautettavaa);
        return palautettavaa;   
    }

    public void setFunds(Researcher researcher, double amount) {
    	researcher.consumeMoney();
    //	System.out.println(amount);
    	//T�h�n tulee sillein et t�ytt�� tutkijan toiveet, vaan ei ylit�. T�n voi disabloida sitten tarvittaessa.
    	amount = researcher.getResourcesNeededToBeEfective()-researcher.ResourcesForResearch;
    	researcher.setMoney(jaaRahaa(amount)+varmaFunding);        
    }

    public void setFunds(double d) {
        funding = d;
        
    }
}

