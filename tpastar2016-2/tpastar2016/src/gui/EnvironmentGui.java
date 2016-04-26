package gui;

import java.awt.BorderLayout;
import javax.swing.*; 

import environment.Entrepot;


/** window used to display the environment
 * @author emmanuel adam*/
@SuppressWarnings("serial")
public class EnvironmentGui extends JFrame{

    /** environment to display*/
    Entrepot ent;
    
    /**jPanel for displaying results*/
    PanelMethodChoice panelResult;
    
    
    public EnvironmentGui(Entrepot _ent ) {
        ent = _ent;
    	setBounds(10, 10, ent.getWidth()*6+10, ent.getHeight()*5+100);     
    	setTitle("A* resolution for shortest way");
    	buildGui();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    /** add a PanelEnvironment in the center of the window*/
    private void buildGui()
    {
    	
    	getContentPane().setLayout(new BorderLayout());
    	getContentPane().add(BorderLayout.CENTER, new PanelEnvironment( ent.getWidth()*2, ent.getHeight()*2, ent));    		
        panelResult = new PanelMethodChoice(ent);
        getContentPane().add(BorderLayout.SOUTH, panelResult);
    }

	/**
	 * @param fieldClosedNodes the fieldClosedNodes to set
	 */
	public void setFieldClosedNodes(String _fieldClosedNodes) {
		panelResult.setFieldClosedNodes(_fieldClosedNodes);
	}

	/**
	 * @param fieldFreeNodes the fieldFreeNodes to set
	 */
	public void setFieldFreeNodes(String _fieldFreeNodes) {
		panelResult.setFieldFreeNodes(_fieldFreeNodes);
	}

	/**
	 * @param fieldDistance the fieldDistance to set
	 */
	public void setFieldDistance(String _fieldDistance) {
		panelResult.setFieldDistance(_fieldDistance);
	}

	/**
	 * @param fieldDistanceNuisance the fieldDistance with nuisance to set
	 */
	public void setFieldDistanceWithNuisance(String _fieldDistanceWithNuisance) {
		panelResult.setFieldDistanceWithNuisance(_fieldDistanceWithNuisance);
	}

	/**
	 * @param fieldComputeTime the  Compute Time to set
	 */
	public void setFieldComputeTime(String _fieldComputeTime) {
		panelResult.setFieldComputeTime( _fieldComputeTime) ;
	}

}
