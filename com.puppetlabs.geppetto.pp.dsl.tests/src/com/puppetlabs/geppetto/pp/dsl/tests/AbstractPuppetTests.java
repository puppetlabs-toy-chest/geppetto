/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.pp.dsl.tests;

import static com.google.inject.util.Modules.override;
import static com.puppetlabs.geppetto.injectable.CommonModuleProvider.getCommonModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.junit4.AbstractXtextTests;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics;
import org.eclipse.xtext.junit4.validation.ValidatorTester;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.mwe.ContainersStateFactory;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.containers.DelegatingIAllContainerAdapter;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.impl.ListBasedDiagnosticConsumer;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.StringInputStream;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.puppetlabs.geppetto.pp.AttributeOperation;
import com.puppetlabs.geppetto.pp.AttributeOperations;
import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.PPFactory;
import com.puppetlabs.geppetto.pp.PuppetManifest;
import com.puppetlabs.geppetto.pp.ResourceBody;
import com.puppetlabs.geppetto.pp.ResourceExpression;
import com.puppetlabs.geppetto.pp.SingleQuotedString;
import com.puppetlabs.geppetto.pp.VariableExpression;
import com.puppetlabs.geppetto.pp.VirtualNameOrReference;
import com.puppetlabs.geppetto.pp.dsl.PPRuntimeModule;
import com.puppetlabs.geppetto.pp.dsl.PPStandaloneSetup;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath.IConfigurableProvider;
import com.puppetlabs.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;
import com.puppetlabs.geppetto.pp.dsl.target.PuppetTarget;
import com.puppetlabs.geppetto.pp.dsl.validation.DefaultPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import com.puppetlabs.geppetto.pp.dsl.validation.PPJavaValidator;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationAdvisorProvider;
import com.puppetlabs.xtext.serializer.DomBasedSerializer;

public class AbstractPuppetTests extends AbstractXtextTests {
	public class PPTestModule extends AbstractGenericModule {

		public Provider<IValidationAdvisor> provideValidationAdvisor() {
			return ValidationAdvisorProvider.create(getComplianceLevel(), getPotentialProblemsAdvisor());
		}

		// contributed by org.eclipse.xtext.generator.parser.antlr.ex.rt.AntlrGeneratorFragment
		public Provider<XtextResourceSet> provideXtextResourceSet() {
			return new Provider<XtextResourceSet>() {

				@Override
				public XtextResourceSet get() {
					XtextResourceSet resourceSet = new SynchronizedXtextResourceSet();
					PuppetTarget target = PuppetTarget.forComplianceLevel(getComplianceLevel(), false);
					resourceSet.getResource(target.getPlatformURI(), true);
					URI typesURI = target.getTypesURI();
					if(typesURI != null)
						resourceSet.getResource(typesURI, true);
					return resourceSet;
				}
			};
		}
	}

	public class PPTestSetup extends PPStandaloneSetup {
		@Override
		public Injector createInjector() {
			return Guice.createInjector(override(getCommonModule(), new PPRuntimeModule()).with(getTestModule()));
		}
	}

	interface SerializationTestControl {
		public boolean shouldTestSerializer(XtextResource resource);
	}

	protected ValidatorTester<PPJavaValidator> tester;

	protected final PPFactory pf = PPFactory.eINSTANCE;

	private ResourceSet resourceSet;

	private ContainersStateFactory factory;

	protected void addResourceBody(ResourceExpression o, String title, Object... keyValPairs) {
		o.getResourceData().add(createResourceBody(title, keyValPairs));
	}

	/**
	 * Asserts that instance is assignable to expected. Appends information to message about what was
	 * expected and given.
	 *
	 * @param message
	 * @param expected
	 * @param instance
	 */
	protected void assertInstanceOf(String message, Class<?> expected, Object instance) {
		assertTrue(
			message + ": expected instanceof: " + expected.getSimpleName() + " got: " + instance.getClass().getSimpleName(),
			expected.isAssignableFrom(instance.getClass()));
	}

	protected AttributeOperation createAttributeAddition(String name, Expression value) {
		AttributeOperation ao = pf.createAttributeOperation();
		ao.setKey(name);
		ao.setValue(value);
		ao.setOp("+>");
		return ao;
	}

	protected AttributeOperation createAttributeAddition(String name, String value) {
		return createAttributeAddition(name, createNameOrReference(value));
	}

	protected AttributeOperation createAttributeDefinition(String name, Expression value) {
		AttributeOperation ao = pf.createAttributeOperation();
		ao.setKey(name);
		ao.setValue(value);
		ao.setOp("=>");
		return ao;
	}

