package ui;



import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import resources.*;

public class simulation {
	
	public static Model M = new Model();
	public static Monitor mon = new Monitor();
	FileRead fileread = new FileRead();
	DecimalFormat df = new DecimalFormat("#.##");
	applyForFunds funder = new applyForFunds();
	RandomGenerator randomGenerator = new RandomGenerator();
	public ArrayList<Researcher> researcherArray = new ArrayList<>();
	ArrayList<Researcher> TempResearchersToBeAdded = new ArrayList<>();
	ArrayList<Researcher> TempResearchersToBeRemoved = new ArrayList<>();
	public ArrayList<Integer> cumuPaperCounter = new ArrayList<Integer>();
	public ArrayList<Integer> cumuCitationCounter = new ArrayList<Integer>();
	int addableCitations;
	int addablePapers;
	int totalCitations;
	int totalPapers;
	int year;
	private int papersFromRemovedResearchers;
	private int citationsFromRemovedResearchers;
	private double overhead = 0; // 0 - 100%
	private double kuinkaPaljonJaetaanTasan = 0; //0%-100%
	private double maksimiTutkimusResurssi = 0.63; //0.63
	private double kuinkaPaljonMaksimiTutkimisResurssistaHalutaan = 0.63;	
	public static int[] ResearchersByLevels= new int[4];
	public static int[] PapersByLevels= new int[4];
	public static int[] CurrentPapers = new int[4];
	public static int[] CurrentCitations = new int[4];	
	public static int[] CitationsByLevels= new int[4];
	public static int[] ResignByLevels= new int[4];
	public static int[] PromoteByLevels =new int[4];
	public static int[] RetirementAge = new int[4];
	public static double[] SkillByLevels = new double[4];
	public static double[] FrustrationByLevels = new double[4];
	
	Comparator<Researcher> vertaaja = new Comparator<Researcher>(){
		public int compare(Researcher p1, Researcher p2) {
			if (p1.getQualityOfApplication() < p2.getQualityOfApplication()) return -1;
			if (p1.getQualityOfApplication() > p2.getQualityOfApplication()) return 1;
			return 0;
		}
	};
	
	public void initialize()
	{
	
// Initialize a researcher array (with random research skills and right amount of  instances to each level
	for (int i=0; i<4;i++)
	{
		
		for (int j=0; j<M.PositionLevels[i];j++)
		{
		researcherArray.add(new Researcher(RandomGenerator.nextSessionId() , 0, randomGenerator.createResSkill(), randomGenerator.createSkill(), 0, 0, i+1));	
		}
	}
	}
	

	public void RemoveResearchers() 
	{
		for (int i=0;i<4;i++) {ResignByLevels[i]=0;
		RetirementAge[i]=0;}
		for (Researcher ukkeli : researcherArray) 
		{
			ukkeli.consumeMoney();	
			ukkeli.addYear();
			if (ukkeli.getYearsInAcademia() >= 38 && randomGenerator.createRandomDouble() >= 0.8) 
			{
				ukkeli.setSackingProbability(1.);
			}
			if (ukkeli.getLeavingOrganization()) 
				{ 
				citationsFromRemovedResearchers += ukkeli.getCitations(); //Save citations to counter from researchers who are leaving
				papersFromRemovedResearchers += ukkeli.getPapers();        //Save papers to counter from researchers who are leaving
				TempResearchersToBeRemoved.add(ukkeli); 
				ResignByLevels[ukkeli.getPositionInOrganization()-1]++;
				RetirementAge[ukkeli.getPositionInOrganization()-1]+=ukkeli.getYearsInAcademia();
				}		
		}
		for (Researcher ukkeli: TempResearchersToBeRemoved)
		{
			researcherArray.remove(ukkeli);
		}
		TempResearchersToBeRemoved.clear();
	}
	
