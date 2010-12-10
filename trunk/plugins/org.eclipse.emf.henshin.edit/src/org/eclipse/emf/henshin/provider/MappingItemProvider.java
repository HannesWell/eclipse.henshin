/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.emf.henshin.provider.descriptors.MappingImagePropertyDescriptor;
import org.eclipse.emf.henshin.provider.descriptors.MappingOriginPropertyDescriptor;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.henshin.model.Mapping} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class MappingItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider,
		IItemPropertySource {
	
	NodeListener nodeListener;
	
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public MappingItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
		
		nodeListener = new NodeListener();
	}
	
	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);
			
			addOriginPropertyDescriptor(object);
			addImagePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}
	
	/**
	 * This adds a property descriptor for the Origin feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	protected void addOriginPropertyDescriptor(Object object) {
		
		itemPropertyDescriptors.add(new MappingOriginPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Mapping_origin_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_Mapping_origin_feature",
						"_UI_Mapping_type"), HenshinPackage.Literals.MAPPING__ORIGIN));
		
		// itemPropertyDescriptors.add
		// (createItemPropertyDescriptor
		// (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
		// getResourceLocator(),
		// getString("_UI_Mapping_origin_feature"),
		// getString("_UI_PropertyDescriptor_description",
		// "_UI_Mapping_origin_feature", "_UI_Mapping_type"),
		// HenshinPackage.Literals.MAPPING__ORIGIN,
		// true,
		// false,
		// true,
		// null,
		// null,
		// null));
	}
	
	/**
	 * This adds a property descriptor for the Image feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	protected void addImagePropertyDescriptor(Object object) {
		
		itemPropertyDescriptors.add(new MappingImagePropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Mapping_image_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_Mapping_image_feature",
						"_UI_Mapping_type"), HenshinPackage.Literals.MAPPING__IMAGE));
		
		// itemPropertyDescriptors.add
		// (createItemPropertyDescriptor
		// (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
		// getResourceLocator(),
		// getString("_UI_Mapping_image_feature"),
		// getString("_UI_PropertyDescriptor_description",
		// "_UI_Mapping_image_feature", "_UI_Mapping_type"),
		// HenshinPackage.Literals.MAPPING__IMAGE,
		// true,
		// false,
		// true,
		// null,
		// null,
		// null));
	}
	
	/**
	 * This returns Mapping.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Mapping"));
	}
	
	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		Mapping mapping = (Mapping) object;
		String result = getString("_UI_Mapping_type");
		if (mapping.getOrigin() != null && mapping.getImage() != null) {
			String origin = (mapping.getOrigin() != null) ? NodeItemProvider.getNodeLabel(mapping
					.getOrigin()) : "null";
			String image = (mapping.getImage() != null) ? NodeItemProvider.getNodeLabel(mapping
					.getImage()) : "null";
			if (origin != null && image != null) {
				result = result + " " + origin + " -> " + image;
			}
		}
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#getParent(java.lang
	 * .Object)
	 */
	@Override
	public Object getParent(Object object) {
		
		Object o = super.getParent(object);
		if (o instanceof AmalgamationUnit) {
			AmalgamationUnit au = (AmalgamationUnit) o;
			AmalgamationUnitItemProvider auIp = (AmalgamationUnitItemProvider) adapterFactory
					.adapt(au, IEditingDomainItemProvider.class);
			if (au.getLhsMappings().contains(object)) {
				return auIp != null ? auIp.getLhsMappingsItemProvider() : null;
			} else {
				return auIp != null ? auIp.getRhsMappingsItemProvider() : null;
			}
		} else {
			return super.getParent(object);
		}
	}// getParent
	
	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void notifyChanged(Notification notification) {
		
		if (notification.getNotifier() == this.getTarget()) {
			/*
			 * Refresh the corresponding Nodes if the Mapping changes
			 */
			if (notification.getEventType() == Notification.SET) {
				
				Node n1 = (Node) notification.getNewValue();
				Node n2 = (Node) notification.getOldValue();
				
				// refresh node labels
				notifyNodeForRefresh(notification, n1);
				notifyNodeForRefresh(notification, n2);
				// refresh the mapping label itself
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(),
						false, true));
				
				removeNodeListener(n2);
				addNodeListener(n1);
				
			}// if
		}
		updateChildren(notification);
		super.notifyChanged(notification);
	}
	
	/**
	 * @param node
	 */
	private void addNodeListener(Node node) {
		if (node != null) {
			ItemProviderAdapter adapter = (ItemProviderAdapter) this.adapterFactory.adapt(node,
					Node.class);
			adapter.addListener(nodeListener);
		}// if
	}// addNodeListener
	
	/**
	 * @param node
	 */
	private void removeNodeListener(Node node) {
		if (node != null) {
			ItemProviderAdapter adapter = (ItemProviderAdapter) this.adapterFactory.adapt(node,
					Node.class);
			adapter.removeListener(nodeListener);
		}// if
	}// removeNodeListener
	
	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}
	
	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return HenshinEditPlugin.INSTANCE;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#unsetTarget(org.eclipse
	 * .emf.common.notify.Notifier)
	 */
	@Override
	public void unsetTarget(Notifier target) {
		super.unsetTarget(target);
		// remove node listeners, if mappings are no longer visible
		Mapping mapping = (Mapping) target;
		removeNodeListener(mapping.getImage());
		removeNodeListener(mapping.getOrigin());
	}// unsetTarget
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#setTarget(org.eclipse
	 * .emf.common.notify.Notifier)
	 */
	@Override
	public void setTarget(Notifier target) {
		super.setTarget(target);
		// add node listeners to fetch node renamings in order to update the
		// mapping visualization
		Mapping mapping = (Mapping) target;
		addNodeListener(mapping.getImage());
		addNodeListener(mapping.getOrigin());
	}// setTarget
	
	/**
	 * Notifies the given node to refresh its label (only). This affects the
	 * icon in particular, which shows if the node is created, deleted or
	 * preserve.
	 * 
	 * @param notification
	 * @param node
	 */
	private void notifyNodeForRefresh(Notification notification, Node node) {
		if (node != null) {
			ItemProviderAdapter adapter = (ItemProviderAdapter) this.adapterFactory.adapt(node,
					Node.class);
			Notification notif = new ViewerNotification(notification, node, false, true);
			adapter.fireNotifyChanged(notif);
		}// if
	}// notifyNodeForRefresh
	
	/**
	 * This Listener listens for events on nodes.
	 * 
	 * @author sjtuner
	 * 
	 */
	private class NodeListener implements INotifyChangedListener {
		
		@Override
		public void notifyChanged(Notification notification) {
			/*
			 * Listen for Node renaming events.
			 */
			if (notification.getFeatureID(Node.class) == HenshinPackage.NODE__NAME) {
				List<Mapping> mappings = findMappingsByNode((Node) notification.getNotifier());
				
				AdapterFactory fac = MappingItemProvider.this.adapterFactory;
				for (Mapping m : mappings) {
					// update the mapping visualization in the editor
					Notification notif = new ViewerNotification(notification, m, false, true);
					ItemProviderAdapter adapter = (ItemProviderAdapter) fac.adapt(m, Mapping.class);
					adapter.fireNotifyChanged(notif);
				}// for
			}// if
		}// notifyChanged
		
		/**
		 * Finds all mappings related to the given node.
		 * 
		 * @param node
		 * @return
		 */
		private List<Mapping> findMappingsByNode(Node node) {
			List<Mapping> resultList = new ArrayList<Mapping>();
			Graph graph = node.getGraph();
			
			if (graph.eContainer() instanceof Rule) {
				Rule rule = (Rule) graph.eContainer();
				collectMappingHelper(node, rule.getMappings(), resultList);
			} else { // graph.eContainer() instance of NestedCondition
				NestedCondition nc = (NestedCondition) graph.eContainer();
				List<NestedCondition> allNestedConditions = new ArrayList<NestedCondition>();
				collectNestedConditions(nc, allNestedConditions);
				
				for (NestedCondition n : allNestedConditions) {
					collectMappingHelper(node, n.getMappings(), resultList);
				}// for
				
			}// if else
			
			return resultList;
		}// findMappingsByNode
		
		/**
		 * Iterates over the given mappingList and returns a resultList with all
		 * mappings the given node is related to, whether as origin or image.
		 * 
		 * @param node
		 * @param mappingList
		 * @param resultList
		 */
		private void collectMappingHelper(Node node, List<Mapping> mappingList,
				List<Mapping> resultList) {
			for (Mapping m : mappingList) {
				if ((m.getImage() == node) || (m.getOrigin() == node)) resultList.add(m);
			}// for
		}// collectMappingHelper
		
		/**
		 * Collects recursively all {@link NestedCondition}s the given
		 * <code>formula</code> is associated with, i.e. the hierarchy of the
		 * given <code>formula</code>. If the formula is a
		 * {@link NestedCondition} itself, it is added to the
		 * <code>resultList</code>.<br>
		 * Remark: Initially the resultList shall be not null. The given formula
		 * is included in the list if it is a NestedCondition itself.
		 * 
		 * @param formula
		 * @param resultList
		 */
		private void collectNestedConditions(Formula formula, List<NestedCondition> resultList) {
			
			if (formula == null) return;
			
			if (formula instanceof BinaryFormula) {
				BinaryFormula bf = (BinaryFormula) formula;
				collectNestedConditions(bf.getLeft(), resultList);
				collectNestedConditions(bf.getRight(), resultList);
			} else if (formula instanceof UnaryFormula) {
				UnaryFormula uf = (UnaryFormula) formula;
				collectNestedConditions(uf.getChild(), resultList);
			} else { // it IS a nested condition
				resultList.add((NestedCondition) formula);
			}// if ifelse else
		}// collectNestedCondition
		
	}// inner class
	
}// class