	protected AttributeOperation createAttributeDefinition(String name, String value) {
		return createAttributeDefinition(name, createNameOrReference(value));
	}

	protected LiteralNameOrReference createNameOrReference(String name) {
		LiteralNameOrReference o = pf.createLiteralNameOrReference();
		o.setValue(name);
		return o;
	}

	protected ResourceBody createResourceBody(boolean additive, Expression titleExpr, Object... keyValPairs) {
		ResourceBody rb = pf.createResourceBody();
		rb.setNameExpr(titleExpr);
		AttributeOperations aos = pf.createAttributeOperations();
		EList<AttributeOperation> aoList = aos.getAttributes();
		for(int i = 0; i < keyValPairs.length; i++) {
			AttributeOperation ao = pf.createAttributeOperation();
			ao.setOp(additive
				? "+>"
				: "=>");
			if(!(keyValPairs[i] instanceof String))
				throw new IllegalArgumentException("Bad test spec, key not a String");
			ao.setKey((String) (keyValPairs[i++]));

			if(keyValPairs[i] instanceof String) {
				SingleQuotedString valueExpr = pf.createSingleQuotedString();
				valueExpr.setText((String) (keyValPairs[i]));
				ao.setValue(valueExpr);
			}
			else if(keyValPairs[i] instanceof Expression)
				ao.setValue((Expression) keyValPairs[i]);
			else
				throw new IllegalArgumentException("Bad test spec, keyValPair value neither String not expression");
			aoList.add(ao);
		}
		if(aos.getAttributes().size() > 0)
			rb.setAttributes(aos);
		return rb;
	}

	protected ResourceBody createResourceBody(boolean additive, String title, Object... keyValPairs) {
		SingleQuotedString titleExpr = null;
		if(title != null) {
			titleExpr = pf.createSingleQuotedString();
			titleExpr.setText(title);
		}
		return createResourceBody(additive, titleExpr, keyValPairs);
	}