	public void CountByLevels()
	{
	for (int i=0;i<4; i++)
	{
		ResearchersByLevels[i]=0;
		PapersByLevels[i]=0;
		CitationsByLevels[i]=0;
		SkillByLevels[i]=0.;
		FrustrationByLevels[i]=0.;
	}
	for (Researcher ukkeli : researcherArray) 
	{
		ResearchersByLevels[ukkeli.getPositionInOrganization()-1]++;	
		PapersByLevels[ukkeli.getPositionInOrganization()-1]+=ukkeli.getPapers();	
		CitationsByLevels[ukkeli.getPositionInOrganization()-1]+=ukkeli.getCitations();	
		SkillByLevels[ukkeli.getPositionInOrganization()-1]+=ukkeli.getResearchSkill();
		FrustrationByLevels[ukkeli.getPositionInOrganization()-1]+=ukkeli.getFrustration();
	}
	
	}

	public void AddResearchers()
	{
		CountByLevels();
		int temp= ResearchersByLevels[0]+ResearchersByLevels[1]+ResearchersByLevels[2]+ResearchersByLevels[3];
		for (int i=0;i< M.PopulationSize-temp;i++)
		{
			researcherArray.add(new Researcher(RandomGenerator.nextSessionId() , 0, randomGenerator.createResSkill(), randomGenerator.createSkill(), 0, 0, 1));
		}
	}
	
	public void TTpromote() {

		double[] citationaverage = new double[4]; 
		CountByLevels();
		if(M.promotionModel=="Citation_based"){
			for (int i=0;i<4;i++)
			{
				citationaverage[i]=CitationsByLevels[i]/(ResearchersByLevels[i]+0.0001);
				PromoteByLevels[i]=0;
			}
			for(Researcher dude : researcherArray) {
				if (dude.getPositionInOrganization() == 3 &&  dude.getYearsInAcademia() >= 18 && (dude.getCitations() >= M.promotionTreshold*citationaverage[2])) 
				{
					dude.setPositionInOrganization(4);
					PromoteByLevels[2]++;
				}
			}
			for(Researcher dude : researcherArray) {
				if (dude.getPositionInOrganization() == 2 &&  dude.getYearsInAcademia() >= 10 && (dude.getCitations() >= M.promotionTreshold*citationaverage[1])) 
					{
					dude.setPositionInOrganization(3);
					PromoteByLevels[1]++;
					}
				}
				for(Researcher dude : researcherArray) {
					if (dude.getPositionInOrganization() == 1 && dude.getYearsInAcademia() > 4 && dude.getCitations() >= M.promotionTreshold*citationaverage[0])
					{
						dude.setPositionInOrganization(2);
						PromoteByLevels[0]++;
					}
			}
			
		}
	}

