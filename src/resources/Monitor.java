package resources;
import java.util.ArrayList;

import ui.*;



public class Monitor {
	private static int[] CumPositionLevels= new int[4];
	private static int[] CumResearchersByLevels= new int[4];
	private static int[] CumPapersByLevels= new int[4];
	private static int[] CumCurrentPapers = new int[4];
	private static int[] CumCurrentCitations = new int[4];	
	private static int[] CumCitationsByLevels= new int[4];
	private static double[] CumSkillByLevels = new double[4];
	private static double[] CumFrustrationByLevels = new double[4];
	private static int[] CumResignByLevels = new int[4];
	private static int[] CumPromoteByLevels = new int[4];
	private static int[] CumRetirementAge = new int[4];


public void resetCounters()
{
	for (int i=0; i<4;i++)
	{
		CumPositionLevels[i]=0;
		CumResearchersByLevels[i]=0;
		CumCitationsByLevels[i]=0;
		CumPapersByLevels[i]=0;
		CumCurrentPapers[i]=0;
		CumCurrentCitations[i]=0;
		CumSkillByLevels[i]=0;
		CumResignByLevels[i]=0;
		CumPromoteByLevels[i]=0;
		CumFrustrationByLevels[i]=0;
		CumRetirementAge[i]=0;
	}
}
public void updateCounters()
{
	for (int i=0;i<4; i++)
	{
		CumResearchersByLevels[i]+=simulation.ResearchersByLevels[i];
		CumPapersByLevels[i]+=simulation.PapersByLevels[i];
		CumCitationsByLevels[i]+=simulation.CitationsByLevels[i];
		CumSkillByLevels[i]+=simulation.SkillByLevels[i];
		CumCurrentCitations[i]+=simulation.CurrentCitations[i];
		CumCurrentPapers[i]+=simulation.CurrentPapers[i];
		CumResignByLevels[i]+=simulation.ResignByLevels[i];
		CumPromoteByLevels[i]+=simulation.PromoteByLevels[i];
		CumFrustrationByLevels[i]+=simulation.FrustrationByLevels[i];
		CumRetirementAge[i]+=simulation.RetirementAge[i];
	}	
}
public void reportHeadings() {
	System.out.println(simulation.M.narrative);
	System.out.println("Case; Ladder; HeadCount; AllPapers; AllCitations; Skill; YearlyPapers; YearlyCitations;  Resignations; Promotions; Frustration; RetirementAge");
}
public palaute report(int y){
	double yd = y;
	
	palaute palaute = new palaute();
	
	//String[] returnString = new String[4];
	
	//omaa
//	ArrayList<Integer> paperit = new ArrayList<Integer>();
	
	for (int i=0;i<4;i++) {
	//	System.out.print(simulation.M.instanssi +"; "+(i+1)+"; "+CumResearchersByLevels[i]/yd+"; "+CumPapersByLevels[i]/yd+"; "+CumCitationsByLevels[i]/yd+"; "+CumSkillByLevels[i]/yd);
	//	System.out.print("; "+CumCurrentPapers[i]/yd+"; "+CumCurrentCitations[i]/yd);
	//	System.out.println("; "+CumResignByLevels[i]/yd+"; "+CumPromoteByLevels[i]/yd+"; "+CumFrustrationByLevels[i]/yd +"; "+CumRetirementAge[i]/yd);
		
	//	System.out.println("dd " + CumPapersByLevels[i]/yd);
		palaute.paperit.add(CumPapersByLevels[i]/yd);
		palaute.sitaatit.add(CumCitationsByLevels[i]/yd);
		palaute.teksti.add(simulation.M.narrative);
		
	//	returnString[i] = simulation.M.instanssi +"; "+(i+1)+"; "+CumResearchersByLevels[i]/yd+"; "+CumPapersByLevels[i]/yd+"; "+CumCitationsByLevels[i]/yd+"; "+CumSkillByLevels[i]/yd +"; "+CumCurrentPapers[i]/yd+"; "+CumCurrentCitations[i]/yd +"; "+CumResignByLevels[i]/yd+"; "+CumPromoteByLevels[i]/yd+"; "+CumFrustrationByLevels[i]/yd +"; "+CumRetirementAge[i]/yd;
		
	}
	
	palaute.citPos1.add(CumCitationsByLevels[0]/yd);
	palaute.citPos2.add(CumCitationsByLevels[1]/yd);
	palaute.citPos3.add(CumCitationsByLevels[2]/yd);
	palaute.citPos4.add(CumCitationsByLevels[3]/yd);
	
	
	
//	System.out.println(M.frustrationGrowthRate);
	/*
	try {
		fileread.writeLines2(addableCitations+"",kuinkaPaljonJaetaanTasan, overhead); //addablePapers + " " + 
	} catch (FileNotFoundException | UnsupportedEncodingException e) {
		e.printStackTrace();
	}
*/
	return palaute;
	
	
	

}

}
