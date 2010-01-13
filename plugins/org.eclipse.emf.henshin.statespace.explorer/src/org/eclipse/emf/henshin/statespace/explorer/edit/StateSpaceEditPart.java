package org.eclipse.emf.henshin.statespace.explorer.edit;

import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.impl.StateSpacePackageImpl;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.SWT;

/**
 * State space diagram edit part.
 * @generated NOT
 * @author Christian Krause
 */
public class StateSpaceEditPart extends AbstractGraphicalEditPart implements Adapter {
	
	// State space manager to be used:
	private StateSpaceManager manager;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getStateSpace().eAdapters().add(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			getStateSpace().eAdapters().remove(this);
			super.deactivate();
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new StateSpaceLayoutEditPolicy());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		
		// Create free form layer:
		Figure layer = new FreeformLayer();
		layer.setBorder(new MarginBorder(3));
		layer.setLayoutManager(new FreeformLayout());
		
		layer.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent me) {
			}
			
			public void mousePressed(MouseEvent me) {
			}
			
			public void mouseDoubleClicked(MouseEvent me) {
				System.out.println("double click: " + me);
			}
		});
		
		// Create the static router for the connection layer
		ConnectionLayer connections = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		connections.setConnectionRouter(new ShortestPathConnectionRouter(layer));
		connections.setAntialias(SWT.ON);

		return layer;
		
	}
		
	/**
	 * Get the state space corresponding to this edit part.
	 * @return State space.
	 */
	public StateSpace getStateSpace() {
		return (StateSpace) getModel();
	}
	
	public StateSpaceManager getStateSpaceManager() {
		return manager;
	}
	
	public void setStateSpaceManager(StateSpaceManager manager) {
		this.manager = manager;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected List getModelChildren() {
		return getStateSpace().getStates();
	}

	
	/* -------------------- *
	 * --- Notification --- *
	 * -------------------- */
	
	private Notifier target;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.Adapter#getTarget()
	 */
	public Notifier getTarget() {
		return target;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.Adapter#isAdapterForType(java.lang.Object)
	 */
	public boolean isAdapterForType(Object type) {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void notifyChanged(Notification event) {
		switch (event.getFeatureID(StateSpace.class)) {	
		case StateSpacePackageImpl.STATE_SPACE__STATES: 
			refreshChildren(); break;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.Adapter#setTarget(org.eclipse.emf.common.notify.Notifier)
	 */
	public void setTarget(Notifier target) {
		this.target = target;
	}

}