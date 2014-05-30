/**
 */
package com.puppetlabs.geppetto.module.dsl.metadata;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage
 * @generated
 */
public interface MetadataFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  MetadataFactory eINSTANCE = com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Json Object</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Json Object</em>'.
   * @generated
   */
  JsonObject createJsonObject();

  /**
   * Returns a new object of class '<em>Json Metadata</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Json Metadata</em>'.
   * @generated
   */
  JsonMetadata createJsonMetadata();

  /**
   * Returns a new object of class '<em>Pair</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Pair</em>'.
   * @generated
   */
  Pair createPair();

  /**
   * Returns a new object of class '<em>Unrecognized Pair</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Unrecognized Pair</em>'.
   * @generated
   */
  UnrecognizedPair createUnrecognizedPair();

  /**
   * Returns a new object of class '<em>Json Array</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Json Array</em>'.
   * @generated
   */
  JsonArray createJsonArray();

  /**
   * Returns a new object of class '<em>Json Dependency</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Json Dependency</em>'.
   * @generated
   */
  JsonDependency createJsonDependency();

  /**
   * Returns a new object of class '<em>Ref Pair</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ref Pair</em>'.
   * @generated
   */
  MetadataRefPair createMetadataRefPair();

  /**
   * Returns a new object of class '<em>Json Requirement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Json Requirement</em>'.
   * @generated
   */
  JsonRequirement createJsonRequirement();

  /**
   * Returns a new object of class '<em>Json Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Json Value</em>'.
   * @generated
   */
  JsonValue createJsonValue();

  /**
   * Returns a new object of class '<em>Requirement Name Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Requirement Name Value</em>'.
   * @generated
   */
  RequirementNameValue createRequirementNameValue();

  /**
   * Returns a new object of class '<em>Json Version Range</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Json Version Range</em>'.
   * @generated
   */
  JsonVersionRange createJsonVersionRange();

  /**
   * Returns a new object of class '<em>Json Module Name</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Json Module Name</em>'.
   * @generated
   */
  JsonModuleName createJsonModuleName();

  /**
   * Returns a new object of class '<em>Json Tag</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Json Tag</em>'.
   * @generated
   */
  JsonTag createJsonTag();

  /**
   * Returns a new object of class '<em>Json Version</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Json Version</em>'.
   * @generated
   */
  JsonVersion createJsonVersion();

  /**
   * Returns a new object of class '<em>Json OS</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Json OS</em>'.
   * @generated
   */
  JsonOS createJsonOS();

  /**
   * Returns a new object of class '<em>Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Value</em>'.
   * @generated
   */
  Value createValue();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  MetadataPackage getMetadataPackage();

} //MetadataFactory
