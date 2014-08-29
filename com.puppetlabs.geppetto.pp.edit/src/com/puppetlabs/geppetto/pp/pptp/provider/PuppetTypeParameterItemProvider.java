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

import com.puppetlabs.geppetto.pp.dsl.pptp.PptpPrinter;
import com.puppetlabs.geppetto.pp.pptp.PPTPFactory;
import com.puppetlabs.geppetto.pp.pptp.PPTPPackage;
import com.puppetlabs.geppetto.pp.pptp.PuppetTypeParameter;

/**
 * This is the item provider adapter for a {@link com.puppetlabs.geppetto.pp.pptp.PuppetTypeParameter} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class PuppetTypeParameterItemProvider extends TargetElementItemProvider {
	private final PptpPrinter printer = new PptpPrinter();

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PuppetTypeParameterItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This adds a property descriptor for the Accept Default feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addAcceptDefaultPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_PuppetTypeParameter_acceptDefault_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_PuppetTypeParameter_acceptDefault_feature", "_UI_PuppetTypeParameter_type"),
			PPTPPackage.Literals.PUPPET_TYPE_PARAMETER__ACCEPT_DEFAULT, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
			null, null));
	}

	/**
	 * This adds a property descriptor for the Namevar feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addNamevarPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_TypeArgument_namevar_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_TypeArgument_namevar_feature", "_UI_TypeArgument_type"),
			PPTPPackage.Literals.TYPE_ARGUMENT__NAMEVAR, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Required feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addRequiredPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_TypeArgument_required_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_TypeArgument_required_feature", "_UI_TypeArgument_type"),
			PPTPPackage.Literals.TYPE_ARGUMENT__REQUIRED, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_PuppetTypeParameter_type_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_PuppetTypeParameter_type_feature", "_UI_PuppetTypeParameter_type"),
			PPTPPackage.Literals.PUPPET_TYPE_PARAMETER__TYPE, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Varargs feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addVarargsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
			getString("_UI_PuppetTypeParameter_varargs_feature"),
			getString("_UI_PropertyDescriptor_description", "_UI_PuppetTypeParameter_varargs_feature", "_UI_PuppetTypeParameter_type"),
			PPTPPackage.Literals.PUPPET_TYPE_PARAMETER__VARARGS, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
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
			PPTPPackage.Literals.PUPPET_TYPE_PARAMETER__PARAMETERS, PPTPFactory.eINSTANCE.createStringValue()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.PUPPET_TYPE_PARAMETER__PARAMETERS, PPTPFactory.eINSTANCE.createTypeReferenceValue()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.PUPPET_TYPE_PARAMETER__PARAMETERS, PPTPFactory.eINSTANCE.createFloatValue()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.PUPPET_TYPE_PARAMETER__PARAMETERS, PPTPFactory.eINSTANCE.createIntegerValue()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.PUPPET_TYPE_PARAMETER__PARAMETERS, PPTPFactory.eINSTANCE.createTypeValue()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.PUPPET_TYPE_PARAMETER__PARAMETERS, PPTPFactory.eINSTANCE.createRegexpValue()));

		newChildDescriptors.add(createChildParameter(
			PPTPPackage.Literals.PUPPET_TYPE_PARAMETER__PARAMETERS, PPTPFactory.eINSTANCE.createNamedTypeValue()));
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
			childrenFeatures.add(PPTPPackage.Literals.PUPPET_TYPE_PARAMETER__PARAMETERS);
		}
		return childrenFeatures;
	}

	/**
	 * This returns PuppetTypeParameter.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/PuppetTypeParameter"));
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

			addRequiredPropertyDescriptor(object);
			addNamevarPropertyDescriptor(object);
			addTypePropertyDescriptor(object);
			addAcceptDefaultPropertyDescriptor(object);
			addVarargsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		StringBuilder bld = new StringBuilder();
		printer.append((PuppetTypeParameter) object, bld);
		return bld.toString();
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

		switch(notification.getFeatureID(PuppetTypeParameter.class)) {
			case PPTPPackage.PUPPET_TYPE_PARAMETER__REQUIRED:
			case PPTPPackage.PUPPET_TYPE_PARAMETER__NAMEVAR:
			case PPTPPackage.PUPPET_TYPE_PARAMETER__ACCEPT_DEFAULT:
			case PPTPPackage.PUPPET_TYPE_PARAMETER__VARARGS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case PPTPPackage.PUPPET_TYPE_PARAMETER__PARAMETERS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

}
