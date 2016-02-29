package resources;

public class Model {
	public String instanssi;
	public String narrative;
//simulation related parameters	
	public String allocationScheme;
	public double arviointiVirhe=0.;
	public double overhead = 0; // 0 - 100%
	public double kuinkaPaljonJaetaanTasan = 0; //0%-100%
	public double maksimiTutkimusResurssi = 0.63; //0.63
	public double kuinkaPaljonMaksimiTutkimisResurssistaHalutaan = 0.63;	
	public int PopulationSize = 100;
	public int[] PositionLevels = new int[4];
	public double AllocatableResource = 35.;
	public String promotionModel;
	public double promotionTreshold;
	public String publishingModel;
	public String paperQualityModel;

// researcher related parameters
	public double oletusTutkimusAika = 0; //0.2325; //aika joka kaikilla k�yt�ss�
	public double neededToBeEffective;
	public double hakemisenOsuus=0.5; // hakemisen aika osuutena oletustutkimusajasta 
	public double defaultMonetaryFrustration = 0;
	public String monetaryFrustrationModel;
	public double frustrationGrowthRate =0.1;
	public double defaultPromotionalFrustration = 0;
	public String promotionalFrustrationModel;
	public int[] promotionalFrustrationAge = new int[4];
	public double[] promotionalFrustrationrate = new double[4];
	public double monetaryFrustrationWeight;
	public double promotionalFrustrationWeight;
	public String productivityModel;
	public String applicationQualityModel;
	
	public String citationModel; // for Paper
	
	public String skillModel; // for random generators
	public double skillParameter;
	public String researchSkillModel;
	public double researchSkillParameter;

	public void reset(){
		allocationScheme="Communism";
		arviointiVirhe=0.;
		overhead = 0; // 0 - 100%
		kuinkaPaljonJaetaanTasan = 0; //0%-100%
		maksimiTutkimusResurssi = 0.63; //0.63
		kuinkaPaljonMaksimiTutkimisResurssistaHalutaan = 0.63;	
		PopulationSize = 100;
		PositionLevels[0] = 35; PositionLevels[1]=25; PositionLevels[2]=25; PositionLevels[3]=15;
		AllocatableResource = 35.;
		promotionModel = "Citation_based";
		promotionTreshold = 1.; //1. = average performance
		publishingModel= "PoissonMultiplier";
		paperQualityModel = "Skill_based";

	// researcher related parameters
		oletusTutkimusAika = 0; //0.2325; //aika joka kaikilla k�yt�ss�
		neededToBeEffective = 0.215;
		hakemisenOsuus=0.5; // hakemisen aika osuutena oletustutkimusajasta 
		defaultMonetaryFrustration = 0;
		frustrationGrowthRate =0.1;
		defaultPromotionalFrustration = 0;
		monetaryFrustrationModel="Linear_Decay";
		promotionalFrustrationModel="Academic_Age";
		promotionalFrustrationAge[0] = 3;
		promotionalFrustrationAge[1] = 7;
		promotionalFrustrationAge[2] = 17;
		promotionalFrustrationAge[3] = 45;
		promotionalFrustrationrate[0] = 0.25;
		promotionalFrustrationrate[1] = 0.167;
		promotionalFrustrationrate[2] = 0.125;
		promotionalFrustrationrate[3] = 0.0;
		monetaryFrustrationWeight=1.;
		promotionalFrustrationWeight=1.;
		productivityModel="Summative";
		applicationQualityModel="Skill";
		
		citationModel="Wang"; // for Paper
		
		skillModel="LogNormal"; // for random generators
		skillParameter=0.35;
		researchSkillModel="LogNormal";
		researchSkillParameter=0.2;
	
		
	}
	public void configure(int malli){
		
		reset(); //aluksi oletuksiin
//		instanssi=malli;
		switch(malli){
		case 1:{ //tasajako
			allocationScheme="1.";	
			kuinkaPaljonJaetaanTasan = 100; //0%-100%
			instanssi="Even distribution";
			break;
		}
		case 2:{//lotto
			allocationScheme="2.";
			kuinkaPaljonJaetaanTasan = 0; //0%-100%	
			instanssi="Lottery";
			break;
		}
		case 3:{//oraakkeli
			allocationScheme="3.";
			kuinkaPaljonJaetaanTasan = 0; //0%-100%	
			instanssi="Oracle";
			break;
		}
		case 4:{// erehtyv� oraakkeli
			allocationScheme="4.";
			arviointiVirhe=1.;
			kuinkaPaljonJaetaanTasan = 0; //0%-100%	
			instanssi="Oracle, err";
			break;
		}
		case 5:{ //tasajako
			allocationScheme="5.";	
			kuinkaPaljonJaetaanTasan = 100; //0%-100%
			instanssi="Even";
			monetaryFrustrationModel="BiExponential";		
			frustrationGrowthRate =0.3;
			break;
		}
		case 6:{//lotto
			allocationScheme="6.";
			kuinkaPaljonJaetaanTasan = 0; //0%-100%	
			instanssi="Lottery";
			monetaryFrustrationModel="BiExponential";
			frustrationGrowthRate =0.3;
			break;
		}
		case 7:{//oraakkeli
			allocationScheme="7.";
			kuinkaPaljonJaetaanTasan = 0; //0%-100%	
			instanssi="Tunnistus";
			monetaryFrustrationModel="BiExponential";
			frustrationGrowthRate =0.3;
			break;
		}
		case 8:{// erehtyv� oraakkeli
			allocationScheme="8.";
			arviointiVirhe=1.;
			kuinkaPaljonJaetaanTasan = 0; //0%-100%	
			monetaryFrustrationModel="BiExponential";
			instanssi="Tunnistus";
			frustrationGrowthRate =0.3;
			break;
		}
		case 10:{//oraakkeli
			allocationScheme="9.";
			kuinkaPaljonJaetaanTasan = 0; //0%-100%	
			frustrationGrowthRate =0.1;
			instanssi="Oracle";
			break;

		}
		case 11:{//oraakkeli
			allocationScheme="10.";
			kuinkaPaljonJaetaanTasan = 0; //0%-100%	
			frustrationGrowthRate =0.08;
			instanssi="Recognition";
			break;

		}
		case 12:{//oraakkeli
			allocationScheme="11.";
			kuinkaPaljonJaetaanTasan = 0; //0%-100%	
			frustrationGrowthRate =0.06;
			monetaryFrustrationWeight=1;
			promotionalFrustrationWeight=1.;
			instanssi="Recognition";
			break;
		}
	
		} //end case
	setNarrative();

	}
	public void setNarrative(){
		narrative="Case "+instanssi+ "\n";
		narrative+="Population size = "+ PopulationSize + " allocatable resource = "+AllocatableResource;
		narrative+=" Allocation Scheme "+allocationScheme+ "\n";
		narrative+="Promotion model is "+promotionModel+ " Publishing model is "+publishingModel; 
	}
}
