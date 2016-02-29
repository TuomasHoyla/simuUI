package resources;
import ui.*;
public class Paper {
	static RandomGenerator randGenerator = new RandomGenerator();
	
	private int citations = 0;
	private double m, beta, fitness, immediacy, longevity;
	private int creatingYear;

	/*
	 * Constructors
	 */
	public Paper(double m, double beta, double skill, int creatingYear, double immediacy, double longevity){
		this.m = m;
		this.beta = beta;
		this.fitness = skill;
		this.creatingYear = creatingYear;
		this.immediacy = immediacy;
		this.longevity = longevity;
	}

	Paper() {
		this(1,1,1,1,1,1);
	}


	/**
	 * Citations by MiC model
	 * @param thisYear
	 * @param researcherSkill
	 */
	public int updateCitationsTT(int thisYear) {
		if(simulation.M.citationModel=="Wang"){
			int citationsToAdd = (int) Math.rint(2*randGenerator.Mic(m, beta, fitness, (thisYear-getCreatingYear()), immediacy, longevity));
			if (citationsToAdd >= 0) citations += citationsToAdd;
			return citationsToAdd;
		}
		else if(simulation.M.citationModel=="WangPoisson"){
			int citationsToAdd = randGenerator.createPoisson2(randGenerator.Mic(m, beta, fitness, (thisYear-getCreatingYear()), immediacy, longevity));
			if (citationsToAdd >= 0) citations += citationsToAdd;
			return citationsToAdd;
		}
		else return 0;
	}

	public int getCitations() {
		return citations;
	}

	public void setCitations(int citations) {
		this.citations = citations;
	}

	public int getCreatingYear() {
		return creatingYear;
	}

}