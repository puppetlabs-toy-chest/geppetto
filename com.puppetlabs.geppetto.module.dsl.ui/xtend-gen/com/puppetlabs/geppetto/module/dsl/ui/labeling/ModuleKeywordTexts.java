package com.puppetlabs.geppetto.module.dsl.ui.labeling;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.puppetlabs.geppetto.module.dsl.services.ModuleGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@SuppressWarnings("all")
public class ModuleKeywordTexts {
  @Inject
  private ModuleGrammarAccess grammarAccess;
  
  private final String AUTHOR_DOC = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Human friendly author name.<br>");
      _builder.newLine();
      _builder.append("eg. Bob Smith <bob@example.com>");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String DEPENDENCIES = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Module(s) that this module depends on.");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String ISSUES_URL = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Where users of this module should file issues.<br>");
      _builder.newLine();
      _builder.append("eg. https://github.com/foobar/puppet-module-baz/issues");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String LICENSE = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<p>The license under which the module is made available.</p>");
      _builder.newLine();
      _builder.append("<p>Please provide the short standard designation for this modules<br>");
      _builder.newLine();
      _builder.append("license such as \'Apache-2.0\' or \'GPL-3.0\'. A reference on license<br>");
      _builder.newLine();
      _builder.append("names is available at <a href=\"http://opensource.org/licenses/alphabetical\">opensource.org</a>.</p>");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String NAME = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<p>The full name (including namespace) of this module<br>");
      _builder.newLine();
      _builder.append("eg. &quot;puppetlabs-apache&quot; (using a dash)</p>");
      _builder.newLine();
      _builder.append("<p>This information must exactly match your namespace<br>");
      _builder.newLine();
      _builder.append("and modulename on Forge and it is case-sensitive. eg.<br>");
      _builder.newLine();
      _builder.append("foo != Foo</p>");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String PROJECT_PAGE = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Where users of this module should go to learn more.<br>");
      _builder.newLine();
      _builder.append("eg. https://github.com/foobar/puppet-module-baz/wiki");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String REQUIREMENTS = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<p>Requirements is a list of external requirements of the module of the form:<br>");
      _builder.newLine();
      _builder.append("<code>&quot;requirements&quot;: [ {&quot;name&quot;: &quot;pe&quot;, &quot;version_requirement&quot;: &quot;3.x&quot;}]</code></p>");
      _builder.newLine();
      _builder.append("<p>&quot;name&quot; is the name of the requirement.</p>");
      _builder.newLine();
      _builder.append("<p>&quot;version_requirement&quot; can be a semver version range similar to<br>");
      _builder.newLine();
      _builder.append("dependencies.</p>");
      _builder.newLine();
      _builder.append("<p>While any requirement can be expressed, we only expose &quot;puppet&quot; and &quot;pe&quot;<br>");
      _builder.newLine();
      _builder.append("requirements for Puppet version and Puppet Enterprise version respectively<br>");
      _builder.newLine();
      _builder.append("through the Forge, on module pages and search filters.</p>");
      _builder.newLine();
      _builder.append("<p>Because Puppet before 3.0 does not follow semver, it is not recommended<br>");
      _builder.newLine();
      _builder.append("to express requirements on it.</p>");
      _builder.newLine();
      _builder.append("<p>We plan to support “mco”, “facter” and “hiera” requirements in the future.</p>");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String OS_SUPPORT = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<p>This key is for expressing the operating systems (and optionally their<br>");
      _builder.newLine();
      _builder.append("versions) that this module supports. The OS will be used with Forge search<br>");
      _builder.newLine();
      _builder.append("filters but the version data will just be treated as strings on module pages.</p>");
      _builder.newLine();
      _builder.append("<p>We expect you to provide the values of Facter facts operatingsystem and<br>");
      _builder.newLine();
      _builder.append("operatingsystemrelease.</p>");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String SUMMARY = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<p>A quick, one-line description of what this module does.</p>");
      _builder.newLine();
      _builder.append("<p>This information will be surfaced in search results on Forge.</p>");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String SOURCE = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Where others should retrieve the source code for this module.<br>");
      _builder.newLine();
      _builder.append("eg. git@github.com:foo/puppet-module-bar.git");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String TAGS = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<p>Key words that will help others find this module (not case sensitive)<br>");
      _builder.newLine();
      _builder.append("eg. [&quot;msyql&quot;, &quot;database&quot;]. Cannot contain whitespace.</p>");
      _builder.newLine();
      _builder.append("<p>This information will help people find this module based on related</br>");
      _builder.newLine();
      _builder.append("terms such as monitoring or postgres instead of postgresql. We recommend<br>");
      _builder.newLine();
      _builder.append("using between four and six tags.</p> \t\t");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String VERSION = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The current version of the module. We require a <a href=\"http://semver.org/spec/v1.0.0.html\">SemVer 1.0.0</a><br>");
      _builder.newLine();
      _builder.append("formatted version number. We strongly recommend following SemVer for<br>");
      _builder.newLine();
      _builder.append("all module versions.");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String OS_OPERATINGSYSTEM = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The name of the operating system. e.g. &quot;RedHat&quot; or &quot;Ubuntu&quot;.");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String OS_OPERATINGSYSTEM_RELEASE = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("A list of supported operatingsystem releases. e.g. &quot;12.04&quot; or &quot;10.04&quot;.");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String DEP_NAME = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The full name (including namespace) of the module<br>");
      _builder.newLine();
      _builder.append("appointed by this dependency.");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String REQ_NAME = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<p>The name of the requirement.</p>");
      _builder.newLine();
      _builder.append("<p>While any requirement can be expressed, we only expose &quot;puppet&quot; and &quot;pe&quot;<br>");
      _builder.newLine();
      _builder.append("requirements for Puppet version and Puppet Enterprise version respectively<br>");
      _builder.newLine();
      _builder.append("through the Forge, on module pages and search filters.</p>");
      _builder.newLine();
      _builder.append("<p>We plan to support “mco”, “facter” and “hiera” requirements in the future.</p>");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private final String VERSION_REQUIREMENT = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("A SemVer compliant version range. e.g. &quot;3.x&quot; &quot;>=3.0.4&quot; See<br>");
      _builder.newLine();
      _builder.append("<a href=\"http://semver.org/spec/v1.0.0.html\">SemVer 1.0.0</a> for more info.");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  public boolean isDependencyName(final Keyword keyword) {
    boolean _xblockexpression = false;
    {
      EObject c = keyword.eContainer();
      boolean _xifexpression = false;
      if ((c instanceof Assignment)) {
        boolean _xifexpression_1 = false;
        EObject _eContainer = ((Assignment)c).eContainer();
        EObject _c = c = _eContainer;
        if ((_c instanceof Group)) {
          EObject _eContainer_1 = c.eContainer();
          ParserRule _metadataRefPairRule = this.grammarAccess.getMetadataRefPairRule();
          _xifexpression_1 = (_eContainer_1 == _metadataRefPairRule);
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public String getHoverText(final Keyword keyword) {
    String _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      ModuleGrammarAccess.AuthorPairElements _authorPairAccess = this.grammarAccess.getAuthorPairAccess();
      Keyword _nameAuthorKeyword_0_0 = _authorPairAccess.getNameAuthorKeyword_0_0();
      if (Objects.equal(keyword, _nameAuthorKeyword_0_0)) {
        _matched=true;
        _switchResult = this.AUTHOR_DOC;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.DependenciesPairElements _dependenciesPairAccess = this.grammarAccess.getDependenciesPairAccess();
      Keyword _nameDependenciesKeyword_0_0 = _dependenciesPairAccess.getNameDependenciesKeyword_0_0();
      if (Objects.equal(keyword, _nameDependenciesKeyword_0_0)) {
        _matched=true;
        _switchResult = this.DEPENDENCIES;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.IssuesUrlPairElements _issuesUrlPairAccess = this.grammarAccess.getIssuesUrlPairAccess();
      Keyword _nameIssues_urlKeyword_0_0 = _issuesUrlPairAccess.getNameIssues_urlKeyword_0_0();
      if (Objects.equal(keyword, _nameIssues_urlKeyword_0_0)) {
        _matched=true;
        _switchResult = this.ISSUES_URL;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.LicensePairElements _licensePairAccess = this.grammarAccess.getLicensePairAccess();
      Keyword _nameLicenseKeyword_0_0 = _licensePairAccess.getNameLicenseKeyword_0_0();
      if (Objects.equal(keyword, _nameLicenseKeyword_0_0)) {
        _matched=true;
        _switchResult = this.LICENSE;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.NamePairElements _namePairAccess = this.grammarAccess.getNamePairAccess();
      Keyword _nameNameKeyword_0_0 = _namePairAccess.getNameNameKeyword_0_0();
      if (Objects.equal(keyword, _nameNameKeyword_0_0)) {
        _matched=true;
        _switchResult = this.NAME;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.OperatingsystemSupportPairElements _operatingsystemSupportPairAccess = this.grammarAccess.getOperatingsystemSupportPairAccess();
      Keyword _nameOperatingsystem_supportKeyword_0_0 = _operatingsystemSupportPairAccess.getNameOperatingsystem_supportKeyword_0_0();
      if (Objects.equal(keyword, _nameOperatingsystem_supportKeyword_0_0)) {
        _matched=true;
        _switchResult = this.OS_SUPPORT;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.ProjectPagePairElements _projectPagePairAccess = this.grammarAccess.getProjectPagePairAccess();
      Keyword _nameProject_pageKeyword_0_0 = _projectPagePairAccess.getNameProject_pageKeyword_0_0();
      if (Objects.equal(keyword, _nameProject_pageKeyword_0_0)) {
        _matched=true;
        _switchResult = this.PROJECT_PAGE;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.RequirementsPairElements _requirementsPairAccess = this.grammarAccess.getRequirementsPairAccess();
      Keyword _nameRequirementsKeyword_0_0 = _requirementsPairAccess.getNameRequirementsKeyword_0_0();
      if (Objects.equal(keyword, _nameRequirementsKeyword_0_0)) {
        _matched=true;
        _switchResult = this.REQUIREMENTS;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.SourcePairElements _sourcePairAccess = this.grammarAccess.getSourcePairAccess();
      Keyword _nameSourceKeyword_0_0 = _sourcePairAccess.getNameSourceKeyword_0_0();
      if (Objects.equal(keyword, _nameSourceKeyword_0_0)) {
        _matched=true;
        _switchResult = this.SOURCE;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.SummaryPairElements _summaryPairAccess = this.grammarAccess.getSummaryPairAccess();
      Keyword _nameSummaryKeyword_0_0 = _summaryPairAccess.getNameSummaryKeyword_0_0();
      if (Objects.equal(keyword, _nameSummaryKeyword_0_0)) {
        _matched=true;
        _switchResult = this.SUMMARY;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.TagsPairElements _tagsPairAccess = this.grammarAccess.getTagsPairAccess();
      Keyword _nameTagsKeyword_0_0 = _tagsPairAccess.getNameTagsKeyword_0_0();
      if (Objects.equal(keyword, _nameTagsKeyword_0_0)) {
        _matched=true;
        _switchResult = this.TAGS;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.VersionPairElements _versionPairAccess = this.grammarAccess.getVersionPairAccess();
      Keyword _nameVersionKeyword_0_0 = _versionPairAccess.getNameVersionKeyword_0_0();
      if (Objects.equal(keyword, _nameVersionKeyword_0_0)) {
        _matched=true;
        _switchResult = this.VERSION;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.OSPairElements _oSPairAccess = this.grammarAccess.getOSPairAccess();
      Keyword _nameOperatingsystemKeyword_0_0_0 = _oSPairAccess.getNameOperatingsystemKeyword_0_0_0();
      if (Objects.equal(keyword, _nameOperatingsystemKeyword_0_0_0)) {
        _matched=true;
        _switchResult = this.OS_OPERATINGSYSTEM;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.OSPairElements _oSPairAccess_1 = this.grammarAccess.getOSPairAccess();
      Keyword _nameOperatingsystemreleaseKeyword_1_0_0 = _oSPairAccess_1.getNameOperatingsystemreleaseKeyword_1_0_0();
      if (Objects.equal(keyword, _nameOperatingsystemreleaseKeyword_1_0_0)) {
        _matched=true;
        _switchResult = this.OS_OPERATINGSYSTEM_RELEASE;
      }
    }
    if (!_matched) {
      boolean _isDependencyName = this.isDependencyName(keyword);
      if (_isDependencyName) {
        _matched=true;
        _switchResult = this.DEP_NAME;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.RequirementPairElements _requirementPairAccess = this.grammarAccess.getRequirementPairAccess();
      Keyword _nameNameKeyword_0_0_0 = _requirementPairAccess.getNameNameKeyword_0_0_0();
      if (Objects.equal(keyword, _nameNameKeyword_0_0_0)) {
        _matched=true;
        _switchResult = this.REQ_NAME;
      }
    }
    if (!_matched) {
      ModuleGrammarAccess.VRPairElements _vRPairAccess = this.grammarAccess.getVRPairAccess();
      Keyword _nameVersion_requirementKeyword_0_0 = _vRPairAccess.getNameVersion_requirementKeyword_0_0();
      if (Objects.equal(keyword, _nameVersion_requirementKeyword_0_0)) {
        _matched=true;
        _switchResult = this.VERSION_REQUIREMENT;
      }
    }
    return _switchResult;
  }
}
