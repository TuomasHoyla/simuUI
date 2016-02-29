package resources;

import java.util.ArrayList;

public class palaute {

	ArrayList<Double> paperit = new ArrayList<Double>();
	ArrayList<Double> sitaatit = new ArrayList<Double>();
	ArrayList<String> teksti = new ArrayList<String>();
	ArrayList<Double> citPos1 = new ArrayList<Double>();
	ArrayList<Double> citPos2 = new ArrayList<Double>();
	ArrayList<Double> citPos3 = new ArrayList<Double>();
	ArrayList<Double> citPos4 = new ArrayList<Double>();
	
	public ArrayList<Double> getPapers(){
		return paperit;
	}
	
	public ArrayList<Double> getCitations(){
		return sitaatit;
	}
	
	public ArrayList<String> getText(){
		return teksti;
	}
	
	public ArrayList<Double> getCitationsPos1(){
		return citPos1;
	}
	
	public ArrayList<Double> getCitationsPos2(){
		return citPos2;
	}
	
	public ArrayList<Double> getCitationsPos3(){
		return citPos3;
	}
	
	public ArrayList<Double> getCitationsPos4(){
		return citPos4;
	}
	
	
	
}
