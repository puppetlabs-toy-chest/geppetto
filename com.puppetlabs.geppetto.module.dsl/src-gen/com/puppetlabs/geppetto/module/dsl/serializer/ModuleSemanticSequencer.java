package com.puppetlabs.geppetto.module.dsl.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonArray;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonDependency;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonMetadata;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonModuleName;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonOS;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonObject;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonRequirement;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonTag;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonValue;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonVersion;
import com.puppetlabs.geppetto.module.dsl.metadata.JsonVersionRange;
import com.puppetlabs.geppetto.module.dsl.metadata.MetadataPackage;
import com.puppetlabs.geppetto.module.dsl.metadata.MetadataRefPair;
import com.puppetlabs.geppetto.module.dsl.metadata.Pair;
import com.puppetlabs.geppetto.module.dsl.metadata.RequirementNameValue;
import com.puppetlabs.geppetto.module.dsl.metadata.UnrecognizedPair;
import com.puppetlabs.geppetto.module.dsl.services.ModuleGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class ModuleSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private ModuleGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == MetadataPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case MetadataPackage.JSON_ARRAY:
				if(context == grammarAccess.getDependencyArrayRule()) {
					sequence_DependencyArray(context, (JsonArray) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getJsonArrayRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_JsonArray(context, (JsonArray) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getOSArrayRule()) {
					sequence_OSArray(context, (JsonArray) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getRequirementArrayRule()) {
					sequence_RequirementArray(context, (JsonArray) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getStringArrayRule()) {
					sequence_StringArray(context, (JsonArray) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getTagArrayRule()) {
					sequence_TagArray(context, (JsonArray) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.JSON_DEPENDENCY:
				if(context == grammarAccess.getDependencyObjectRule() ||
				   context == grammarAccess.getJsonDependencyRule()) {
					sequence_JsonDependency(context, (JsonDependency) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.JSON_METADATA:
				if(context == grammarAccess.getJsonMetadataRule() ||
				   context == grammarAccess.getModelRule()) {
					sequence_JsonMetadata(context, (JsonMetadata) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.JSON_MODULE_NAME:
				if(context == grammarAccess.getJsonModuleNameRule() ||
				   context == grammarAccess.getModuleNameRule()) {
					sequence_JsonModuleName(context, (JsonModuleName) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.JSON_OS:
				if(context == grammarAccess.getJsonOSRule() ||
				   context == grammarAccess.getOSObjectRule()) {
					sequence_JsonOS(context, (JsonOS) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.JSON_OBJECT:
				if(context == grammarAccess.getJsonObjectRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_JsonObject(context, (JsonObject) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.JSON_REQUIREMENT:
				if(context == grammarAccess.getJsonRequirementRule() ||
				   context == grammarAccess.getRequirementObjectRule()) {
					sequence_JsonRequirement(context, (JsonRequirement) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.JSON_TAG:
				if(context == grammarAccess.getJsonTagRule() ||
				   context == grammarAccess.getTagRule()) {
					sequence_JsonTag(context, (JsonTag) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.JSON_VALUE:
				if(context == grammarAccess.getValueRule()) {
					sequence_BooleanLiteral_DoubleLiteral_LongLiteral_NullValue_StringLiteral_Value(context, (JsonValue) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getBooleanLiteralRule()) {
					sequence_BooleanLiteral(context, (JsonValue) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getDoubleLiteralRule()) {
					sequence_DoubleLiteral(context, (JsonValue) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getLongLiteralRule()) {
					sequence_LongLiteral(context, (JsonValue) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getNullValueRule()) {
					sequence_NullValue(context, (JsonValue) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getStringLiteralRule()) {
					sequence_StringLiteral(context, (JsonValue) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.JSON_VERSION:
				if(context == grammarAccess.getJsonVersionRule() ||
				   context == grammarAccess.getVersionRule()) {
					sequence_JsonVersion(context, (JsonVersion) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.JSON_VERSION_RANGE:
				if(context == grammarAccess.getJsonVersionRangeRule() ||
				   context == grammarAccess.getVersionRangeRule()) {
					sequence_JsonVersionRange(context, (JsonVersionRange) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.METADATA_REF_PAIR:
				if(context == grammarAccess.getDependencyPairRule() ||
				   context == grammarAccess.getMetadataRefPairRule()) {
					sequence_MetadataRefPair(context, (MetadataRefPair) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.PAIR:
				if(context == grammarAccess.getMetadataPairRule()) {
					sequence_AuthorPair_DependenciesPair_IssuesUrlPair_LicensePair_MetadataPair_NamePair_OperatingsystemSupportPair_ProjectPagePair_RequirementsPair_SourcePair_SummaryPair_TagsPair_VersionPair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getAuthorPairRule()) {
					sequence_AuthorPair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getDependenciesPairRule()) {
					sequence_DependenciesPair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getIssuesUrlPairRule()) {
					sequence_IssuesUrlPair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getLicensePairRule()) {
					sequence_LicensePair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getNamePairRule()) {
					sequence_NamePair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getOSPairRule()) {
					sequence_OSPair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getOperatingsystemSupportPairRule()) {
					sequence_OperatingsystemSupportPair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPairRule()) {
					sequence_Pair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getProjectPagePairRule()) {
					sequence_ProjectPagePair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getRequirementsPairRule()) {
					sequence_RequirementsPair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getSourcePairRule()) {
					sequence_SourcePair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getSummaryPairRule()) {
					sequence_SummaryPair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getTagsPairRule()) {
					sequence_TagsPair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getDependencyPairRule() ||
				   context == grammarAccess.getRequirementPairRule() ||
				   context == grammarAccess.getVRPairRule()) {
					sequence_VRPair(context, (Pair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getVersionPairRule()) {
					sequence_VersionPair(context, (Pair) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.REQUIREMENT_NAME_VALUE:
				if(context == grammarAccess.getRequirementNameRule() ||
				   context == grammarAccess.getRequirementNameValueRule()) {
					sequence_RequirementNameValue(context, (RequirementNameValue) semanticObject); 
					return; 
				}
				else break;
			case MetadataPackage.UNRECOGNIZED_PAIR:
				if(context == grammarAccess.getMetadataPairRule() ||
				   context == grammarAccess.getUnrecognizedMetadataPairRule()) {
					sequence_UnrecognizedMetadataPair(context, (UnrecognizedPair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getOSPairRule() ||
				   context == grammarAccess.getUnrecognizedOSPairRule()) {
					sequence_UnrecognizedOSPair(context, (UnrecognizedPair) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getDependencyPairRule() ||
				   context == grammarAccess.getRequirementPairRule() ||
				   context == grammarAccess.getUnrecognizedRequirementPairRule()) {
					sequence_UnrecognizedRequirementPair(context, (UnrecognizedPair) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (
	 *         (name='"author"' value=StringLiteral) | 
	 *         (name='"dependencies"' value=DependencyArray) | 
	 *         (name='"issues_url"' value=StringLiteral) | 
	 *         (name='"license"' value=StringLiteral) | 
	 *         (name='"name"' value=ModuleName) | 
	 *         (name='"project_page"' value=StringLiteral) | 
	 *         (name='"source"' value=StringLiteral) | 
	 *         (name='"summary"' value=StringLiteral) | 
	 *         (name='"requirements"' value=RequirementArray) | 
	 *         (name='"operatingsystem_support"' value=OSArray) | 
	 *         (name='"tags"' value=TagArray) | 
	 *         (name='"version"' value=Version)
	 *     )
	 */
	protected void sequence_AuthorPair_DependenciesPair_IssuesUrlPair_LicensePair_MetadataPair_NamePair_OperatingsystemSupportPair_ProjectPagePair_RequirementsPair_SourcePair_SummaryPair_TagsPair_VersionPair(EObject context, Pair semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name='"author"' value=StringLiteral)
	 */
	protected void sequence_AuthorPair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAuthorPairAccess().getNameAuthorKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getAuthorPairAccess().getValueStringLiteralParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (value=BOOL | value=NULL | value=Q_StringOrKey | value=DOUBLE | value=LONG)
	 */
	protected void sequence_BooleanLiteral_DoubleLiteral_LongLiteral_NullValue_StringLiteral_Value(EObject context, JsonValue semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=BOOL
	 */
	protected void sequence_BooleanLiteral(EObject context, JsonValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getBooleanLiteralAccess().getValueBOOLParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name='"dependencies"' value=DependencyArray)
	 */
	protected void sequence_DependenciesPair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getDependenciesPairAccess().getNameDependenciesKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getDependenciesPairAccess().getValueDependencyArrayParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ((value+=DependencyObject value+=DependencyObject*)?)
	 */
	protected void sequence_DependencyArray(EObject context, JsonArray semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=DOUBLE
	 */
	protected void sequence_DoubleLiteral(EObject context, JsonValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getDoubleLiteralAccess().getValueDOUBLETerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name='"issues_url"' value=StringLiteral)
	 */
	protected void sequence_IssuesUrlPair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getIssuesUrlPairAccess().getNameIssues_urlKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getIssuesUrlPairAccess().getValueStringLiteralParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ((value+=Value value+=Value*)?)
	 */
	protected void sequence_JsonArray(EObject context, JsonArray semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (pairs+=DependencyPair pairs+=DependencyPair*)
	 */
	protected void sequence_JsonDependency(EObject context, JsonDependency semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (pairs+=MetadataPair pairs+=MetadataPair*)
	 */
	protected void sequence_JsonMetadata(EObject context, JsonMetadata semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=STRING
	 */
	protected void sequence_JsonModuleName(EObject context, JsonModuleName semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJsonModuleNameAccess().getValueSTRINGTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (pairs+=OSPair pairs+=OSPair*)
	 */
	protected void sequence_JsonOS(EObject context, JsonOS semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((pairs+=Pair pairs+=Pair*)?)
	 */
	protected void sequence_JsonObject(EObject context, JsonObject semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (pairs+=RequirementPair pairs+=RequirementPair*)
	 */
	protected void sequence_JsonRequirement(EObject context, JsonRequirement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=Q_StringOrKey
	 */
	protected void sequence_JsonTag(EObject context, JsonTag semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJsonTagAccess().getValueQ_StringOrKeyParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=STRING
	 */
	protected void sequence_JsonVersionRange(EObject context, JsonVersionRange semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJsonVersionRangeAccess().getValueSTRINGTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=STRING
	 */
	protected void sequence_JsonVersion(EObject context, JsonVersion semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJsonVersionAccess().getValueSTRINGTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name='"license"' value=StringLiteral)
	 */
	protected void sequence_LicensePair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLicensePairAccess().getNameLicenseKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getLicensePairAccess().getValueStringLiteralParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=LONG
	 */
	protected void sequence_LongLiteral(EObject context, JsonValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLongLiteralAccess().getValueLONGTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name='"name"' ref=[JsonMetadata|STRING])
	 */
	protected void sequence_MetadataRefPair(EObject context, MetadataRefPair semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name='"name"' value=ModuleName)
	 */
	protected void sequence_NamePair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getNamePairAccess().getNameNameKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getNamePairAccess().getValueModuleNameParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=NULL
	 */
	protected void sequence_NullValue(EObject context, JsonValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getNullValueAccess().getValueNULLParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ((value+=OSObject value+=OSObject*)?)
	 */
	protected void sequence_OSArray(EObject context, JsonArray semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((name='"operatingsystem"' value=StringLiteral) | (name='"operatingsystemrelease"' value=StringArray))
	 */
	protected void sequence_OSPair(EObject context, Pair semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name='"operatingsystem_support"' value=OSArray)
	 */
	protected void sequence_OperatingsystemSupportPair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getOperatingsystemSupportPairAccess().getNameOperatingsystem_supportKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getOperatingsystemSupportPairAccess().getValueOSArrayParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=Q_StringOrKey value=Value)
	 */
	protected void sequence_Pair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getPairAccess().getNameQ_StringOrKeyParserRuleCall_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getPairAccess().getValueValueParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name='"project_page"' value=StringLiteral)
	 */
	protected void sequence_ProjectPagePair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getProjectPagePairAccess().getNameProject_pageKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getProjectPagePairAccess().getValueStringLiteralParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ((value+=RequirementObject value+=RequirementObject*)?)
	 */
	protected void sequence_RequirementArray(EObject context, JsonArray semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=STRING
	 */
	protected void sequence_RequirementNameValue(EObject context, RequirementNameValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getRequirementNameValueAccess().getValueSTRINGTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name='"requirements"' value=RequirementArray)
	 */
	protected void sequence_RequirementsPair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getRequirementsPairAccess().getNameRequirementsKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getRequirementsPairAccess().getValueRequirementArrayParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name='"source"' value=StringLiteral)
	 */
	protected void sequence_SourcePair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getSourcePairAccess().getNameSourceKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getSourcePairAccess().getValueStringLiteralParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ((value+=StringLiteral value+=StringLiteral*)?)
	 */
	protected void sequence_StringArray(EObject context, JsonArray semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=Q_StringOrKey
	 */
	protected void sequence_StringLiteral(EObject context, JsonValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.JSON_VALUE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getStringLiteralAccess().getValueQ_StringOrKeyParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name='"summary"' value=StringLiteral)
	 */
	protected void sequence_SummaryPair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getSummaryPairAccess().getNameSummaryKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getSummaryPairAccess().getValueStringLiteralParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ((value+=Tag value+=Tag*)?)
	 */
	protected void sequence_TagArray(EObject context, JsonArray semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name='"tags"' value=TagArray)
	 */
	protected void sequence_TagsPair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTagsPairAccess().getNameTagsKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getTagsPairAccess().getValueTagArrayParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=STRING value=Value)
	 */
	protected void sequence_UnrecognizedMetadataPair(EObject context, UnrecognizedPair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getUnrecognizedMetadataPairAccess().getNameSTRINGTerminalRuleCall_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getUnrecognizedMetadataPairAccess().getValueValueParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=Q_OsUnknownKey value=Value)
	 */
	protected void sequence_UnrecognizedOSPair(EObject context, UnrecognizedPair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getUnrecognizedOSPairAccess().getNameQ_OsUnknownKeyParserRuleCall_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getUnrecognizedOSPairAccess().getValueValueParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=Q_ReqUnknownKey value=Value)
	 */
	protected void sequence_UnrecognizedRequirementPair(EObject context, UnrecognizedPair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getUnrecognizedRequirementPairAccess().getNameQ_ReqUnknownKeyParserRuleCall_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getUnrecognizedRequirementPairAccess().getValueValueParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name='"version_requirement"' value=VersionRange)
	 */
	protected void sequence_VRPair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getVRPairAccess().getNameVersion_requirementKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getVRPairAccess().getValueVersionRangeParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name='"version"' value=Version)
	 */
	protected void sequence_VersionPair(EObject context, Pair semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__NAME));
			if(transientValues.isValueTransient(semanticObject, MetadataPackage.Literals.PAIR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, MetadataPackage.Literals.PAIR__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getVersionPairAccess().getNameVersionKeyword_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getVersionPairAccess().getValueVersionParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
}
