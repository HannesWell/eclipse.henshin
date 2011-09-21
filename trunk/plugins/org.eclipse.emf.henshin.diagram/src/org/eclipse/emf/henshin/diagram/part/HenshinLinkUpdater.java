package org.eclipse.emf.henshin.diagram.part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.diagram.edit.parts.InvocationEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.LinkEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.SymbolType;
import org.eclipse.emf.henshin.diagram.providers.HenshinViewProvider;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Update methods for link views.
 * @see LinkEditPart
 * @author Christian Krause
 */
public class HenshinLinkUpdater {

	// Preferences hint:
	private PreferencesHint prefHint;
	
	// Persisted flag:
	private boolean persisted;
	
	// View provider:
	private HenshinViewProvider provider;
	
	// Symbol helper:
	private HenshinSymbolUpdater symbolHelper;
	
	/**
	 * Default constructor.
	 * @param prefHint preferences hint.
	 * @param persisted persisted flag.
	 */
	public HenshinLinkUpdater(PreferencesHint prefHint, boolean persisted) {
		this.prefHint = prefHint;
		this.persisted = persisted;
		this.provider = new HenshinViewProvider();
		this.symbolHelper = new HenshinSymbolUpdater(prefHint, persisted);
	}
	
	public void update(View unitView) {
		
		// Get the compartment view:
		View compartment = symbolHelper.getUnitCompartment(unitView);
		
		// Get the unit and its sub-units:
		TransformationUnit unit = (TransformationUnit) ((View) compartment.eContainer()).getElement();
		EList<TransformationUnit> subUnits = unit.getSubUnits(false);
		
		// Get the begin and the end symbol:
		View begin = getSymbol(unit, compartment, SymbolType.UNIT_BEGIN);
		View end = getSymbol(unit, compartment, SymbolType.UNIT_END);

		// Get all invocations in the unit view:
		List<View> invocations = getInvocations(compartment, subUnits);

		// Known links:
		Set<Edge> knownLinks = new HashSet<Edge>();
		
		/* NOW WE ARE READY TO UPDATE THE LINKS */
		
		// Sequential and priority units:
		if (unit instanceof SequentialUnit ||
			unit instanceof PriorityUnit) {
			
			// Update the links:
			if (subUnits.isEmpty()) {
				knownLinks.add(updateLink(unit, begin, end));
			} else {
				int count = invocations.size();
				knownLinks.add(updateLink(unit, begin, invocations.get(0)));
				knownLinks.add(updateLink(unit, invocations.get(count-1), end));
				for (int i=1; i<count; i++) {
					knownLinks.add(updateLink(unit, invocations.get(i-1), invocations.get(i)));
				}
			}
		}
		
		// Independent units:
		if (unit instanceof IndependentUnit) {
				
			// Update the links:
			if (subUnits.isEmpty()) {
				knownLinks.add(updateLink(unit, begin, end));
			} else {
				for (View invocation : invocations) {
					knownLinks.add(updateLink(unit, begin, invocation));
					knownLinks.add(updateLink(unit, invocation, end));
				}
			}
		}
		
		// Delete unknown links:
		Set<View> nodes = new HashSet<View>(invocations.size()+1);
		for (View node : invocations) {
			if (node!=null) nodes.add(node);
		}
		if (begin!=null) nodes.add(begin);
		if (end!=null) nodes.add(end);
		deleteUnknownLinks(unitView.getDiagram(), knownLinks, nodes);

	}
	
	/*
	 * Make sure there exists exactly one link between two nodes.
	 */
	private Edge updateLink(TransformationUnit unit, View source, View target) {
		if (source!=null && target!=null) {
			List<Edge> links = getLinks(source, target);
			if (links.isEmpty()) {
				Edge link = provider.createLink_4002(source.getDiagram(), -1, persisted, prefHint);
				link.setElement(unit);
				link.setSource(source);
				link.setTarget(target);
				return link;
			} else {
				while (links.size()>1) {
					ViewUtil.destroy(links.remove(links.size()-1));
				}
				return links.get(0);
			}
		}
		return DUMMY_EDGE;
	}

	private void deleteUnknownLinks(Diagram diagram, Collection<Edge> knownLinks, Collection<View> nodes) {
		for (Object link : diagram.getEdges().toArray()) {
			if (knownLinks.contains(link)) continue;
			if (nodes.contains(((Edge) link).getSource()) && 
				nodes.contains(((Edge) link).getTarget())) {
				ViewUtil.destroy((Edge) link);
			}
		}
	}

	private List<Edge> getLinks(View source, View target) {
		List<Edge> links = new ArrayList<Edge>();
		if (source!=null && target!=null) {
			for (Object obj : source.getDiagram().getEdges()) {
				Edge edge = (Edge) obj;
				if (LINK_VISUAL_ID.equals(edge.getType()) 
						&& source==edge.getSource()
						&& target==edge.getTarget()) {
					links.add(edge);
				}
			}
		}
		return links;
	}
	
	private View getSymbol(TransformationUnit unit, View compartment, SymbolType type) {
		if (unit==null || compartment==null || type==null) {
			return null;
		}
		List<View> symbols = symbolHelper.getSymbols(unit, compartment, type);
		return (symbols.isEmpty()) ? null : symbols.get(0);
	}

	private List<View> getInvocations(View compartment, List<TransformationUnit> targets) {
		List<View> invocations = new ArrayList<View>(targets.size());
		for (TransformationUnit unit : targets) {
			invocations.add(getInvocation(compartment, unit, invocations));
		}
		return invocations;
	}

	private View getInvocation(View compartment, TransformationUnit target, Collection<View> exclude) {
		if (compartment==null || target==null) {
			return null;
		}
		for (Object obj : compartment.getChildren()) {
			View view = (View) obj;
			if (view.getElement()==target && 
				INVOCATION_VISUAL_ID.equals(view.getType()) &&
				!exclude.contains(view)) {
				return view;
			}
		}
		return null;
	}
	
	private static final String INVOCATION_VISUAL_ID = HenshinVisualIDRegistry.getType(InvocationEditPart.VISUAL_ID);
	private static final String LINK_VISUAL_ID = HenshinVisualIDRegistry.getType(LinkEditPart.VISUAL_ID);
	
	private static final Edge DUMMY_EDGE = NotationFactory.eINSTANCE.createEdge();
	
}
