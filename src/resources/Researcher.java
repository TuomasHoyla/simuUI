package resources;
import java.util.Vector;
import ui.*;

public class Researcher  {

	static RandomGenerator randgenerator = new RandomGenerator();
	public String name;
	//	private boolean EligbleForFunding;
	double aplTimeResources = 0;
	private double qualityOfApplication;
	private double researchSkill;
	private double applyingSkill;
	public double timeAvailableForResearch = 1;
	public double oletusTutkimusAika = 0; //0.2325; //aika joka kaikilla käytössä
	public double hakemisenOsuus=0.5; // hakemisen aika osuutena oletustutkimusajasta 
	private int yearsInAcedemia;
	public double monetaryFrustration = 0;
	public double frustrationGrowthRate =0.1;
	public double promotionalFrustration = 0;
	private double totalFrustration;
	//	private int citations; //This could be removed, and there could be just method to return all the citations of the researcher
	public double ResourcesForResearch = 0;
	private int positionInOrganization; //What would be the advantages for this to be enum instead?
	private double resourcesNeededToBeEfective;
	private double productivity;
	private double baseProductivity;
	private double monetaryProductivity;
	private double sackingProbability;
	private boolean leavingOrganization;
	private double skillsSummedUp;
	double timeUsedForApplying;
	public Vector<Paper> papers = new Vector<Paper>(); 

	//Constructors TODO Citations voi ottaa huoletta pois
	Researcher() {
		this("Researcher",0,50,50,0,0,0);
	}

	public Researcher (String name) {
		this(name, 0,1,1,0,0,0); 
	}

	Researcher (String name, int yearsInAcademia) {
		this(name, yearsInAcademia,50,50,0,0,0); 
	}

	Researcher (String name, int yearsInAcademia, double researchSkill) {
		this(name, yearsInAcademia,researchSkill,50,0,0,0); 
	}

	Researcher (String name, int yearsInAcademia, double researchSkill, double applyingSkill) {
		this(name, yearsInAcademia,researchSkill,applyingSkill,0,0,0); 
	}

	Researcher (String name, int yearsInAcademia, double researchSkill, double applyingSkill, int citations) {
		this(name, yearsInAcademia,researchSkill,applyingSkill,citations,0,0); 
	}

	Researcher (String name, int yearsInAcademia, double researchSkill, double applyingSkill, int citations, int ResourcesForResearch) {
		this(name, yearsInAcademia,researchSkill,applyingSkill,citations,ResourcesForResearch,0); 
	}

	public Researcher(String name, int yearsInAcademia, double researchSkill, double applyingSkill, int citations, int ResourcesForResearch, int positionInOrganization){
		this.name = name;
		this.yearsInAcedemia = yearsInAcademia;
		this.researchSkill = researchSkill;
		this.applyingSkill = applyingSkill;
		this.ResourcesForResearch = 0;
		this.positionInOrganization = positionInOrganization;
		this.productivity = 1.0; //no need to be one...
		this.leavingOrganization = false;
		this.resourcesNeededToBeEfective = simulation.M.neededToBeEffective; //Average
		this.skillsSummedUp = this.researchSkill + this.applyingSkill; //Tï¿½llï¿½ hetkellï¿½ molemmat ovat yhtï¿½ vahvoja
	}
	public void setMoney(double dallaDallaBillYall) {
		ResourcesForResearch += dallaDallaBillYall;
	}
	
	public double getResourcesForResearch() {
		return ResourcesForResearch;
	}
		
	public void consumeMoney() {
		ResourcesForResearch=0; 
		if (ResourcesForResearch < 0) ResourcesForResearch = 0; 
	}

	public double getResearchSkill() {
		return researchSkill;
	}

	public void addResearchSkill(double skill) {
		researchSkill += skill;
	}

	public void addApplyingSkill(double skill) {
		applyingSkill += skill;
	}

	public double getSumSkill() {
		return skillsSummedUp;
	}

	/**
	 * Gives the parameter for application quality
	 * @param normalDistributedSkill 1-efectivenessOfFundingProcess/100, is the distribution ratio
	 */
	public void setSkillSum(double normalDistributedSkill) { 
		skillsSummedUp = normalDistributedSkill;
	}

	public double getApplyingSkill() {
		return applyingSkill;}
	public double justSumSkillsTogetherAndReturnThem() {

		return researchSkill + applyingSkill;
	}


	public int getYearsInAcademia() {
		return yearsInAcedemia;
	}

	public String getName() {
		return name;
	}

	public double getFrustration() {
		return totalFrustration;
	}
	public double getMonetaryFrustration(){
		return monetaryFrustration;
	}

