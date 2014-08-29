package com.puppetlabs.geppetto.validation.impl;

import com.google.inject.AbstractModule;
import com.puppetlabs.geppetto.validation.ValidationService;

public class ValidationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ValidationService.class).to(ValidationServiceImpl.class);
	}
}
