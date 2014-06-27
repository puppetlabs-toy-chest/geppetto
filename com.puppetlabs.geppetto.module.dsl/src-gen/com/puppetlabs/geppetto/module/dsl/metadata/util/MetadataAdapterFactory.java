/**
 */
package com.puppetlabs.geppetto.module.dsl.metadata.util;

import com.puppetlabs.geppetto.module.dsl.metadata.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage
 * @generated
 */
public class MetadataAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static MetadataPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MetadataAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = MetadataPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MetadataSwitch<Adapter> modelSwitch =
    new MetadataSwitch<Adapter>()
    {
      @Override
      public Adapter caseJsonObject(JsonObject object)
      {
        return createJsonObjectAdapter();
      }
      @Override
      public Adapter caseJsonMetadata(JsonMetadata object)
      {
        return createJsonMetadataAdapter();
      }
      @Override
      public Adapter casePair(Pair object)
      {
        return createPairAdapter();
      }
      @Override
      public Adapter caseUnrecognizedPair(UnrecognizedPair object)
      {
        return createUnrecognizedPairAdapter();
      }
      @Override
      public Adapter caseJsonArray(JsonArray object)
      {
        return createJsonArrayAdapter();
      }
      @Override
      public Adapter caseJsonDependency(JsonDependency object)
      {
        return createJsonDependencyAdapter();
      }
      @Override
      public Adapter caseMetadataRefPair(MetadataRefPair object)
      {
        return createMetadataRefPairAdapter();
      }
      @Override
      public Adapter caseJsonRequirement(JsonRequirement object)
      {
        return createJsonRequirementAdapter();
      }
      @Override
      public Adapter caseJsonValue(JsonValue object)
      {
        return createJsonValueAdapter();
      }
      @Override
      public Adapter caseRequirementNameValue(RequirementNameValue object)
      {
        return createRequirementNameValueAdapter();
      }
      @Override
      public Adapter caseJsonVersionRange(JsonVersionRange object)
      {
        return createJsonVersionRangeAdapter();
      }
      @Override
      public Adapter caseJsonModuleName(JsonModuleName object)
      {
        return createJsonModuleNameAdapter();
      }
      @Override
      public Adapter caseJsonTag(JsonTag object)
      {
        return createJsonTagAdapter();
      }
      @Override
      public Adapter caseJsonVersion(JsonVersion object)
      {
        return createJsonVersionAdapter();
      }
      @Override
      public Adapter caseJsonOS(JsonOS object)
      {
        return createJsonOSAdapter();
      }
      @Override
      public Adapter caseValue(Value object)
      {
        return createValueAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonObject <em>Json Object</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonObject
   * @generated
   */
  public Adapter createJsonObjectAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata <em>Json Metadata</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata
   * @generated
   */
  public Adapter createJsonMetadataAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.Pair <em>Pair</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.Pair
   * @generated
   */
  public Adapter createPairAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.UnrecognizedPair <em>Unrecognized Pair</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.UnrecognizedPair
   * @generated
   */
  public Adapter createUnrecognizedPairAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonArray <em>Json Array</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonArray
   * @generated
   */
  public Adapter createJsonArrayAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonDependency <em>Json Dependency</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonDependency
   * @generated
   */
  public Adapter createJsonDependencyAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.MetadataRefPair <em>Ref Pair</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.MetadataRefPair
   * @generated
   */
  public Adapter createMetadataRefPairAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonRequirement <em>Json Requirement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonRequirement
   * @generated
   */
  public Adapter createJsonRequirementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonValue <em>Json Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonValue
   * @generated
   */
  public Adapter createJsonValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.RequirementNameValue <em>Requirement Name Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.RequirementNameValue
   * @generated
   */
  public Adapter createRequirementNameValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonVersionRange <em>Json Version Range</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonVersionRange
   * @generated
   */
  public Adapter createJsonVersionRangeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonModuleName <em>Json Module Name</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonModuleName
   * @generated
   */
  public Adapter createJsonModuleNameAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonTag <em>Json Tag</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonTag
   * @generated
   */
  public Adapter createJsonTagAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonVersion <em>Json Version</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonVersion
   * @generated
   */
  public Adapter createJsonVersionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.JsonOS <em>Json OS</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.JsonOS
   * @generated
   */
  public Adapter createJsonOSAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.module.dsl.metadata.Value <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.puppetlabs.geppetto.module.dsl.metadata.Value
   * @generated
   */
  public Adapter createValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //MetadataAdapterFactory
