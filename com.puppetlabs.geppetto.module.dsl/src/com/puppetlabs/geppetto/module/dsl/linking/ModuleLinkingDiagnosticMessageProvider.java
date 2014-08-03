package com.puppetlabs.geppetto.module.dsl.linking;

import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference;

@Singleton
public class ModuleLinkingDiagnosticMessageProvider extends LinkingDiagnosticMessageProvider {
	@Inject
	private IModuleValidationAdvisor validationAdvisor;

	@Override
	public DiagnosticMessage getUnresolvedProxyMessage(ILinkingDiagnosticContext context) {
		DiagnosticMessage msg = null;
		final ValidationPreference pref = validationAdvisor.getUnresolvedReference();
		if(pref != ValidationPreference.IGNORE) {
			msg = super.getUnresolvedProxyMessage(context);
			if(msg != null)
				msg = new DiagnosticMessage(mangledMessage(msg), getSeverity(pref), msg.getIssueCode(), msg.getIssueData());
		}
		return msg;
	}

	private String mangledMessage(final DiagnosticMessage msg) {
		return msg.getMessage().replace("JsonMetadata", "Module");
	}

	private Severity getSeverity(final ValidationPreference pref) {
		switch(pref) {
			case ERROR:
				return Severity.ERROR;
			case WARNING:
				return Severity.WARNING;
			default:
				return Severity.IGNORE;
		}
	}
}
