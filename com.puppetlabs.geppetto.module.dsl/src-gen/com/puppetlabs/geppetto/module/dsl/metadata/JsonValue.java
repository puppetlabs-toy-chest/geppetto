/**
 */
package com.puppetlabs.geppetto.module.dsl.metadata;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Json Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonValue#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage#getJsonValue()
 * @model
 * @generated
 */
public interface JsonValue extends Value
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(Object)
   * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage#getJsonValue_Value()
   * @model
   * @generated
   */
  Object getValue();

  /**
   * Sets the value of the '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonValue#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(Object value);

} // JsonValue
