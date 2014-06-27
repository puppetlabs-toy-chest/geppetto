/**
 */
package com.puppetlabs.geppetto.module.dsl.metadata;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pair</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.puppetlabs.geppetto.module.dsl.metadata.Pair#getName <em>Name</em>}</li>
 *   <li>{@link com.puppetlabs.geppetto.module.dsl.metadata.Pair#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage#getPair()
 * @model
 * @generated
 */
public interface Pair extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage#getPair_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.puppetlabs.geppetto.module.dsl.metadata.Pair#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(Value)
   * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage#getPair_Value()
   * @model containment="true"
   * @generated
   */
  Value getValue();

  /**
   * Sets the value of the '{@link com.puppetlabs.geppetto.module.dsl.metadata.Pair#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(Value value);

} // Pair
