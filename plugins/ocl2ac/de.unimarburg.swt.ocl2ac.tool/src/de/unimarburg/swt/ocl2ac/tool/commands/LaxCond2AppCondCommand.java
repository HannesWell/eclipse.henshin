/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package de.unimarburg.swt.ocl2ac.tool.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;

import de.unimarburg.swt.ocl2ac.gc2ac.core.Integrator;
import de.unimarburg.swt.ocl2ac.gc2ac.core.Lefter;
import de.unimarburg.swt.ocl2ac.ocl2gc.util.TranslatorResourceSet;
import laxcondition.Condition;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;

public class LaxCond2AppCondCommand {

	private static final String HENSHIN = ".henshin";
	private IFile henshinFile = null;
	private Rule rule = null;
	private Condition compactCondition;
	public long translationTime = 0;

	public LaxCond2AppCondCommand() {
	}

	public LaxCond2AppCondCommand(IFile henshinFile) {
		this.henshinFile = henshinFile;
	}

	/*
	 * Start the integration
	 */
	public void integrateAndleft(Condition compactCondition, Rule rule) {
		if (rule != null) {
			this.rule = rule;
			this.compactCondition = compactCondition;

			Module module = rule.getModule();
			Copier copy = new Copier();

			Rule ruleCopyOriginal = (Rule) copy.copy(rule);
			copy.copyReferences();

			String UpdatedRuleName = rule.getName() + "_updated_" + compactCondition.getName();
			if (module.getUnit(UpdatedRuleName) != null) {
				Date date = new GregorianCalendar().getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
				UpdatedRuleName += sdf.format(date);
			}

			rule.setName(UpdatedRuleName);

			long start = System.currentTimeMillis();

			// Phase I: Compact condition to ngc
			LaxCond2GCCommand compactCondition2NGC = new LaxCond2GCCommand();
			NestedConstraint nestedConstraint = compactCondition2NGC.translate(compactCondition);

			// Phase II: Integrate, i.e., shifting->prepare->translate2formula
			Integrator integrator = new Integrator(nestedConstraint, rule);
			integrator.integrate();

			NestedCondition condition = integrator.getShiftedCondition();
			nestedConstraint.setDomain(condition.getDomain());
			nestedConstraint.setCondition(condition);

			org.eclipse.emf.henshin.model.Formula existingFormula = rule.getLhs().getFormula();

			// left the RAC to LAC
			Lefter lefter = new Lefter(rule);
			lefter.left();

			// add the original nac of the rule to the result
			if (existingFormula != null) {
				And henshinAnd = HenshinFactory.eINSTANCE.createAnd();
				henshinAnd.setLeft(existingFormula);
				henshinAnd.setRight(rule.getLhs().getFormula());
				rule.getLhs().setFormula(henshinAnd);
			}

			long stop = System.currentTimeMillis();
			translationTime = stop - start;

			// Persist in Place
			module.getUnits().add(ruleCopyOriginal);
			persistTheUpdatedRuleInPlace(module);
		}
	}

	private void persistTheUpdatedRuleInPlace(Module module) {
		String path = henshinFile.getLocation().toOSString();
		TranslatorResourceSet resourceSet = new TranslatorResourceSet(path);
		resourceSet.saveEObject(module, path);
		try {
			henshinFile.getParent().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
