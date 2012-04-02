/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.ui.wizard.widgets;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.henshin.interpreter.ui.InterpreterUIPlugin;
import org.eclipse.emf.henshin.interpreter.ui.wizard.ParameterConfiguration;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class ParameterEditTable {
	
	protected int CONTROL_OFFSET = 5;
	
	protected Collection<ParameterChangeListener> listeners = new ArrayList<ParameterEditTable.ParameterChangeListener>();
	
	protected TableViewer tableViewer;
	
	protected Group container;
	
	public ParameterEditTable(Composite parent) {
		container = new Group(parent, SWT.NONE);
		container.setText(InterpreterUIPlugin.LL("_UI_SetParameters"));
		container.setLayout(new FormLayout());
		tableViewer = new TableViewer(container, SWT.FULL_SELECTION);
		{
			FormData data = new FormData();
			data.top = new FormAttachment(0, CONTROL_OFFSET);
			data.left = new FormAttachment(0, CONTROL_OFFSET);
			data.right = new FormAttachment(100, -CONTROL_OFFSET);
			data.bottom = new FormAttachment(100, -CONTROL_OFFSET);
			data.height = 80;
			tableViewer.getTable().setLayoutData(data);
			tableViewer.getTable().setLinesVisible(true);
			tableViewer.getTable().setHeaderVisible(true);
			
		}
		buildColumns();
		
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				
			}
			
			@Override
			public Object[] getElements(Object inputElement) {
				@SuppressWarnings("unchecked")
				Collection<ParameterConfiguration> paramCfgs = (Collection<ParameterConfiguration>) inputElement;
				return paramCfgs.toArray();
			}
			
			@Override
			public void dispose() {
			}
		});
		
	}
	
	public Control getControl() {
		return container;
	}
	
	protected void buildColumns() {
		TableViewerColumn keyColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		{
			keyColumn.getColumn().setText(InterpreterUIPlugin.LL("_UI_ParameterColumn_Name"));
			keyColumn.getColumn().setWidth(100);
			keyColumn.setLabelProvider(new ColumnLabelProvider() {
				
				@Override
				public String getText(Object entry) {
					return ((ParameterConfiguration) entry).getName();
				}
			});
		}
		
		TableViewerColumn typeColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		{
			typeColumn.getColumn().setText(InterpreterUIPlugin.LL("_UI_ParameterColumn_Type"));
			typeColumn.getColumn().setWidth(100);
			typeColumn.setLabelProvider(new ColumnLabelProvider() {
				
				@Override
				public String getText(Object element) {
					ParameterConfiguration paramCfg = (ParameterConfiguration) element;
					return paramCfg.getTypeLabel();
				}
			});
			typeColumn.setEditingSupport(new EditingSupport(tableViewer) {
				
				@Override
				protected void setValue(Object element, Object value) {
					ParameterConfiguration paramCfg = (ParameterConfiguration) element;
					
					paramCfg.setType((Integer) value);
					
					for (ParameterChangeListener l : listeners)
						l.parameterChanged(paramCfg);
					tableViewer.refresh();
				}
				
				@Override
				protected Object getValue(Object element) {
					ParameterConfiguration paramCfg = (ParameterConfiguration) element;
					return paramCfg.getType();
				}
				
				@Override
				protected CellEditor getCellEditor(Object element) {
					
					return new ComboBoxCellEditor(tableViewer.getTable(), ParameterConfiguration
							.getSupportedTypes().values().toArray(new String[0]), SWT.READ_ONLY);
				}
				
				@Override
				protected boolean canEdit(Object element) {
					return true;
				}
			});
		}
		
		TableViewerColumn valueColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		{
			valueColumn.getColumn().setText(InterpreterUIPlugin.LL("_UI_ParameterColumn_Value"));
			valueColumn.getColumn().setWidth(100);
			valueColumn.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					ParameterConfiguration paramCfg = (ParameterConfiguration) element;
					switch (paramCfg.getType()) {
						case ParameterConfiguration.CLEAR:
							return "";
						case ParameterConfiguration.NULL:
							return "null";
						default:
							return paramCfg.getValue().toString();
					}
				}
			});
			valueColumn.setEditingSupport(new EditingSupport(tableViewer) {
				
				@Override
				protected void setValue(Object element, Object value) {
					ParameterConfiguration paramCfg = (ParameterConfiguration) element;
					try {
						switch (paramCfg.getType()) {
							case ParameterConfiguration.STRING:
								paramCfg.setValue(value.toString());
								break;
							case ParameterConfiguration.FLOAT:
								paramCfg.setValue(Float.parseFloat(value.toString()));
								break;
							case ParameterConfiguration.DOUBLE:
								paramCfg.setValue(Double.parseDouble(value.toString()));
								break;
							case ParameterConfiguration.INT:
								paramCfg.setValue(Integer.parseInt(value.toString()));
								break;
							case ParameterConfiguration.LONG:
								paramCfg.setValue(Long.parseLong(value.toString()));
								break;
							case ParameterConfiguration.BOOLEAN:
								paramCfg.setValue((Integer) value > 0 ? true : false);
								break;
							default:
								paramCfg.setValue(value);
						}
						for (ParameterChangeListener l : listeners)
							l.parameterChanged(paramCfg);
						tableViewer.refresh();
					} catch (Exception e) {						
					}
				}
				
				@Override
				protected Object getValue(Object entry) {
					ParameterConfiguration paramCfg = (ParameterConfiguration) entry;
					System.out.println("celleditor getvalue: " + paramCfg.getValue() + "("
							+ paramCfg.getTypeLabel() + ")");
					
					switch (paramCfg.getType()) {
						case ParameterConfiguration.BOOLEAN:
							boolean value = (Boolean) paramCfg.getValue();
							return value ? 1 : 0;
						default:
							return paramCfg.getValue().toString();
					}
					
				}
				
				@Override
				protected CellEditor getCellEditor(Object element) {
					ParameterConfiguration paramCfg = (ParameterConfiguration) element;
					// case ParameterConfiguration.NULL is not editable
					switch (paramCfg.getType()) {
						case ParameterConfiguration.BOOLEAN:
							return new ComboBoxCellEditor(tableViewer.getTable(), new String[] {
									"false", "true" }, SWT.READ_ONLY);
						default:
							// default covers the cases:
							// STRING,INT,LONG,FLOAT,DOUBLE
							return new TextCellEditor(tableViewer.getTable());
					}
				}
				
				@Override
				protected boolean canEdit(Object element) {
					ParameterConfiguration paramCfg = (ParameterConfiguration) element;
					return paramCfg.getType() != ParameterConfiguration.NULL
							&& paramCfg.getType() != ParameterConfiguration.CLEAR;
				}
			});
		}
		
	}
	
	public void addParameterChangeListener(ParameterChangeListener listener) {
		listeners.add(listener);
	}
	
	public static interface ParameterChangeListener {
		void parameterChanged(ParameterConfiguration paramCfg);
	}
	
	public void setParameters(Collection<ParameterConfiguration> paramCfgs) {
		tableViewer.setInput(paramCfgs);
	}
	
}
