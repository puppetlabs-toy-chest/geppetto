/**
 */
package com.puppetlabs.geppetto.module.dsl.metadata.util;

import com.puppetlabs.geppetto.module.dsl.metadata.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage
 * @generated
 */
public class MetadataSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static MetadataPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MetadataSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = MetadataPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @parameter ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case MetadataPackage.JSON_OBJECT:
      {
        JsonObject jsonObject = (JsonObject)theEObject;
        T result = caseJsonObject(jsonObject);
        if (result == null) result = caseValue(jsonObject);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.JSON_METADATA:
      {
        JsonMetadata jsonMetadata = (JsonMetadata)theEObject;
        T result = caseJsonMetadata(jsonMetadata);
        if (result == null) result = caseJsonObject(jsonMetadata);
        if (result == null) result = caseValue(jsonMetadata);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.PAIR:
      {
        Pair pair = (Pair)theEObject;
        T result = casePair(pair);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.UNRECOGNIZED_PAIR:
      {
        UnrecognizedPair unrecognizedPair = (UnrecognizedPair)theEObject;
        T result = caseUnrecognizedPair(unrecognizedPair);
        if (result == null) result = casePair(unrecognizedPair);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.JSON_ARRAY:
      {
        JsonArray jsonArray = (JsonArray)theEObject;
        T result = caseJsonArray(jsonArray);
        if (result == null) result = caseValue(jsonArray);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.JSON_DEPENDENCY:
      {
        JsonDependency jsonDependency = (JsonDependency)theEObject;
        T result = caseJsonDependency(jsonDependency);
        if (result == null) result = caseJsonObject(jsonDependency);
        if (result == null) result = caseValue(jsonDependency);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.METADATA_REF_PAIR:
      {
        MetadataRefPair metadataRefPair = (MetadataRefPair)theEObject;
        T result = caseMetadataRefPair(metadataRefPair);
        if (result == null) result = casePair(metadataRefPair);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.JSON_REQUIREMENT:
      {
        JsonRequirement jsonRequirement = (JsonRequirement)theEObject;
        T result = caseJsonRequirement(jsonRequirement);
        if (result == null) result = caseJsonObject(jsonRequirement);
        if (result == null) result = caseValue(jsonRequirement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.JSON_VALUE:
      {
        JsonValue jsonValue = (JsonValue)theEObject;
        T result = caseJsonValue(jsonValue);
        if (result == null) result = caseValue(jsonValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.REQUIREMENT_NAME_VALUE:
      {
        RequirementNameValue requirementNameValue = (RequirementNameValue)theEObject;
        T result = caseRequirementNameValue(requirementNameValue);
        if (result == null) result = caseJsonValue(requirementNameValue);
        if (result == null) result = caseValue(requirementNameValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.JSON_VERSION_RANGE:
      {
        JsonVersionRange jsonVersionRange = (JsonVersionRange)theEObject;
        T result = caseJsonVersionRange(jsonVersionRange);
        if (result == null) result = caseJsonValue(jsonVersionRange);
        if (result == null) result = caseValue(jsonVersionRange);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.JSON_MODULE_NAME:
      {
        JsonModuleName jsonModuleName = (JsonModuleName)theEObject;
        T result = caseJsonModuleName(jsonModuleName);
        if (result == null) result = caseJsonValue(jsonModuleName);
        if (result == null) result = caseValue(jsonModuleName);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.JSON_TAG:
      {
        JsonTag jsonTag = (JsonTag)theEObject;
        T result = caseJsonTag(jsonTag);
        if (result == null) result = caseJsonValue(jsonTag);
        if (result == null) result = caseValue(jsonTag);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.JSON_VERSION:
      {
        JsonVersion jsonVersion = (JsonVersion)theEObject;
        T result = caseJsonVersion(jsonVersion);
        if (result == null) result = caseJsonValue(jsonVersion);
        if (result == null) result = caseValue(jsonVersion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.JSON_OS:
      {
        JsonOS jsonOS = (JsonOS)theEObject;
        T result = caseJsonOS(jsonOS);
        if (result == null) result = caseJsonObject(jsonOS);
        if (result == null) result = caseValue(jsonOS);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MetadataPackage.VALUE:
      {
        Value value = (Value)theEObject;
        T result = caseValue(value);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Json Object</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Json Object</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJsonObject(JsonObject object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Json Metadata</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Json Metadata</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJsonMetadata(JsonMetadata object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Pair</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Pair</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePair(Pair object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Unrecognized Pair</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Unrecognized Pair</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUnrecognizedPair(UnrecognizedPair object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Json Array</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Json Array</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJsonArray(JsonArray object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Json Dependency</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Json Dependency</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJsonDependency(JsonDependency object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ref Pair</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ref Pair</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMetadataRefPair(MetadataRefPair object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Json Requirement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Json Requirement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJsonRequirement(JsonRequirement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Json Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Json Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJsonValue(JsonValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Requirement Name Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Requirement Name Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRequirementNameValue(RequirementNameValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Json Version Range</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Json Version Range</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJsonVersionRange(JsonVersionRange object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Json Module Name</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Json Module Name</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJsonModuleName(JsonModuleName object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Json Tag</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Json Tag</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJsonTag(JsonTag object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Json Version</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Json Version</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJsonVersion(JsonVersion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Json OS</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Json OS</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJsonOS(JsonOS object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseValue(Value object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //MetadataSwitch