	public void addYear() {
		yearsInAcedemia++;
	}
	public void setMonetaryFrustration() {
		if(simulation.M.monetaryFrustrationModel=="Linear_Decay"){
		monetaryFrustration += (1-ResourcesForResearch/resourcesNeededToBeEfective)*simulation.M.frustrationGrowthRate;
		}
		else if(simulation.M.monetaryFrustrationModel=="BiExponential"){
			double res= ResourcesForResearch/resourcesNeededToBeEfective;
			if(res > 0.5) {monetaryFrustration-= monetaryFrustration*(res-0.5)*simulation.M.frustrationGrowthRate;}
			if(res < 0.5) {monetaryFrustration+= (1-monetaryFrustration)*(0.5-res)*simulation.M.frustrationGrowthRate;}
		}
	}

	/**
	 * sets Promotional frustration, works but needs randomness
	 */
	public void setPromotionalFrustration(){

		if(simulation.M.promotionalFrustrationModel=="Academic_Age"){
			int temp = getPositionInOrganization()-1;
			if(getYearsInAcademia()> simulation.M.promotionalFrustrationAge[temp]){
				promotionalFrustration += randgenerator.createRandomDouble()*simulation.M.promotionalFrustrationrate[temp];
			}
		}
/*
		if (getPositionInOrganization() == 1 && getYearsInAcademia() > 3) {
			promotionalFrustration += randgenerator.createRandomDouble()/4;
		}

		if (getPositionInOrganization() == 2 && getYearsInAcademia() > 7) {
			promotionalFrustration += randgenerator.createRandomDouble()/6;
		}

		if (getPositionInOrganization() == 3 && getYearsInAcademia() > 17) {
			promotionalFrustration += randgenerator.createRandomDouble()/8;
		}
*/
		if (promotionalFrustration < 0) {promotionalFrustration = 0;} //Frustration cant get negative value

	}


	/**
	 * Works
	 */
	public void setTotalFrustration() {

		setMonetaryFrustration();
		setPromotionalFrustration();

		totalFrustration = simulation.M.monetaryFrustrationWeight*monetaryFrustration+simulation.M.promotionalFrustrationWeight*promotionalFrustration;
	}

	public double getResourcesNeededToBeEfective() {
		return resourcesNeededToBeEfective;
	}

	public int getPapers() {
		return papers.size();
	}

	public int getCitations() {
		int citations = 0;
		for (Paper paper: papers){
			citations+= paper.getCitations();
		}

		return citations;
	}
	public void setResearchSkill(double skill) {
		this.researchSkill = skill;
	}

	public int getPositionInOrganization() {
		return positionInOrganization;
	}


	public void setPositionInOrganization(int positionInOrganization) { //set level in org and at the same set the expected annually salary
		this.positionInOrganization = positionInOrganization;
		this.promotionalFrustration = simulation.M.defaultPromotionalFrustration;
		this.monetaryFrustration = simulation.M.defaultMonetaryFrustration;
		//setResourcesNeededToBeEfectiveDependingFromPositionInOrganization();
	}


	public double getProductivity() {
		return productivity;
	}

	/**
	 * Set base and monetary productivity and sums them up
	 */
	public void setProductivity() {
		if(simulation.M.productivityModel=="Summative"){
			this.baseProductivity = (1-getFrustration())+randgenerator.createNormalDistributedValue(0.0,0.1);
			monetaryProductivity =  (ResourcesForResearch/resourcesNeededToBeEfective);
			productivity = baseProductivity+monetaryProductivity;		
		}
	}

	public double getTimeAvailableForResearch() {
		return timeAvailableForResearch;
	}

	public boolean getLeavingOrganization() {
		if (this.sackingProbability >= 1.0 || totalFrustration >= 1.0) {leavingOrganization = true; 
		}
		return leavingOrganization;
	}


	/**
	 * 
	 * Sacking probability happens just in first & last level (No tenure & retirement)
	 */
	public void setSackingProbability(double sackingProbability) {
		this.sackingProbability += sackingProbability;
	}

	public double getSackingProbability() {
		return sackingProbability;
	}


	/**
	 * Set quality of application and the time used for applying
	 * @param valintaTarkkuus
	 */
	public void setQualityOfApplication (double arviointiVirhe) {
		if(simulation.M.applicationQualityModel=="Skill"){
			timeUsedForApplying = oletusTutkimusAika*hakemisenOsuus*(1-totalFrustration)*2;
			qualityOfApplication = (researchSkill)*(1.+randgenerator.createNormalDistributedValue(0, arviointiVirhe));			
		}
		if(simulation.M.applicationQualityModel=="Time_and_skills"){
			timeUsedForApplying = oletusTutkimusAika*hakemisenOsuus*(1-totalFrustration)*2;
			qualityOfApplication = (Math.sqrt(timeUsedForApplying*researchSkill)*applyingSkill)*(1.+randgenerator.createNormalDistributedValue(0, arviointiVirhe));
		}		
	}

	public void setTimeForResearch() {

		timeAvailableForResearch = oletusTutkimusAika+ResourcesForResearch-timeUsedForApplying;
	}


	public double getQualityOfApplication() {
		return qualityOfApplication;
	}

	public void setResourcesNeededToBeEfective(double d) {

		resourcesNeededToBeEfective=1.0;

	}

}