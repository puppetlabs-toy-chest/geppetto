package com.puppetlabs.geppetto.module.dsl.linking

import com.google.inject.Inject
import com.puppetlabs.geppetto.module.dsl.validation.IModuleValidationAdvisor
import com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference
import org.eclipse.xtext.diagnostics.DiagnosticMessage
import org.eclipse.xtext.diagnostics.Severity
import org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider

import static com.puppetlabs.geppetto.pp.dsl.validation.ValidationPreference.*

class ModuleLinkingDiagnosticMessageProvider extends LinkingDiagnosticMessageProvider {
	@Inject
	IModuleValidationAdvisor validationAdvisor

	override getUnresolvedProxyMessage(ILinkingDiagnosticContext context) {
		var msg = null as DiagnosticMessage
		val pref = validationAdvisor.unresolvedReference
		if (pref !== IGNORE) {
			msg = super.getUnresolvedProxyMessage(context)
			if (msg !== null)
				msg = new DiagnosticMessage(msg.mangledMessage, pref.severity, msg.issueCode, msg.issueData)
		}
		msg
	}
	
	def private mangledMessage(DiagnosticMessage msg) {
		return msg.message.replace('JsonMetadata', 'Module')
	}

	def private getSeverity(ValidationPreference pref) {
		switch (pref) {
			case ERROR: Severity.ERROR
			case WARNING: Severity.WARNING
			default: Severity.IGNORE
		}
	}
}
