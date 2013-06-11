package org.eclipse.emf.henshin.interpreter.ui.giraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class GiraphUtil {
	
	public static class MatchingStep {
		
		public final Node node;
		
		public final Edge edge;

		public final int verifyEdgeTo;

		public final int sendBackTo;

		public MatchingStep(Node node, Edge edge, int sendBackTo, int verifyEdgeTo) {
			this.node = node;
			this.edge = edge;
			this.verifyEdgeTo = verifyEdgeTo;
			this.sendBackTo = sendBackTo;
		}
		
	}
	
	public static List<MatchingStep> getMatchingSteps(Rule rule) {
		for (Node start : rule.getLhs().getNodes()) {
			List<MatchingStep> matchingSteps = getMatchingSteps(rule, start);
			if (matchingSteps!=null) {
				return matchingSteps;
			}
		}
		return null;
	}

	private static List<MatchingStep> getMatchingSteps(Rule rule, Node start) {
		List<MatchingStep> matchingSteps = new ArrayList<GiraphUtil.MatchingStep>();
		if (start.getOutgoing().isEmpty()) {
			return matchingSteps;
		}
		Deque<Edge> edgeQueue = new ArrayDeque<Edge>();
		edgeQueue.addAll(start.getOutgoing());
		Set<Node> lockedNodes = new HashSet<Node>();
		Set<Edge> visitedEdges = new HashSet<Edge>();
		while (!edgeQueue.isEmpty()) {
			Edge edge = edgeQueue.pop();
			
			matchingSteps.add(new MatchingStep(edge.getSource(), 
					edge, -1, getMatchingStepIndex(matchingSteps, edge.getTarget())));
			
			visitedEdges.add(edge);
			lockedNodes.add(edge.getSource());
			lockedNodes.add(edge.getTarget());

			if (edge.getTarget().getOutgoing().isEmpty()) {
				
				// Leaf:
				if (!edgeQueue.isEmpty()) {
					int sendBackTo = getMatchingStepIndex(matchingSteps, edgeQueue.getFirst().getSource());
					matchingSteps.add(new MatchingStep(edge.getTarget(), null, sendBackTo, -1));
				}
				
			} else {
				
				// Intermediate node:
				for (Edge succ : edge.getTarget().getOutgoing()) {
					if (!visitedEdges.contains(succ) && !edgeQueue.contains(succ)) {
						edgeQueue.push(succ);
					}
				}
				
			}
		}
		if (rule.getLhs().getNodes().size()==lockedNodes.size()) {
			return matchingSteps;
		}
		return null;
	}

	private static int getMatchingStepIndex(List<MatchingStep> matchingSteps, Node node) {
		for (int i=0; i<matchingSteps.size(); i++) {
			if (matchingSteps.get(i).node==node) {
				return i;
			}
		}
		return -1;
	}
	
	public static String getNodeName(Node node) {
		return node.getName()!=null && node.getName().trim().length()>0 ? 
				"\""+node.getName()+"\"" : 
				"" + node.getGraph().getNodes().indexOf(node);
	}
	
	public static String getTypeConstantName(ENamedElement type) {
		EPackage pack = (EPackage) type.eContainer();
		return camelCase2Upper(pack.getName()) + "_" + camelCase2Upper(type.getName());
	}

	public static Map<ENamedElement,String> getTypeConstants(Module module) {
		Map<ENamedElement,String> map = new LinkedHashMap<ENamedElement, String>();
		for (EPackage pack : module.getImports()) {
			for (EClassifier classifier : pack.getEClassifiers()) {
				if (!(classifier instanceof EClass)) {
					continue;
				}
				String baseName = camelCase2Upper(pack.getName()) + "_" + camelCase2Upper(classifier.getName());
				map.put(classifier, baseName);
				for (EReference ref : ((EClass) classifier).getEAllReferences()) {
					map.put(ref, baseName + "_" + camelCase2Upper(ref.getName()));
				}
			}
		}
		return map;
	}

	public static String camelCase2Upper(String s) {
		String r = "";
		boolean u = false;
		for (int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			char C = Character.toUpperCase(c);
			if (Character.isUpperCase(c)) {
				r = r + (u ? ("_"+C) : C);
			} else {
				u = true;
				r = r + C;
			}
		}
		return r;
	}
	
}
