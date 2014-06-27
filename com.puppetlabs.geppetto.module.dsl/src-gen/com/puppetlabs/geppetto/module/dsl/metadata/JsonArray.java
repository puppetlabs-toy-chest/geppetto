/**
 */
package com.puppetlabs.geppetto.module.dsl.metadata;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Json Array</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonArray#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage#getJsonArray()
 * @model
 * @generated
 */
public interface JsonArray extends Value
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference list.
   * The list contents are of type {@link com.puppetlabs.geppetto.module.dsl.metadata.Value}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference list.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage#getJsonArray_Value()
   * @model containment="true"
   * @generated
   */
  EList<Value> getValue();

} // JsonArray