	public void evenAllocation() {
		double nettoTutkimusResurssit = maksimiTutkimusResurssi*M.AllocatableResource; 
		double varmatTutkimusResurssit = nettoTutkimusResurssit; //Jaetaan tasaisesti populaation kesken
		double varmatTutkimusResurssitPerTutkija = varmatTutkimusResurssit/researcherArray.size();
		for (Researcher researcher: researcherArray) {
			researcher.setResourcesNeededToBeEfective(kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
			researcher.setMoney(varmatTutkimusResurssitPerTutkija); 
			researcher.setTimeForResearch();
		}		
	}
	
	public void randomAllocation() {
		double nettoTutkimusResurssit = maksimiTutkimusResurssi*M.AllocatableResource; 
		double varmatTutkimusResurssit = 0; //Jaetaan tasaisesti populaation kesken
		double varmatTutkimusResurssitPerTutkija = 0;
		funder.funds.funding =(nettoTutkimusResurssit-varmatTutkimusResurssit); 
		funder.funds.varmaFunding = varmatTutkimusResurssitPerTutkija;

		Collections.shuffle(researcherArray);//sekoitetaan

		for (Researcher researcher: researcherArray) {
			researcher.setResourcesNeededToBeEfective(kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
			researcher.setMoney(varmatTutkimusResurssitPerTutkija); 
			funder.receiveResearcher(researcher, kuinkaPaljonMaksimiTutkimisResurssistaHalutaan-varmatTutkimusResurssitPerTutkija);
			researcher.setTimeForResearch();
		}		
	}

	public void grantAllocation(double arviointiVirhe) {
		ArrayList<Researcher> temp2 = new ArrayList<>();
		ArrayList<Researcher> temp3 = new ArrayList<>();
		ArrayList<Researcher> temp4 = new ArrayList<>();
		double nettoTutkimusResurssit = (1-overhead)*maksimiTutkimusResurssi*M.AllocatableResource; 		
		double varmatTutkimusResurssit = 0; //nettoTutkimusResurssit*kuinkaPaljonJaetaanTasan; 
		double varmatTutkimusResurssitPerTutkija = varmatTutkimusResurssit/researcherArray.size();
		funder.funds.varmaFunding = varmatTutkimusResurssitPerTutkija;

		for (Researcher researcher: researcherArray) {
			researcher.setQualityOfApplication(arviointiVirhe);
			int taso =researcher.getPositionInOrganization();
			if (taso==2) {
				temp2.add(researcher);
			}
			if (taso==3) {
				temp3.add(researcher);
			}
			if (taso==4) {
				temp4.add(researcher);
			}
		}
		for (Researcher researcher: temp2)
		{
			researcherArray.remove(researcher);
		}
		for (Researcher researcher: temp3)
		{
			researcherArray.remove(researcher);
		}
		for (Researcher researcher: temp4)
		{
			researcherArray.remove(researcher);
		}
		
		Collections.sort(researcherArray, vertaaja);

		Collections.reverse(researcherArray);
		funder.funds.funding =(nettoTutkimusResurssit-varmatTutkimusResurssit)*researcherArray.size()/M.PopulationSize; 
			for (Researcher researcher: researcherArray) {
			researcher.setResourcesNeededToBeEfective(kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
			researcher.setMoney(varmatTutkimusResurssitPerTutkija); 
			funder.receiveResearcher(researcher, kuinkaPaljonMaksimiTutkimisResurssistaHalutaan-varmatTutkimusResurssitPerTutkija);
			researcher.setTimeForResearch();
		}
			Collections.sort(temp2, vertaaja);

			Collections.reverse(temp2);
			funder.funds.funding =(nettoTutkimusResurssit-varmatTutkimusResurssit)*temp2.size()/M.PopulationSize; 
				for (Researcher researcher: temp2) {
				researcher.setResourcesNeededToBeEfective(kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
				researcher.setMoney(varmatTutkimusResurssitPerTutkija); 
				funder.receiveResearcher(researcher, kuinkaPaljonMaksimiTutkimisResurssistaHalutaan-varmatTutkimusResurssitPerTutkija);
				researcher.setTimeForResearch();
				researcherArray.add(researcher);
			}
				Collections.sort(temp3, vertaaja);

				Collections.reverse(temp3);
				funder.funds.funding =(nettoTutkimusResurssit-varmatTutkimusResurssit)*temp3.size()/M.PopulationSize; 
					for (Researcher researcher: temp3) {
					researcher.setResourcesNeededToBeEfective(kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
					researcher.setMoney(varmatTutkimusResurssitPerTutkija); 
					funder.receiveResearcher(researcher, kuinkaPaljonMaksimiTutkimisResurssistaHalutaan-varmatTutkimusResurssitPerTutkija);
					researcher.setTimeForResearch();
					researcherArray.add(researcher);
		}
					Collections.sort(temp4, vertaaja);

					Collections.reverse(temp4);
					funder.funds.funding =(nettoTutkimusResurssit-varmatTutkimusResurssit)*temp4.size()/M.PopulationSize; 
						for (Researcher researcher: temp4) {
						researcher.setResourcesNeededToBeEfective(kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
						researcher.setMoney(varmatTutkimusResurssitPerTutkija); 
						funder.receiveResearcher(researcher, kuinkaPaljonMaksimiTutkimisResurssistaHalutaan-varmatTutkimusResurssitPerTutkija);
						researcher.setTimeForResearch();
						researcherArray.add(researcher);
			}
			temp2.clear();
			temp3.clear();
			temp4.clear();
	
	}
	


public void publishTT(int currentYear) {
	int paperCount=0;	
	for (int i=0;i<4;i++)
	{
		CurrentPapers[i]=0;
		CurrentCitations[i]=0;
	}
		
		for(Researcher researcher : researcherArray) {
							
			if(M.publishingModel=="PoissonMultiplier"){
				paperCount = randomGenerator.createPoisson2(researcher.getTimeAvailableForResearch()*10*researcher.getProductivity());				
			}
			if (paperCount < 0) paperCount = 0; //Prevents negative counts
			if(M.paperQualityModel=="Skill_based"){
				for (int i = 1; i <= paperCount; i++) {
					researcher.papers.add(new Paper(randomGenerator.createLogDistributedRandomValue(0.0, 0.5),1.0, researcher.getResearchSkill(), currentYear, 1, 1));
				}				
			}
			CurrentPapers[researcher.getPositionInOrganization()-1]+=paperCount;
			paperCount = 0;
			for (Paper paper : researcher.papers) {
				CurrentCitations[researcher.getPositionInOrganization()-1]+= paper.updateCitationsTT(currentYear+1);
			}

		}
	}

	private void compareReceivedMoney() {
		for (Researcher researcher : researcherArray) {
			researcher.setTotalFrustration();
			researcher.setProductivity();
		}

	}
	public int getTotalCitations() {
		totalCitations = 0;
		for (Researcher dude : researcherArray) {
			totalCitations += dude.getCitations();
		}
		return totalCitations;
	}

	public int getTotalPapers() {
		totalPapers = 0;
		for (Researcher dude : researcherArray) {
			totalPapers += dude.getPapers();
		}
		return totalPapers;
	}

	public void allocate(){
		if(M.allocationScheme=="Communism") {evenAllocation();}
		if(M.allocationScheme=="Lottery") {randomAllocation();}
		if(M.allocationScheme=="Grant") {grantAllocation(M.arviointiVirhe);} 
		
	}
	

	public void iterate (int count){
		for (int loop=0; loop< count; loop++) 
		{
			RemoveResearchers();
			TTpromote();
			AddResearchers();
			allocate();
			compareReceivedMoney(); //Compare what got and what not, sets frustration and productivity
			publishTT(year);
			CountByLevels();
			mon.updateCounters();
			year++;
		}	
		
	}
	public ArrayList<palaute> simulate(int sisaan)  {

		System.out.println(sisaan);
		
		ArrayList<palaute> palaute = new ArrayList<palaute>();
	//	palaute pala = null;
		
		if (sisaan == 0) { //Ajetaan kaikki kokeet
		
		for(int koe=1; koe<9 ; koe++) 
		{
			M.configure(koe);
			initialize();
			mon.resetCounters();
			iterate(100); //warm up
			mon.reportHeadings();
			for (int sample=0; sample < 10; sample++)
			{
				mon.resetCounters();
				iterate(200); // single sample
		//		String luku[] = mon.report(200);
				for (int i = 0; i <= 3; i++) {
		//		System.out.println(luku[i]);
				}
				iterate(50); //separation

			} //end sampling

			researcherArray.clear();
			year = 0;
		} //end koe
		}
		
		else 	{ //Ajetaan yksitt�inen koe
			
			M.configure(sisaan);
			initialize();
			mon.resetCounters();
			iterate(100); //warm up
			mon.reportHeadings();
			for (int sample=0; sample < 10; sample++)
			{
				mon.resetCounters();
				iterate(200); // single sample
				palaute pala =  mon.report(200);
			//	for (int i = 0; i <= 3; i++) {
					
					palaute.add(pala);
					
			//		System.out.println(pala.getPapers() + "tht ");
		//		System.out.println("dd" + luku[i]);
			//	palaute.add(luku[i]);
				
			//	}
				iterate(50); //separation

			} //end sampling

			researcherArray.clear();
			year = 0;
		}



		return palaute;
	}


	/**
	 * @param args
	
	public static void main(String[] args) {

		//Uusi simu
		simulation test = new simulation();
		
		//Uusi Arraylist
	//	ArrayList<palaute> paltsu = new ArrayList<palaute>();
		
		//Simulaatiomallin ajo
		ArrayList<palaute> paltsu = test.simulate(11);
		
		//Iteeraattorin alustaminen
		Iterator<palaute> iterator = paltsu.iterator();
		
		//Tulostus
		while (iterator.hasNext()) {
			System.out.println(iterator.next().getPapers() + " " +iterator.next().getCitations());
		}


	}
 */
}