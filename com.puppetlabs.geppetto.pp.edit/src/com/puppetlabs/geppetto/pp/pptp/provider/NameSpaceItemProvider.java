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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.puppetlabs.geppetto.pp.pptp.NameSpace;
import com.puppetlabs.geppetto.pp.pptp.PPTPFactory;
import com.puppetlabs.geppetto.pp.pptp.PPTPPackage;

/**
 * This is the item provider adapter for a {@link com.puppetlabs.geppetto.pp.pptp.NameSpace} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class NameSpaceItemProvider extends TargetElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NameSpaceItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This adds a property descriptor for the Reserved feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addReservedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_NameSpace_reserved_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_NameSpace_reserved_feature", "_UI_NameSpace_type"),
			PPTPPackage.Literals.NAME_SPACE__RESERVED, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
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
		}
		return childrenFeatures;
	}

	/**
	 * This returns NameSpace.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/NameSpace"));
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

			addReservedPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
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
		String label = ((NameSpace) object).getName();
		return label == null || label.length() == 0
			? getString("_UI_NameSpace_type")
			: getString("_UI_NameSpace_type") + " " + label;
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

		switch(notification.getFeatureID(NameSpace.class)) {
			case PPTPPackage.NAME_SPACE__RESERVED:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case PPTPPackage.NAME_SPACE__CONTENTS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

}
