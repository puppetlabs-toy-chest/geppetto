/**
 */
package com.puppetlabs.geppetto.module.dsl.metadata;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ref Pair</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.puppetlabs.geppetto.module.dsl.metadata.MetadataRefPair#getRef <em>Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage#getMetadataRefPair()
 * @model
 * @generated
 */
public interface MetadataRefPair extends Pair
{
  /**
   * Returns the value of the '<em><b>Ref</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Ref</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Ref</em>' reference.
   * @see #setRef(JsonMetadata)
   * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage#getMetadataRefPair_Ref()
   * @model
   * @generated
   */
  JsonMetadata getRef();

  /**
   * Sets the value of the '{@link com.puppetlabs.geppetto.module.dsl.metadata.MetadataRefPair#getRef <em>Ref</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ref</em>' reference.
   * @see #getRef()
   * @generated
   */
  void setRef(JsonMetadata value);

} // MetadataRefPair
