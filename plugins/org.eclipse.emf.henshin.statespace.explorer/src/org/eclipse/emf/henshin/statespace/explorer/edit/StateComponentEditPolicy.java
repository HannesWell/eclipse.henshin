package org.eclipse.emf.henshin.statespace.explorer.edit;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.commands.DeleteStateCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * Component edit policy for states.
 * @generated NOT
 * @author Christian Krause
 */
public class StateComponentEditPolicy extends ComponentEditPolicy {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		
		StateEditPart stateEditPart = (StateEditPart) getHost();
		State state = stateEditPart.getState();
		StateSpaceManager manager = stateEditPart.getStateSpaceManager();
		
		return new DeleteStateCommand(state, manager);
		
	}
	
}
