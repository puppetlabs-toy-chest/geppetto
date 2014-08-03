package com.puppetlabs.geppetto.module.dsl.ui.labeling;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.module.dsl.services.ModuleGrammarAccess;

@Singleton
public class ModuleKeywordTexts {
	@Inject
	private ModuleGrammarAccess grammarAccess;

	// @fmtOff
	private final String AUTHOR_DOC = "Human friendly author name.<br>\n" +
			"eg. Bob Smith <bob@example.com>";

	private final String DEPENDENCIES = "Module(s) that this module depends on.";

	private final String ISSUES_URL = "Where users of this module should file issues.<br>\n" +
			"eg. https://github.com/foobar/puppet-module-baz/issues\n";

	private final String LICENSE = "<p>The license under which the module is made available.</p>\n" +
			"<p>Please provide the short standard designation for this modules<br>\n" +
			"license such as 'Apache-2.0' or 'GPL-3.0'. A reference on license<br>\n" +
			"names is available at <a href=\"http://opensource.org/licenses/alphabetical\">opensource.org</a>.</p>";

	private final String NAME = "<p>The full name (including namespace) of this module<br>\n" +
			"eg. &quot;puppetlabs-apache&quot; (using a dash)</p>\n" +
			"<p>This information must exactly match your namespace<br>\n" +
			"and modulename on Forge and it is case-sensitive. eg.<br>\n" +
			"foo != Foo</p>";

	private final String PROJECT_PAGE = "Where users of this module should go to learn more.<br>\n" +
			"eg. https://github.com/foobar/puppet-module-baz/wiki";

	private final String REQUIREMENTS = "<p>Requirements is a list of external requirements of the module of the form:<br>\n" +
			"<code>&quot;requirements&quot;: [{&quot;name&quot;: &quot;pe&quot;, &quot;version_requirement&quot;: &quot;3.x&quot;}]</code></p>\n" +
			"<p>&quot;name&quot; is the name of the requirement.</p>\n" +
			"<p>&quot;version_requirement&quot; can be a semver version range similar to<br>\n" +
			"dependencies.</p>\n" +
			"<p>While any requirement can be expressed, we only expose &quot;puppet&quot; and &quot;pe&quot;<br>\n" +
			"requirements for Puppet version and Puppet Enterprise version respectively<br>\n" +
			"through the Forge, on module pages and search filters.</p>\n" +
			"<p>Because Puppet before 3.0 does not follow semver, it is not recommended<br>\n" +
			"to express requirements on it.</p>\n" +
			"<p>We plan to support “mco”, “facter” and “hiera” requirements in the future.</p>";

	private final String OS_SUPPORT = "<p>This key is for expressing the operating systems (and optionally their<br>\n" +
			"versions) that this module supports. The OS will be used with Forge search<br>\n" +
			"filters but the version data will just be treated as strings on module pages.</p>\n" +
			"<p>We expect you to provide the values of Facter facts operatingsystem and<br>\n" +
			"operatingsystemrelease.</p>";

	private final String SUMMARY = "<p>A quick, one-line description of what this module does.</p>\n" +
			"<p>This information will be surfaced in search results on Forge.</p>";

	private final String SOURCE = "Where others should retrieve the source code for this module.<br>\n" +
			"eg. git@github.com:foo/puppet-module-bar.git";

	private final String TAGS = "<p>Key words that will help others find this module (not case sensitive)<br>\n" +
			"eg. [&quot;msyql&quot;, &quot;database&quot;]. Cannot contain whitespace.</p>\n" +
			"<p>This information will help people find this module based on related</br>\n" +
			"terms such as monitoring or postgres instead of postgresql. We recommend<br>\n" +
			"using between four and six tags.</p>";

	private final String VERSION = "The current version of the module. We require a <a href=\"http://semver.org/spec/v1.0.0.html\">SemVer 1.0.0</a><br>\n" +
			"formatted version number. We strongly recommend following SemVer for<br>\n" +
			"all module versions.";

