/**
 */
package com.puppetlabs.geppetto.module.dsl.metadata;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataFactory
 * @model kind="package"
 * @generated
 */
public interface MetadataPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "metadata";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.puppetlabs.com/geppetto/module/dsl/Module";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "metadata";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  MetadataPackage eINSTANCE = com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl.init();

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.ValueImpl <em>Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.ValueImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getValue()
   * @generated
   */
  int VALUE = 15;

  /**
   * The number of structural features of the '<em>Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonObjectImpl <em>Json Object</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonObjectImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonObject()
   * @generated
   */
  int JSON_OBJECT = 0;

  /**
   * The feature id for the '<em><b>Pairs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_OBJECT__PAIRS = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Json Object</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_OBJECT_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonMetadataImpl <em>Json Metadata</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonMetadataImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonMetadata()
   * @generated
   */
  int JSON_METADATA = 1;

  /**
   * The feature id for the '<em><b>Pairs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_METADATA__PAIRS = JSON_OBJECT__PAIRS;

  /**
   * The number of structural features of the '<em>Json Metadata</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_METADATA_FEATURE_COUNT = JSON_OBJECT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.PairImpl <em>Pair</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.PairImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getPair()
   * @generated
   */
  int PAIR = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAIR__NAME = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAIR__VALUE = 1;

  /**
   * The number of structural features of the '<em>Pair</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PAIR_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.UnrecognizedPairImpl <em>Unrecognized Pair</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.UnrecognizedPairImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getUnrecognizedPair()
   * @generated
   */
  int UNRECOGNIZED_PAIR = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNRECOGNIZED_PAIR__NAME = PAIR__NAME;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNRECOGNIZED_PAIR__VALUE = PAIR__VALUE;

  /**
   * The number of structural features of the '<em>Unrecognized Pair</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNRECOGNIZED_PAIR_FEATURE_COUNT = PAIR_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonArrayImpl <em>Json Array</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonArrayImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonArray()
   * @generated
   */
  int JSON_ARRAY = 4;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_ARRAY__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Json Array</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_ARRAY_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonDependencyImpl <em>Json Dependency</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonDependencyImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonDependency()
   * @generated
   */
  int JSON_DEPENDENCY = 5;

  /**
   * The feature id for the '<em><b>Pairs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_DEPENDENCY__PAIRS = JSON_OBJECT__PAIRS;

  /**
   * The number of structural features of the '<em>Json Dependency</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_DEPENDENCY_FEATURE_COUNT = JSON_OBJECT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataRefPairImpl <em>Ref Pair</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataRefPairImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getMetadataRefPair()
   * @generated
   */
  int METADATA_REF_PAIR = 6;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METADATA_REF_PAIR__NAME = PAIR__NAME;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METADATA_REF_PAIR__VALUE = PAIR__VALUE;

  /**
   * The feature id for the '<em><b>Ref</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METADATA_REF_PAIR__REF = PAIR_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Ref Pair</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METADATA_REF_PAIR_FEATURE_COUNT = PAIR_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonRequirementImpl <em>Json Requirement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonRequirementImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonRequirement()
   * @generated
   */
  int JSON_REQUIREMENT = 7;

  /**
   * The feature id for the '<em><b>Pairs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_REQUIREMENT__PAIRS = JSON_OBJECT__PAIRS;

  /**
   * The number of structural features of the '<em>Json Requirement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_REQUIREMENT_FEATURE_COUNT = JSON_OBJECT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonValueImpl <em>Json Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonValueImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonValue()
   * @generated
   */
  int JSON_VALUE = 8;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Json Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.RequirementNameValueImpl <em>Requirement Name Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.RequirementNameValueImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getRequirementNameValue()
   * @generated
   */
  int REQUIREMENT_NAME_VALUE = 9;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REQUIREMENT_NAME_VALUE__VALUE = JSON_VALUE__VALUE;

  /**
   * The number of structural features of the '<em>Requirement Name Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REQUIREMENT_NAME_VALUE_FEATURE_COUNT = JSON_VALUE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonVersionRangeImpl <em>Json Version Range</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonVersionRangeImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonVersionRange()
   * @generated
   */
  int JSON_VERSION_RANGE = 10;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_VERSION_RANGE__VALUE = JSON_VALUE__VALUE;

  /**
   * The number of structural features of the '<em>Json Version Range</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_VERSION_RANGE_FEATURE_COUNT = JSON_VALUE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonModuleNameImpl <em>Json Module Name</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonModuleNameImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonModuleName()
   * @generated
   */
  int JSON_MODULE_NAME = 11;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_MODULE_NAME__VALUE = JSON_VALUE__VALUE;

  /**
   * The number of structural features of the '<em>Json Module Name</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_MODULE_NAME_FEATURE_COUNT = JSON_VALUE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonTagImpl <em>Json Tag</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonTagImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonTag()
   * @generated
   */
  int JSON_TAG = 12;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_TAG__VALUE = JSON_VALUE__VALUE;

  /**
   * The number of structural features of the '<em>Json Tag</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_TAG_FEATURE_COUNT = JSON_VALUE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonVersionImpl <em>Json Version</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonVersionImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonVersion()
   * @generated
   */
  int JSON_VERSION = 13;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_VERSION__VALUE = JSON_VALUE__VALUE;

  /**
   * The number of structural features of the '<em>Json Version</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_VERSION_FEATURE_COUNT = JSON_VALUE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonOSImpl <em>Json OS</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonOSImpl
   * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonOS()
   * @generated
   */
  int JSON_OS = 14;

  /**
   * The feature id for the '<em><b>Pairs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_OS__PAIRS = JSON_OBJECT__PAIRS;

  /**
   * The number of structural features of the '<em>Json OS</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JSON_OS_FEATURE_COUNT = JSON_OBJECT_FEATURE_COUNT + 0;


  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonObject <em>Json Object</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Json Object</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonObject
   * @generated
   */
  EClass getJsonObject();

  /**
   * Returns the meta object for the containment reference list '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonObject#getPairs <em>Pairs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Pairs</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonObject#getPairs()
   * @see #getJsonObject()
   * @generated
   */
  EReference getJsonObject_Pairs();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata <em>Json Metadata</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Json Metadata</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata
   * @generated
   */
  EClass getJsonMetadata();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.Pair <em>Pair</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Pair</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.Pair
   * @generated
   */
  EClass getPair();

  /**
   * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.module.dsl.metadata.Pair#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.Pair#getName()
   * @see #getPair()
   * @generated
   */
  EAttribute getPair_Name();

  /**
   * Returns the meta object for the containment reference '{@link com.puppetlabs.geppetto.module.dsl.metadata.Pair#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.Pair#getValue()
   * @see #getPair()
   * @generated
   */
  EReference getPair_Value();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.UnrecognizedPair <em>Unrecognized Pair</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Unrecognized Pair</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.UnrecognizedPair
   * @generated
   */
  EClass getUnrecognizedPair();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonArray <em>Json Array</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Json Array</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonArray
   * @generated
   */
  EClass getJsonArray();

  /**
   * Returns the meta object for the containment reference list '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonArray#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Value</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonArray#getValue()
   * @see #getJsonArray()
   * @generated
   */
  EReference getJsonArray_Value();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonDependency <em>Json Dependency</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Json Dependency</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonDependency
   * @generated
   */
  EClass getJsonDependency();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.MetadataRefPair <em>Ref Pair</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ref Pair</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataRefPair
   * @generated
   */
  EClass getMetadataRefPair();

  /**
   * Returns the meta object for the reference '{@link com.puppetlabs.geppetto.module.dsl.metadata.MetadataRefPair#getRef <em>Ref</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Ref</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataRefPair#getRef()
   * @see #getMetadataRefPair()
   * @generated
   */
  EReference getMetadataRefPair_Ref();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonRequirement <em>Json Requirement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Json Requirement</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonRequirement
   * @generated
   */
  EClass getJsonRequirement();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonValue <em>Json Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Json Value</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonValue
   * @generated
   */
  EClass getJsonValue();

  /**
   * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonValue#getValue()
   * @see #getJsonValue()
   * @generated
   */
  EAttribute getJsonValue_Value();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.RequirementNameValue <em>Requirement Name Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Requirement Name Value</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.RequirementNameValue
   * @generated
   */
  EClass getRequirementNameValue();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonVersionRange <em>Json Version Range</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Json Version Range</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonVersionRange
   * @generated
   */
  EClass getJsonVersionRange();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonModuleName <em>Json Module Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Json Module Name</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonModuleName
   * @generated
   */
  EClass getJsonModuleName();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonTag <em>Json Tag</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Json Tag</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonTag
   * @generated
   */
  EClass getJsonTag();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonVersion <em>Json Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Json Version</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonVersion
   * @generated
   */
  EClass getJsonVersion();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonOS <em>Json OS</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Json OS</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonOS
   * @generated
   */
  EClass getJsonOS();

  /**
   * Returns the meta object for class '{@link com.puppetlabs.geppetto.module.dsl.metadata.Value <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Value</em>'.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.Value
   * @generated
   */
  EClass getValue();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  MetadataFactory getMetadataFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonObjectImpl <em>Json Object</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonObjectImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonObject()
     * @generated
     */
    EClass JSON_OBJECT = eINSTANCE.getJsonObject();

    /**
     * The meta object literal for the '<em><b>Pairs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JSON_OBJECT__PAIRS = eINSTANCE.getJsonObject_Pairs();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonMetadataImpl <em>Json Metadata</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonMetadataImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonMetadata()
     * @generated
     */
    EClass JSON_METADATA = eINSTANCE.getJsonMetadata();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.PairImpl <em>Pair</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.PairImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getPair()
     * @generated
     */
    EClass PAIR = eINSTANCE.getPair();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PAIR__NAME = eINSTANCE.getPair_Name();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PAIR__VALUE = eINSTANCE.getPair_Value();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.UnrecognizedPairImpl <em>Unrecognized Pair</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.UnrecognizedPairImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getUnrecognizedPair()
     * @generated
     */
    EClass UNRECOGNIZED_PAIR = eINSTANCE.getUnrecognizedPair();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonArrayImpl <em>Json Array</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonArrayImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonArray()
     * @generated
     */
    EClass JSON_ARRAY = eINSTANCE.getJsonArray();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JSON_ARRAY__VALUE = eINSTANCE.getJsonArray_Value();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonDependencyImpl <em>Json Dependency</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonDependencyImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonDependency()
     * @generated
     */
    EClass JSON_DEPENDENCY = eINSTANCE.getJsonDependency();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataRefPairImpl <em>Ref Pair</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataRefPairImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getMetadataRefPair()
     * @generated
     */
    EClass METADATA_REF_PAIR = eINSTANCE.getMetadataRefPair();

    /**
     * The meta object literal for the '<em><b>Ref</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference METADATA_REF_PAIR__REF = eINSTANCE.getMetadataRefPair_Ref();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonRequirementImpl <em>Json Requirement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonRequirementImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonRequirement()
     * @generated
     */
    EClass JSON_REQUIREMENT = eINSTANCE.getJsonRequirement();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonValueImpl <em>Json Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonValueImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonValue()
     * @generated
     */
    EClass JSON_VALUE = eINSTANCE.getJsonValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute JSON_VALUE__VALUE = eINSTANCE.getJsonValue_Value();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.RequirementNameValueImpl <em>Requirement Name Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.RequirementNameValueImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getRequirementNameValue()
     * @generated
     */
    EClass REQUIREMENT_NAME_VALUE = eINSTANCE.getRequirementNameValue();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonVersionRangeImpl <em>Json Version Range</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonVersionRangeImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonVersionRange()
     * @generated
     */
    EClass JSON_VERSION_RANGE = eINSTANCE.getJsonVersionRange();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonModuleNameImpl <em>Json Module Name</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonModuleNameImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonModuleName()
     * @generated
     */
    EClass JSON_MODULE_NAME = eINSTANCE.getJsonModuleName();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonTagImpl <em>Json Tag</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonTagImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonTag()
     * @generated
     */
    EClass JSON_TAG = eINSTANCE.getJsonTag();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonVersionImpl <em>Json Version</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonVersionImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonVersion()
     * @generated
     */
    EClass JSON_VERSION = eINSTANCE.getJsonVersion();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonOSImpl <em>Json OS</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.JsonOSImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getJsonOS()
     * @generated
     */
    EClass JSON_OS = eINSTANCE.getJsonOS();

    /**
     * The meta object literal for the '{@link com.puppetlabs.geppetto.module.dsl.metadata.impl.ValueImpl <em>Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.ValueImpl
     * @see com.puppetlabs.geppetto.module.dsl.metadata.impl.MetadataPackageImpl#getValue()
     * @generated
     */
    EClass VALUE = eINSTANCE.getValue();

  }

} //MetadataPackage