	protected ResourceBody createResourceBody(String title, Object... keyValPairs) {
		return createResourceBody(false, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(boolean exported, boolean virtual, boolean additive, String type,
			Expression title, Object... keyValPairs) {

		ResourceExpression re = pf.createResourceExpression();

		Expression resourceExpr = null;
		if(virtual) {
			VirtualNameOrReference resourceName = pf.createVirtualNameOrReference();
			resourceName.setValue(type);
			resourceName.setExported(exported);
			resourceExpr = resourceName;

		}
		else {
			LiteralNameOrReference resourceName = pf.createLiteralNameOrReference();
			resourceName.setValue(type);
			resourceExpr = resourceName;
		}
		re.setResourceExpr(resourceExpr);

		re.getResourceData().add(createResourceBody(additive, title, keyValPairs));
		return re;
	}

	protected ResourceExpression createResourceExpression(boolean exported, boolean virtual, boolean additive, String type, String title,
			Object... keyValPairs) {
		SingleQuotedString titleExpr = null;
		if(title != null) {
			titleExpr = pf.createSingleQuotedString();
			titleExpr.setText(title);
		}
		return createResourceExpression(exported, virtual, additive, type, titleExpr, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(boolean virtual, boolean additive, String type, Expression title,
			Object... keyValPairs) {
		return createResourceExpression(false, virtual, additive, type, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(boolean virtual, boolean additive, String type, String title,
			Object... keyValPairs) {
		return createResourceExpression(false, virtual, additive, type, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(boolean additive, String type, Expression title, Object... keyValPairs) {
		return createResourceExpression(false, additive, type, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(boolean additive, String type, String title, Object... keyValPairs) {
		return createResourceExpression(false, additive, type, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(String type, Expression title, Object... keyValPairs) {
		return createResourceExpression(false, type, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(String type, String title, Object... keyValPairs) {
		return createResourceExpression(false, type, title, keyValPairs);
	}

	protected SingleQuotedString createSqString(String text) {
		SingleQuotedString s = pf.createSingleQuotedString();
		s.setText(text);
		return s;
	}

	protected Expression createValue(String txt) {
		LiteralNameOrReference v = pf.createLiteralNameOrReference();
		v.setValue(txt);
		return v;
	}

	protected VariableExpression createVariable(String name) {
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$" + name);
		return v;
	}

	protected ResourceExpression createVirtualExportedResourceExpression(String type, Expression title, Object... keyValPairs) {
		return createResourceExpression(true, true, false, type, title, keyValPairs);
	}

	protected ResourceExpression createVirtualExportedResourceExpression(String type, String title, Object... keyValPairs) {
		return createResourceExpression(true, true, false, type, title, keyValPairs);
	}

	protected ResourceExpression createVirtualResourceExpression(String type, Expression title, Object... keyValPairs) {
		return createResourceExpression(true, false, type, title, keyValPairs);
	}

	protected ResourceExpression createVirtualResourceExpression(String type, String title, Object... keyValPairs) {
		return createResourceExpression(true, false, type, title, keyValPairs);
	}

	protected AssertableResourceDiagnostics diagnostics(ListBasedDiagnosticConsumer c) {
		List<Diagnostic> warnings = c.getResult(Severity.WARNING);
		List<Diagnostic> errors = c.getResult(Severity.ERROR);
		List<org.eclipse.emf.ecore.resource.Resource.Diagnostic> sum = Lists.newArrayListWithCapacity(errors.size() + warnings.size());
		sum.addAll(warnings);
		sum.addAll(errors);
		return new AssertableResourceDiagnostics(sum);
	}

	protected ComplianceLevel getComplianceLevel() {
		return ComplianceLevel.PUPPET_2_7;
	}

	protected IPotentialProblemsAdvisor getPotentialProblemsAdvisor() {
		return new DefaultPotentialProblemsAdvisor();
	}

	protected final ISetup getSetupInstance() {
		return new PPTestSetup();
	}

	public Module getTestModule() {
		return new PPTestModule();
	}

	/**
	 * Configures the resoureset used by the various load methods. Must be called before loading.
	 *
	 * @param paths
	 */
	protected void initializeResourceSet(List<URI> urisInTestContainer) {
		// initialize things only needed when using a ResourceSet
		// ppResourceServiceProvider = get(IResourceServiceProvider.class);
		resourceSet = get(XtextResourceSet.class);
		factory = get(ContainersStateFactory.class);

		// linker = get(ILinker.class);

		Multimap<String, URI> containerToURIMap = ArrayListMultimap.create();
		String container = getClass().getName();
		for(URI containedURI : urisInTestContainer)
			containerToURIMap.put(container, containedURI);

		// Add pre-populated content
		for(Resource r : resourceSet.getResources())
			containerToURIMap.put(container, r.getURI());

		IAllContainersState containersState = factory.getContainersState(Lists.newArrayList(container), containerToURIMap);
		resourceSet.eAdapters().add(new DelegatingIAllContainerAdapter(containersState));
	}

	/**
	 * Loads resources from a set of Strings with source code, and performs linking.
	 * The linked result can be obtained from the returned ResourceSet.
	 * Use makeManifestURI(int) to get the URI for given resources, staring with 1 for the first
	 * given resource.
	 *
	 * @param sourceStrings
	 * @throws Exception
	 */
	public List<Resource> loadAndLinkResources(String... sourceStrings) throws Exception {
		List<URI> uriList = Lists.newArrayListWithCapacity(sourceStrings.length);
		for(int i = 1; i <= sourceStrings.length; i++)
			uriList.add(makeManifestURI(i));
		initializeResourceSet(uriList);
		List<Resource> resources = Lists.newArrayListWithCapacity(sourceStrings.length);
		for(int i = 0; i < sourceStrings.length; i++)
			resources.add(loadResource(sourceStrings[i], uriList.get(i)));
		for(Resource r : resources)
			resolveCrossReferences(r);
		return resources;
	}

	public final Resource loadAndLinkSingleResource(String sourceString) throws Exception {
		URI uri = makeManifestURI(1);
		initializeResourceSet(Lists.newArrayList(uri));
		Resource r = loadResource(sourceString, uri);
		resolveCrossReferences(r);
		return r;
	}

	public final Resource loadAndLinkSingleResource(String sourceString, boolean loadTarget) throws Exception {
		URI uri = makeManifestURI(1);
		initializeResourceSet(Lists.newArrayList(uri));
		// Factory factory = Resource.Factory.Registry.INSTANCE.getFactory(targetURI);
		// Resource r2 = factory.createResource(targetURI);
		Map<String, String> options = Maps.newHashMap();
		options.put(XtextResource.OPTION_ENCODING, "UTF8");
		// resourceSet.getResources().add(r2);
		if(loadTarget) {
			for(Resource pptp : resourceSet.getResources()) {
				if("pptp".equals(pptp.getURI().fileExtension()))
					pptp.load(options);
			}
		}
		// r2.load(options);
		Resource r = loadResource(sourceString, uri);
		resolveCrossReferences(r);
		return r;
	}

	/**
	 * Loads a .pp, .pptp or .rb resource using the resource factory configured for the extension.
	 * Returns null for a .rb resource that is not expected to contribute anything to the pptp.
	 * All non null resources are added to the resource set.
	 */
	public Resource loadResource(InputStream in, URI uri) throws Exception {
		// Lookup the factory to use for the resource
		Factory factory = Resource.Factory.Registry.INSTANCE.getFactory(uri);

		Map<String, String> options = Maps.newHashMap();
		options.put(XtextResource.OPTION_ENCODING, "UTF8");

		Resource r = factory.createResource(uri);
		resourceSet.getResources().add(r);
		r.load(in, options);

		return r;
	}

	/**
	 * Load a resource from a String. The URI must be well formed for the language being the
	 * content of the given sourceString (the uri determined the factory to use and the encoding
	 * via an IEncodingProvider).
	 *
	 * @param sourceString
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public final Resource loadResource(String sourceString, URI uri) throws Exception {
		return loadResource(new StringInputStream(sourceString, "UTF8"), uri);
	}

	public Resource loadResource(URI uri) throws IOException {
		Resource resource = resourceSet.createResource(uri);
		Map<String, String> options = Maps.newHashMap();
		options.put(XtextResource.OPTION_ENCODING, "UTF8");

		resource.load(options);
		resourceSet.getResources().add(resource);
		return resource;
	}

	/**
	 * Create a "fake" URI for a "file"
	 *
	 * @param id
	 * @return
	 */
	protected URI makeManifestURI(int id) {
		StringBuilder builder = new StringBuilder();
		builder.append("/test/");
		builder.append(getClass().getName());
		builder.append("/manifests/");
		builder.append("file");
		builder.append(id);
		builder.append(".pp");

		return URI.createFileURI(builder.toString());
	}

	public void resolveCrossReferences(Resource resource) {
		if(resource instanceof LazyLinkingResource) {
			// perform standard EMF resource linking
			((LazyLinkingResource) resource).resolveLazyCrossReferences(CancelIndicator.NullImpl);

			// // just in case some other crazy thing is sent here check that it is a pp resource
			// // (pp resource linking is not relevant on anything but .pp resources).
			// if(ppResourceServiceProvider.canHandle(resource.getURI())) {
			// // The PP resource linking (normally done by PP Linker (but without documentation association)
			// //
			// final ListBasedDiagnosticConsumer consumer = new ListBasedDiagnosticConsumer();
			// IMessageAcceptor acceptor = new DiagnosticConsumerBasedMessageAcceptor(consumer);
			// linker.linkModel(
			// ((LazyLinkingResource) resource).getParseResult().getRootASTElement(), acceptor);
			// resource.getErrors().addAll(consumer.getResult(Severity.ERROR));
			// resource.getWarnings().addAll(consumer.getResult(Severity.WARNING));
			// }
		}
		else {
			EcoreUtil.resolveAll(resource);
		}

	}

	protected AssertableResourceDiagnostics resourceErrorDiagnostics(Resource r) {
		return new AssertableResourceDiagnostics(r.getErrors());
	}

	protected AssertableResourceDiagnostics resourceWarningDiagnostics(Resource r) {
		return new AssertableResourceDiagnostics(r.getWarnings());
	}

	@Override
	public String serialize(EObject obj) {
		SaveOptions options = SaveOptions.newBuilder().getOptions();
		// System.err.println(options.toString());
		return getSerializer().serialize(obj, options);
	}

	public String serializeFormatted(EObject obj) {
		return getSerializer().serialize(obj, SaveOptions.newBuilder().format().getOptions());
	}

	public String serializeFormatted(EObject obj, ITextRegion regionToFormat) {
		ISerializer s = getSerializer();
		Preconditions.checkState(s instanceof DomBasedSerializer);
		return ((DomBasedSerializer) s).serialize(obj, SaveOptions.newBuilder().format().getOptions(), regionToFormat);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		with(getSetupInstance());
		PPJavaValidator validator = get(PPJavaValidator.class);
		EValidatorRegistrar registrar = get(EValidatorRegistrar.class);
		ISearchPathProvider searchPathProvider = get(ISearchPathProvider.class);
		if(searchPathProvider instanceof IConfigurableProvider)
			((IConfigurableProvider) searchPathProvider).configure(
				URI.createFileURI(System.getProperty("java.io.tmpdir")), null, null, null);

		tester = new ValidatorTester<PPJavaValidator>(validator, registrar, "com.puppetlabs.geppetto.pp.dsl.PP");
	}

	protected AssertableDiagnostics subTestValidateExpressionTitles(Expression titleExpr) {
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();

		ResourceExpression re = createVirtualResourceExpression("file", titleExpr, "owner", createValue("0777"));
		statements.add(re);
		tester.validator().checkResourceExpression(re);
		tester.validator().checkResourceBody(re.getResourceData().get(0));
		return tester.diagnose();
	}
}