	private final String OS_OPERATINGSYSTEM = "The name of the operating system. e.g. &quot;RedHat&quot; or &quot;Ubuntu&quot;.";

	private final String OS_OPERATINGSYSTEM_RELEASE = "A list of supported operatingsystem releases. e.g. &quot;12.04&quot; or &quot;10.04&quot;.";

	private final String DEP_NAME = "The full name (including namespace) of the module<br>\n" +
			"appointed by this dependency.";

	private final String REQ_NAME = "<p>The name of the requirement.</p>\n" +
			"<p>While any requirement can be expressed, we only expose &quot;puppet&quot; and &quot;pe&quot;<br>\n" +
			"requirements for Puppet version and Puppet Enterprise version respectively<br>\n" +
			"through the Forge, on module pages and search filters.</p>\n" +
			"<p>We plan to support “mco”, “facter” and “hiera” requirements in the future.</p>";

	private final String VERSION_REQUIREMENT = "A SemVer compliant version range. e.g. &quot;3.x&quot; &quot;>=3.0.4&quot; See<br>\n" +
			"<a href=\"http://semver.org/spec/v1.0.0.html\">SemVer 1.0.0</a> for more info.";
	// @fmtOn

	public String getHoverText(final Keyword keyword) {
		if(keyword == null)
			return null;

		if(keyword.equals(grammarAccess.getAuthorPairAccess().getNameAuthorKeyword_0_0()))
			return AUTHOR_DOC;

		if(keyword.equals(grammarAccess.getDependenciesPairAccess().getNameDependenciesKeyword_0_0()))
			return DEPENDENCIES;

		if(keyword.equals(grammarAccess.getIssuesUrlPairAccess().getNameIssues_urlKeyword_0_0()))
			return ISSUES_URL;

		if(keyword.equals(grammarAccess.getLicensePairAccess().getNameLicenseKeyword_0_0()))
			return LICENSE;

		if(keyword.equals(grammarAccess.getNamePairAccess().getNameNameKeyword_0_0()))
			return NAME;

		if(keyword.equals(grammarAccess.getOperatingsystemSupportPairAccess().getNameOperatingsystem_supportKeyword_0_0()))
			return OS_SUPPORT;

		if(keyword.equals(grammarAccess.getProjectPagePairAccess().getNameProject_pageKeyword_0_0()))
			return PROJECT_PAGE;

		if(keyword.equals(grammarAccess.getRequirementsPairAccess().getNameRequirementsKeyword_0_0()))
			return REQUIREMENTS;

		if(keyword.equals(grammarAccess.getSourcePairAccess().getNameSourceKeyword_0_0()))
			return SOURCE;

		if(keyword.equals(grammarAccess.getSummaryPairAccess().getNameSummaryKeyword_0_0()))
			return SUMMARY;

		if(keyword.equals(grammarAccess.getTagsPairAccess().getNameTagsKeyword_0_0()))
			return TAGS;

		if(keyword.equals(grammarAccess.getVersionPairAccess().getNameVersionKeyword_0_0()))
			return VERSION;

		if(keyword.equals(grammarAccess.getOSPairAccess().getNameOperatingsystemKeyword_0_0_0()))
			return OS_OPERATINGSYSTEM;

		if(keyword.equals(grammarAccess.getOSPairAccess().getNameOperatingsystemreleaseKeyword_1_0_0()))
			return OS_OPERATINGSYSTEM_RELEASE;

		if(isDependencyName(keyword))
			return DEP_NAME;

		if(keyword.equals(grammarAccess.getRequirementPairAccess().getNameNameKeyword_0_0_0()))
			return REQ_NAME;

		if(keyword.equals(grammarAccess.getVRPairAccess().getNameVersion_requirementKeyword_0_0()))
			return VERSION_REQUIREMENT;

		return null;
	}

	public boolean isDependencyName(final Keyword keyword) {
		EObject c = keyword.eContainer();
		if(c instanceof Assignment)
			if((c = c.eContainer()) instanceof Group)
				return c.eContainer() == grammarAccess.getMetadataRefPairRule();
		return false;
	}
}
