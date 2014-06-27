/**
 */
package com.puppetlabs.geppetto.module.dsl.metadata.impl;

import com.puppetlabs.geppetto.module.dsl.metadata.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MetadataFactoryImpl extends EFactoryImpl implements MetadataFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static MetadataFactory init()
  {
    try
    {
      MetadataFactory theMetadataFactory = (MetadataFactory)EPackage.Registry.INSTANCE.getEFactory(MetadataPackage.eNS_URI);
      if (theMetadataFactory != null)
      {
        return theMetadataFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new MetadataFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MetadataFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case MetadataPackage.JSON_OBJECT: return createJsonObject();
      case MetadataPackage.JSON_METADATA: return createJsonMetadata();
      case MetadataPackage.PAIR: return createPair();
      case MetadataPackage.UNRECOGNIZED_PAIR: return createUnrecognizedPair();
      case MetadataPackage.JSON_ARRAY: return createJsonArray();
      case MetadataPackage.JSON_DEPENDENCY: return createJsonDependency();
      case MetadataPackage.METADATA_REF_PAIR: return createMetadataRefPair();
      case MetadataPackage.JSON_REQUIREMENT: return createJsonRequirement();
      case MetadataPackage.JSON_VALUE: return createJsonValue();
      case MetadataPackage.REQUIREMENT_NAME_VALUE: return createRequirementNameValue();
      case MetadataPackage.JSON_VERSION_RANGE: return createJsonVersionRange();
      case MetadataPackage.JSON_MODULE_NAME: return createJsonModuleName();
      case MetadataPackage.JSON_TAG: return createJsonTag();
      case MetadataPackage.JSON_VERSION: return createJsonVersion();
      case MetadataPackage.JSON_OS: return createJsonOS();
      case MetadataPackage.VALUE: return createValue();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonObject createJsonObject()
  {
    JsonObjectImpl jsonObject = new JsonObjectImpl();
    return jsonObject;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonMetadata createJsonMetadata()
  {
    JsonMetadataImpl jsonMetadata = new JsonMetadataImpl();
    return jsonMetadata;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Pair createPair()
  {
    PairImpl pair = new PairImpl();
    return pair;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UnrecognizedPair createUnrecognizedPair()
  {
    UnrecognizedPairImpl unrecognizedPair = new UnrecognizedPairImpl();
    return unrecognizedPair;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonArray createJsonArray()
  {
    JsonArrayImpl jsonArray = new JsonArrayImpl();
    return jsonArray;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonDependency createJsonDependency()
  {
    JsonDependencyImpl jsonDependency = new JsonDependencyImpl();
    return jsonDependency;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MetadataRefPair createMetadataRefPair()
  {
    MetadataRefPairImpl metadataRefPair = new MetadataRefPairImpl();
    return metadataRefPair;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonRequirement createJsonRequirement()
  {
    JsonRequirementImpl jsonRequirement = new JsonRequirementImpl();
    return jsonRequirement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonValue createJsonValue()
  {
    JsonValueImpl jsonValue = new JsonValueImpl();
    return jsonValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RequirementNameValue createRequirementNameValue()
  {
    RequirementNameValueImpl requirementNameValue = new RequirementNameValueImpl();
    return requirementNameValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonVersionRange createJsonVersionRange()
  {
    JsonVersionRangeImpl jsonVersionRange = new JsonVersionRangeImpl();
    return jsonVersionRange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonModuleName createJsonModuleName()
  {
    JsonModuleNameImpl jsonModuleName = new JsonModuleNameImpl();
    return jsonModuleName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonTag createJsonTag()
  {
    JsonTagImpl jsonTag = new JsonTagImpl();
    return jsonTag;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonVersion createJsonVersion()
  {
    JsonVersionImpl jsonVersion = new JsonVersionImpl();
    return jsonVersion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsonOS createJsonOS()
  {
    JsonOSImpl jsonOS = new JsonOSImpl();
    return jsonOS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Value createValue()
  {
    ValueImpl value = new ValueImpl();
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MetadataPackage getMetadataPackage()
  {
    return (MetadataPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static MetadataPackage getPackage()
  {
    return MetadataPackage.eINSTANCE;
  }

} //MetadataFactoryImpl
