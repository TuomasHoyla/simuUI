package ui;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.TextArea;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.util.Rotation;
import resources.palaute;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenu;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JMenuItem;


public class UI3 extends JFrame  {

	private static final long serialVersionUID = 1L;



	JFreeChart chart3 = null;
	private JPanel contentPane;
	static simulation simu;
	private TextArea mainTextPanel = new TextArea();
	List<Double> cumulativeScores = new ArrayList<Double>();
	Calendar now = Calendar.getInstance();   // This gets the current date and time.
	int year = now.get(Calendar.YEAR);
	int lukemaValitsin = 0;
	
	JButton btnStart;


	/**
	 * Creates the dataserie for display
	 * @param mean
	 * @return
	 */
	private double[][] createSeries(int mean) {
		double[][] series = new double[2][cumulativeScores.size()];
		for (int i = 0; i < cumulativeScores.size(); i++) {
			series[0][i] = i+year;// (double) i;
			series[1][i] = cumulativeScores.get(i);
		}
		return series;
	}


    private JFreeChart createPieChart(PieDataset dataset) {
        
        JFreeChart chart = ChartFactory.createPieChart3D(
        	"Sitation production by positions",          		// chart title
            dataset,                // data
            true,                   // include legend
            true,
            false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
        
    }
	private JFreeChart createMeanScientificOutputChart(final CategoryDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart(
				"Average citations",         // chart title
				"Samples",               // domain axis label
				"Average scientific output", // range axis label
				dataset,                  // data
				PlotOrientation.VERTICAL, // orientation
				false,                     // include legend
				true,                     // tooltips?
				false                     // URLs?
				);


		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines...
		final BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		// set up gradient paints for series...
		final GradientPaint gp0 = new GradientPaint(
				0.0f, 0.0f, Color.blue, 
				0.0f, 0.0f, Color.lightGray
				);
		final GradientPaint gp1 = new GradientPaint(
				0.0f, 0.0f, Color.green, 
				0.0f, 0.0f, Color.lightGray
				);
		final GradientPaint gp2 = new GradientPaint(
				0.0f, 0.0f, Color.red, 
				0.0f, 0.0f, Color.lightGray
				);
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(
				CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
				);
		return chart;

	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//Set look and feel...
				try {
					String laf = UIManager.getSystemLookAndFeelClassName();
					try {
						UIManager.setLookAndFeel(laf);
					} catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException | UnsupportedLookAndFeelException e1) {

						e1.printStackTrace();
					}
					//...ends here
					UI3 frame = new UI3();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


	}

	/**
	 * Create the frame.
	 */
	public UI3() {
		setTitle("Simulation");

		final DefaultXYDataset dataset = new DefaultXYDataset();
		final DefaultCategoryDataset meanDataset = new DefaultCategoryDataset();
		final DefaultPieDataset pieDataset = new DefaultPieDataset();
		JFreeChart chart2 = createMeanScientificOutputChart(meanDataset);
		JFreeChart chartPie = createPieChart(pieDataset);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,1044,600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnParameters = new JMenu("Funding model");
		menuBar.add(mnParameters);
		
		JRadioButtonMenuItem rdbtn1 = new JRadioButtonMenuItem("1. Even distribution");
		mnParameters.add(rdbtn1);
		
		rdbtn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lukemaValitsin = 1;
			}
		});
		//group.add(rdbtn1);
		
		JRadioButtonMenuItem rdbtn2 = new JRadioButtonMenuItem("2. Lottery");
		mnParameters.add(rdbtn2);
		
		rdbtn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lukemaValitsin = 2;
			}
		});
		
	    JRadioButtonMenuItem rdbtn3 = new JRadioButtonMenuItem("3. Skill recognized, no error");
	    mnParameters.add(rdbtn3);
	    
		rdbtn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 lukemaValitsin = 3;
			}
		});
	    
	    JRadioButtonMenuItem rdbtn4 = new JRadioButtonMenuItem("4. Skill recognized, no error");
	    mnParameters.add(rdbtn4);
	    
		rdbtn4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 lukemaValitsin = 4;
			}
		});
	    
	    JRadioButtonMenuItem rdbtn5 = new JRadioButtonMenuItem("5. Even distribution, with frustration");
	    mnParameters.add(rdbtn5);
	    
		rdbtn5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 lukemaValitsin = 5;
			}
		});
	    
	    JRadioButtonMenuItem rdbtn6 = new JRadioButtonMenuItem("6. Lottery with frustration");
	    mnParameters.add(rdbtn6);
	    
		rdbtn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 lukemaValitsin = 6;
			}
		});
	    
	    JRadioButtonMenuItem rdbtn7 = new JRadioButtonMenuItem("7. Skill recognized, fast frustration, no error");
	    mnParameters.add(rdbtn7);
	    
		rdbtn7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 lukemaValitsin = 7;
			}
		});
	    
	    JRadioButtonMenuItem rdbtn8 = new JRadioButtonMenuItem("8. Skill recognized, fast frustration, with error");
	    mnParameters.add(rdbtn8);
	    
		rdbtn8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 lukemaValitsin = 8;
			}
		});
	    
	    JRadioButtonMenuItem rdbtn10 = new JRadioButtonMenuItem("9. Skill recognized, average frustration, no error");
	    mnParameters.add(rdbtn10);
	    
		rdbtn10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 lukemaValitsin = 10;
			}
		});
	    
	    JRadioButtonMenuItem rdbtn11 = new JRadioButtonMenuItem("10. Skill recognized, slow frustration");
	    mnParameters.add(rdbtn11);
	    
		rdbtn11.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 lukemaValitsin = 11;
			}
		});
	    
	    JRadioButtonMenuItem rdbtn12 = new JRadioButtonMenuItem("11. Skill recognized, very slow frustration");
	    mnParameters.add(rdbtn12);
	    
		rdbtn12.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 lukemaValitsin = 12;
			}
		});
		
	    ButtonGroup group = new ButtonGroup();
	    
	    group.add(rdbtn1);
	    group.add(rdbtn2);
	    group.add(rdbtn3);
	    group.add(rdbtn4);
	    group.add(rdbtn5);
	    group.add(rdbtn6);
	    group.add(rdbtn7);
	    group.add(rdbtn8);
	    group.add(rdbtn10);
	    group.add(rdbtn11);
	    group.add(rdbtn12);
	    
	    JMenu mnNewMenu = new JMenu("About...");
	    menuBar.add(mnNewMenu);
	    
	    JMenuItem mntmNewMenuItem = new JMenuItem("About");
	    mnNewMenu.add(mntmNewMenuItem);
	    
	    mntmNewMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainTextPanel.append("Simulation for grant resource allocation.");
				
			}
		});
	    
	  //  group.add(centerJustify);
	  //  group.add(fullJustify);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane);
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		JPanel panel_1 = new JPanel();
		splitPane_1.setLeftComponent(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_1.add(mainTextPanel);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(22)
					.addComponent(splitPane_1, GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(105, Short.MAX_VALUE))
		);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_2);

		JPanel panel_3 = new JPanel();
		splitPane_2.setLeftComponent(panel_3);

		btnStart = new JButton("START");
		panel_3.add(btnStart);

		JButton btnNewButton = new JButton("Clear screen");
		panel_3.add(btnNewButton);
		
		JButton btnRemoveBar = new JButton("Remove Bar");
		panel_3.add(btnRemoveBar);
		ChartPanel chartPiePanel = new ChartPanel(chartPie);
		splitPane_2.setRightComponent(chartPiePanel);
		chartPiePanel.setPreferredSize(new Dimension(600,300));

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//		dataset.removeSeries();
				//			int n = dataset.getSeriesCount() - 1;
				//		dataset.removeSeries("Output " + n);
				cumulativeScores.clear();
				mainTextPanel.setText(null);
				mainTextPanel.repaint();
				mainTextPanel.setText("");
				dataset.removeSeries("Mean");
			
				
				//		dataset.
				for (int i = 0; i <= 500; i++) {
					dataset.removeSeries("Output " +i);
					
				}
					
			}
		});
		
		btnRemoveBar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			//	do {
					if (meanDataset.getColumnCount() != 0) meanDataset.removeColumn(meanDataset.getColumnCount()-1);
			//	} while (meanDataset.getColumnCount() == 0);
				
			}
		});


		btnStart.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				pieDataset.clear();

				if (lukemaValitsin != 0 && lukemaValitsin < 13) {
				
				simu = new simulation();

				ArrayList<palaute> paltsuLista = simu.simulate(lukemaValitsin);
				
				ArrayList<Double> sitaatit = new ArrayList<Double>();
				ArrayList<Double> paperit = new ArrayList<Double>();
				
				double citPos1 = 0;
				double citPos2 = 0;
				double citPos3 = 0;
				double citPos4 = 0;

				String teksti = new String();
				
				for (int i = 0; i < paltsuLista.size(); i++) {
					
					citPos1 += paltsuLista.get(i).getCitationsPos1().get(0);
					citPos2 += paltsuLista.get(i).getCitationsPos2().get(0);
					citPos3 += paltsuLista.get(i).getCitationsPos3().get(0);
					citPos4 += paltsuLista.get(i).getCitationsPos4().get(0);
					
					for (int j = 0; j < paltsuLista.get(0).getCitations().size(); j++) {
			//			if (j % 4 == 0)System.out.println("jaolliset " + paltsuLista.get(0).getCitations().get(j) +" " +  paltsuLista.size());
						
						sitaatit.add(paltsuLista.get(i).getCitations().get(j));
						paperit.add(paltsuLista.get(i).getPapers().get(j));
						teksti = paltsuLista.get(0).getText().get(0);
					}
				}
				
				pieDataset.setValue("Post-doc", citPos1/10);
				pieDataset.setValue("Assistant professor", citPos2/10);
				pieDataset.setValue("Associate professor", citPos3/10);
				pieDataset.setValue("Full professor", citPos4/10);
				
				
				
				double sitaatitKA = 0;
			//	double paperitKA = 0;
				
				for (double cite : sitaatit) {
					sitaatitKA += cite;
				}
				
			//	for (double paper : paperit) {
			//		paperitKA += paper;
			//	}
				
				sitaatitKA = sitaatitKA/40;
			//	paperitKA = paperitKA/40;

				cumulativeScores.add(sitaatitKA);
				int n = dataset.getSeriesCount();
				dataset.addSeries("Output " + n, createSeries(n));
				

				int luku = n+1;
				meanDataset.addValue(sitaatitKA, "category1", teksti+ " " + luku);
			//	meanDataset.addValue(paperitKA, "category2", teksti+ " " + luku);
				mainTextPanel.append("Dataset: " + teksti +  "\n" +cumulativeScores.toString() + "\n");
				
				mainTextPanel.append("Citation distribution between positions: \nPost-doc: " + (int)citPos1/10 + "\nAssistant professor: " + (int)citPos2/10 + "\nAssociate professor: " + (int)citPos3/10 + "\nFull professor: " + (int)citPos4/10);
				mainTextPanel.append("\n------------------------------------------------------------------------------------------\n");
				}
				else {
					mainTextPanel.append("Please choose simulated model from top-left menu \n");
					mainTextPanel.setText(null);
					mainTextPanel.repaint();
				}
			}
		

		});
		
		/**
		 * Eka chart
		 */
		panel.setLayout(gl_panel);
		
		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		
		JSplitPane splitPane_4 = new JSplitPane();
		panel_2.add(splitPane_4);
		
	//	splitPane_4.setBorder(BorderFactory.createTitledBorder("Consommation"));
		splitPane_4.setPreferredSize(new Dimension(500, 400)); 
		
		splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JPanel panel_4 = new JPanel();
		splitPane_4.setLeftComponent(panel_4);
		
		ChartPanel chartPanelFinal = new ChartPanel(chart2, false);
		splitPane_4.setRightComponent(chartPanelFinal);
		chartPanelFinal.setBounds(0, 0, 670, 470);
		chartPanelFinal.setLayout(new BorderLayout(0, 0));
		
		/**
		 * Toinen chart
		 */

	}

}