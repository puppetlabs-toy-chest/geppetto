/**
 */
package com.puppetlabs.geppetto.module.dsl.metadata;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Json Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonObject#getPairs <em>Pairs</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage#getJsonObject()
 * @model
 * @generated
 */
public interface JsonObject extends Value
{
  /**
   * Returns the value of the '<em><b>Pairs</b></em>' containment reference list.
   * The list contents are of type {@link com.puppetlabs.geppetto.module.dsl.metadata.Pair}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pairs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pairs</em>' containment reference list.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage#getJsonObject_Pairs()
   * @model containment="true"
   * @generated
   */
  EList<Pair> getPairs();

} // JsonObject
