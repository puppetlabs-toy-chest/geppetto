/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 *
 */
package com.puppetlabs.geppetto.pp.pptp.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.*;

import com.puppetlabs.geppetto.pp.pptp.PPTPFactory;
import com.puppetlabs.geppetto.pp.pptp.PPTPPackage;
import com.puppetlabs.geppetto.pp.pptp.TargetEntry;

/**
 * This is the item provider adapter for a {@link com.puppetlabs.geppetto.pp.pptp.TargetEntry} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class TargetEntryItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider, IStructuredItemContentProvider,
		ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TargetEntryItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_TargetEntry_description_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_TargetEntry_description_feature", "_UI_TargetEntry_type"),
			PPTPPackage.Literals.TARGET_ENTRY__DESCRIPTION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Label feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addLabelPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_TargetEntry_label_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_TargetEntry_label_feature", "_UI_TargetEntry_type"),
			PPTPPackage.Literals.TARGET_ENTRY__LABEL, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Version feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_TargetEntry_version_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_TargetEntry_version_feature", "_UI_TargetEntry_type"),
			PPTPPackage.Literals.TARGET_ENTRY__VERSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS, PPTPFactory.eINSTANCE.createFunction()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS, PPTPFactory.eINSTANCE.createProperty()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS, PPTPFactory.eINSTANCE.createParameter()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS, PPTPFactory.eINSTANCE.createTypeFragment()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS, PPTPFactory.eINSTANCE.createType()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS, PPTPFactory.eINSTANCE.createMetaType()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS, PPTPFactory.eINSTANCE.createNameSpace()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS, PPTPFactory.eINSTANCE.createTPVariable()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS, PPTPFactory.eINSTANCE.createMetaVariable()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS, PPTPFactory.eINSTANCE.createPuppetType()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS, PPTPFactory.eINSTANCE.createPuppetTypeParameter()));

		newChildDescriptors.add(createChildParameter(PPTPPackage.Literals.TARGET_ENTRY__FUNCTIONS, PPTPFactory.eINSTANCE.createFunction()));

		newChildDescriptors.add(createChildParameter(PPTPPackage.Literals.TARGET_ENTRY__TYPES, PPTPFactory.eINSTANCE.createType()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.TARGET_ENTRY__TYPE_FRAGMENTS, PPTPFactory.eINSTANCE.createTypeFragment()));

		newChildDescriptors.add(createChildParameter(PPTPPackage.Literals.TARGET_ENTRY__META_TYPE, PPTPFactory.eINSTANCE.createMetaType()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.TARGET_ENTRY__META_VARIABLES, PPTPFactory.eINSTANCE.createMetaVariable()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if(childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS);
			childrenFeatures.add(PPTPPackage.Literals.TARGET_ENTRY__FUNCTIONS);
			childrenFeatures.add(PPTPPackage.Literals.TARGET_ENTRY__TYPES);
			childrenFeatures.add(PPTPPackage.Literals.TARGET_ENTRY__TYPE_FRAGMENTS);
			childrenFeatures.add(PPTPPackage.Literals.TARGET_ENTRY__META_TYPE);
			childrenFeatures.add(PPTPPackage.Literals.TARGET_ENTRY__META_VARIABLES);
		}
		return childrenFeatures;
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify = childFeature == PPTPPackage.Literals.ITARGET_ELEMENT_CONTAINER__CONTENTS ||
			childFeature == PPTPPackage.Literals.TARGET_ENTRY__FUNCTIONS ||
			childFeature == PPTPPackage.Literals.TARGET_ENTRY__TYPE_FRAGMENTS || childFeature == PPTPPackage.Literals.TARGET_ENTRY__TYPES ||
			childFeature == PPTPPackage.Literals.TARGET_ENTRY__META_TYPE ||
			childFeature == PPTPPackage.Literals.TARGET_ENTRY__META_VARIABLES;

		if(qualify) {
			return getString("_UI_CreateChild_text2", new Object[] {
				getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if(itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addDescriptionPropertyDescriptor(object);
			addVersionPropertyDescriptor(object);
			addLabelPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return PuppetEditPlugin.INSTANCE;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((TargetEntry) object).getDescription();
		return label == null || label.length() == 0
			? getString("_UI_TargetEntry_type")
			: getString("_UI_TargetEntry_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch(notification.getFeatureID(TargetEntry.class)) {
			case PPTPPackage.TARGET_ENTRY__DESCRIPTION:
			case PPTPPackage.TARGET_ENTRY__VERSION:
			case PPTPPackage.TARGET_ENTRY__LABEL:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case PPTPPackage.TARGET_ENTRY__CONTENTS:
			case PPTPPackage.TARGET_ENTRY__FUNCTIONS:
			case PPTPPackage.TARGET_ENTRY__TYPES:
			case PPTPPackage.TARGET_ENTRY__TYPE_FRAGMENTS:
			case PPTPPackage.TARGET_ENTRY__META_TYPE:
			case PPTPPackage.TARGET_ENTRY__META_VARIABLES:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

}
