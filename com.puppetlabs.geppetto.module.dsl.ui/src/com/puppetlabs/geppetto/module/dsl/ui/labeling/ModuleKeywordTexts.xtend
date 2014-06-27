package com.puppetlabs.geppetto.module.dsl.ui.labeling;

import com.google.inject.Inject
import com.puppetlabs.geppetto.module.dsl.services.ModuleGrammarAccess
import org.eclipse.xtext.Assignment
import org.eclipse.xtext.Group
import org.eclipse.xtext.Keyword

class ModuleKeywordTexts {
	@Inject
	ModuleGrammarAccess grammarAccess;

	val AUTHOR_DOC = '''
		Human friendly author name.<br>
		eg. Bob Smith <bob@example.com>
	'''

	val DEPENDENCIES = '''
		Module(s) that this module depends on.
	'''

	val ISSUES_URL = '''
		Where users of this module should file issues.<br>
		eg. https://github.com/foobar/puppet-module-baz/issues
	'''

	val LICENSE = '''
		<p>The license under which the module is made available.</p>
		<p>Please provide the short standard designation for this modules<br>
		license such as 'Apache-2.0' or 'GPL-3.0'. A reference on license<br>
		names is available at <a href="http://opensource.org/licenses/alphabetical">opensource.org</a>.</p>
	'''

	val NAME = '''
		<p>The full name (including namespace) of this module<br>
		eg. &quot;puppetlabs-apache&quot; (using a dash)</p>
		<p>This information must exactly match your namespace<br>
		and modulename on Forge and it is case-sensitive. eg.<br>
		foo != Foo</p>
	'''

	val PROJECT_PAGE = '''
		Where users of this module should go to learn more.<br>
		eg. https://github.com/foobar/puppet-module-baz/wiki
	'''

	val REQUIREMENTS = '''
		<p>Requirements is a list of external requirements of the module of the form:<br>
		<code>&quot;requirements&quot;: [ {&quot;name&quot;: &quot;pe&quot;, &quot;version_requirement&quot;: &quot;3.x&quot;}]</code></p>
		<p>&quot;name&quot; is the name of the requirement.</p>
		<p>&quot;version_requirement&quot; can be a semver version range similar to<br>
		dependencies.</p>
		<p>While any requirement can be expressed, we only expose &quot;puppet&quot; and &quot;pe&quot;<br>
		requirements for Puppet version and Puppet Enterprise version respectively<br>
		through the Forge, on module pages and search filters.</p>
		<p>Because Puppet before 3.0 does not follow semver, it is not recommended<br>
		to express requirements on it.</p>
		<p>We plan to support “mco”, “facter” and “hiera” requirements in the future.</p>
	'''

	val OS_SUPPORT = '''
		<p>This key is for expressing the operating systems (and optionally their<br>
		versions) that this module supports. The OS will be used with Forge search<br>
		filters but the version data will just be treated as strings on module pages.</p>
		<p>We expect you to provide the values of Facter facts operatingsystem and<br>
		operatingsystemrelease.</p>
	'''

	val SUMMARY = '''
		<p>A quick, one-line description of what this module does.</p>
		<p>This information will be surfaced in search results on Forge.</p>
	'''

	val SOURCE = '''
		Where others should retrieve the source code for this module.<br>
		eg. git@github.com:foo/puppet-module-bar.git
	'''

	val TAGS = '''
		<p>Key words that will help others find this module (not case sensitive)<br>
		eg. [&quot;msyql&quot;, &quot;database&quot;]. Cannot contain whitespace.</p>
		<p>This information will help people find this module based on related</br>
		terms such as monitoring or postgres instead of postgresql. We recommend<br>
		using between four and six tags.</p> 		
	'''

	val VERSION = '''
		The current version of the module. We require a <a href="http://semver.org/spec/v1.0.0.html">SemVer 1.0.0</a><br>
		formatted version number. We strongly recommend following SemVer for<br>
		all module versions.
	'''

	val OS_OPERATINGSYSTEM = '''
		The name of the operating system. e.g. &quot;RedHat&quot; or &quot;Ubuntu&quot;.
	'''

	val OS_OPERATINGSYSTEM_RELEASE = '''
		A list of supported operatingsystem releases. e.g. &quot;12.04&quot; or &quot;10.04&quot;.
	'''

	val DEP_NAME = '''
		The full name (including namespace) of the module<br>
		appointed by this dependency.
	'''

	val REQ_NAME = '''
		<p>The name of the requirement.</p>
		<p>While any requirement can be expressed, we only expose &quot;puppet&quot; and &quot;pe&quot;<br>
		requirements for Puppet version and Puppet Enterprise version respectively<br>
		through the Forge, on module pages and search filters.</p>
		<p>We plan to support “mco”, “facter” and “hiera” requirements in the future.</p>
	'''

	val VERSION_REQUIREMENT = '''
		A SemVer compliant version range. e.g. &quot;3.x&quot; &quot;>=3.0.4&quot; See<br>
		<a href="http://semver.org/spec/v1.0.0.html">SemVer 1.0.0</a> for more info.
	'''

	def isDependencyName(Keyword keyword) {
		var c = keyword.eContainer
		if(c instanceof Assignment)
			if((c = c.eContainer) instanceof Group)
				c.eContainer === grammarAccess.metadataRefPairRule
	}

	def getHoverText(Keyword keyword) {
		switch (keyword) {
			case grammarAccess.authorPairAccess.nameAuthorKeyword_0_0:
				AUTHOR_DOC
			case grammarAccess.dependenciesPairAccess.nameDependenciesKeyword_0_0:
				DEPENDENCIES
			case grammarAccess.issuesUrlPairAccess.nameIssues_urlKeyword_0_0:
				ISSUES_URL
			case grammarAccess.licensePairAccess.nameLicenseKeyword_0_0:
				LICENSE
			case grammarAccess.namePairAccess.nameNameKeyword_0_0:
				NAME
			case grammarAccess.operatingsystemSupportPairAccess.nameOperatingsystem_supportKeyword_0_0:
				OS_SUPPORT
			case grammarAccess.projectPagePairAccess.nameProject_pageKeyword_0_0:
				PROJECT_PAGE
			case grammarAccess.requirementsPairAccess.nameRequirementsKeyword_0_0:
				REQUIREMENTS
			case grammarAccess.sourcePairAccess.nameSourceKeyword_0_0:
				SOURCE
			case grammarAccess.summaryPairAccess.nameSummaryKeyword_0_0:
				SUMMARY
			case grammarAccess.tagsPairAccess.nameTagsKeyword_0_0:
				TAGS
			case grammarAccess.versionPairAccess.nameVersionKeyword_0_0:
				VERSION
			case grammarAccess.OSPairAccess.nameOperatingsystemKeyword_0_0_0:
				OS_OPERATINGSYSTEM
			case grammarAccess.OSPairAccess.nameOperatingsystemreleaseKeyword_1_0_0:
				OS_OPERATINGSYSTEM_RELEASE
			case keyword.dependencyName:
				DEP_NAME
			case grammarAccess.requirementPairAccess.nameNameKeyword_0_0_0:
				REQ_NAME
			case grammarAccess.VRPairAccess.nameVersion_requirementKeyword_0_0:
				VERSION_REQUIREMENT
		}
	}
}
