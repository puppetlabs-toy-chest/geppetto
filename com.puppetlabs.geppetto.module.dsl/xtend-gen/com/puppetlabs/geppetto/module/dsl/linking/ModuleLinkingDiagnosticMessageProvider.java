package com.puppetlabs.geppetto.module.dsl.linking;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider;

@SuppressWarnings("all")
public class ModuleLinkingDiagnosticMessageProvider extends LinkingDiagnosticMessageProvider {
  @Inject
  private IModuleValidationAdvisor validationAdvisor;
  
  public DiagnosticMessage getUnresolvedProxyMessage(final ILinkingDiagnosticMessageProvider.ILinkingDiagnosticContext context) {
    DiagnosticMessage _xblockexpression = null;
    {
      DiagnosticMessage msg = ((DiagnosticMessage) null);
      final ValidationPreference pref = this.validationAdvisor.getUnresolvedReference();
      boolean _tripleNotEquals = (pref != ValidationPreference.IGNORE);
      if (_tripleNotEquals) {
        DiagnosticMessage _unresolvedProxyMessage = super.getUnresolvedProxyMessage(context);
        msg = _unresolvedProxyMessage;
        boolean _tripleNotEquals_1 = (msg != null);
        if (_tripleNotEquals_1) {
          String _mangledMessage = this.mangledMessage(msg);
          Severity _severity = this.getSeverity(pref);
          String _issueCode = msg.getIssueCode();
          String[] _issueData = msg.getIssueData();
          DiagnosticMessage _diagnosticMessage = new DiagnosticMessage(_mangledMessage, _severity, _issueCode, _issueData);
          msg = _diagnosticMessage;
        }
      }
      _xblockexpression = msg;
    }
    return _xblockexpression;
  }
  
  private String mangledMessage(final DiagnosticMessage msg) {
    String _message = msg.getMessage();
    return _message.replace("JsonMetadata", "Module");
  }
  
  private Severity getSeverity(final ValidationPreference pref) {
    Severity _switchResult = null;
    if (pref != null) {
      switch (pref) {
        case ERROR:
          _switchResult = Severity.ERROR;
          break;
        case WARNING:
          _switchResult = Severity.WARNING;
          break;
        default:
          _switchResult = Severity.IGNORE;
          break;
      }
    } else {
      _switchResult = Severity.IGNORE;
    }
    return _switchResult;
  }
}
