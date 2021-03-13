/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package de.unimarburg.swt.ocl2ac.utils.printer.actions;

import java.io.File;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.presentation.HenshinEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.unimarburg.swt.ocl2ac.utils.printer.CoreCommand;
import de.unimarburg.swt.ocl2ac.utils.printer.HenshinNACPrinter;
import graph.util.extensions.Constants;

public class HenshinNACPrintOpenActionShortVersion implements IObjectActionDelegate {

	// Workbench part:
	protected IWorkbenchPart workbenchPart;
	// Henshin rule:
	protected Rule rule;

	private CoreCommand wincmd;

	@Override
	public void run(IAction action) {
		print();
	}

	protected void print() {

		HenshinNACPrinter henshinNACPrinter = new HenshinNACPrinter(rule, rule.eClass().getEPackage(), true);
		henshinNACPrinter.printDocument();

		String filepath = henshinNACPrinter.getOutputFilePath();

		Thread thread = new Thread() {
			public void run() {
				System.out.println("Thread Running");
				compileLatex2PDF(filepath);
				File texFile = new File(filepath);
				int lastIndexOf = texFile.getName().lastIndexOf(Constants.TEX);
				String pdfFileName = texFile.getName().substring(0, lastIndexOf).concat(".pdf");
				File pdfFile = new File(texFile.getParent(), pdfFileName);

				if (!pdfFile.exists()) {
					System.err.println("The PDF file of the generated latex is not produced.");
				} else {
					openPDF(pdfFile);
				}
			}
		};
		thread.start();
	}

	private void openPDF(File pdfFile) {
		if (pdfFile.exists()) {
			wincmd = new CoreCommand();
			wincmd.desktopRun(pdfFile);
		} else {
			System.out.println("The PDF file is not found");
		}
	}

	private void compileLatex2PDF(String filePath) {
		Path p = new Path(filePath);
		wincmd = new CoreCommand();
		wincmd.executePDFLatexCommand(p.toOSString(), p.toFile().getParent());
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		rule = null;
		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof Rule) {
				rule = (Rule) first;
			}
		}
		action.setEnabled(rule != null);
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart part) {
		workbenchPart = (part instanceof HenshinEditor) ? part : null;
		action.setEnabled(workbenchPart != null);
	}

}
