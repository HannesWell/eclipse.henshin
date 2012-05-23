package org.eclipse.emf.henshin.interpreter;

import org.eclipse.emf.henshin.interpreter.impl.InterpreterFactoryImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * Factory interface for the Henshin interpreter.
 * @author Christian Krause
 */
public interface InterpreterFactory {
	
	/**
	 * Static factory instance.
	 */
	final static InterpreterFactory INSTANCE = new InterpreterFactoryImpl();
	
	/**
	 * Create a new {@link EGraph} object.
	 * @return A new {@link EGraph}.
	 */
	EGraph createEGraph();

	/**
	 * Create an {@link Assignment} object.
	 * @param unit Target {@link TransformationUnit}.
	 * @return A new {@link Assignment}.
	 */
	Assignment createAssignment(TransformationUnit unit);
	
	/**
	 * Create a {@link Match}.
	 * @param Rule to be matched.
	 * @return A new {@link Match}.
	 */
	Match createMatch(Rule rule);

	/**
	 * Create a result {@link Match}.
	 * @param Rule to be matched.
	 * @return A new result {@link Match}.
	 */
	Match createResultMatch(Rule rule);

	/**
	 * Create an {@link Engine} object.
	 * @return A new {@link Engine}.
	 */
	Engine createEngine();
	
	/**
	 * Create a new {@link UnitApplication}.
	 * @param engine {@link Engine} to be used.
	 * @return A new {@link UnitApplication}.
	 */
	UnitApplication createUnitApplication(Engine engine);

	/**
	 * Create a new {@link RuleApplication}.
	 * @param engine {@link Engine} to be used.
	 * @return A new {@link RuleApplication}.
	 */
	RuleApplication createRuleApplication(Engine engine);
	
	/**
	 * Create an {@link ApplicationMonitor}.
	 * @return A new {@link ApplicationMonitor}.
	 */
	ApplicationMonitor createApplicationMonitor();
	
}
