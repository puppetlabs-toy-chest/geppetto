package com.puppetlabs.geppetto.forge.v3.model;

import java.net.URI;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.forge.model.Entity;
import com.puppetlabs.geppetto.semver.Version;

public class AbbrevRelease extends Entity {
	@Expose
	private URI uri;

	@Expose
	private Version version;

	@Expose
	private Boolean supported;

	/**
	 * @return the uri for this release
	 */
	public URI getUri() {
		return uri;
	}

	/**
	 * @return the version of this release
	 */
	public Version getVersion() {
		return version;
	}

	/**
	 * @return <code>true</code> if this release is supported by Puppet Labs Inc.
	 */
	public boolean isSupported() {
		return supported != null && supported.booleanValue();
	}

	/**
	 * @param supported
	 */
	public void setSupported(boolean supported) {
		this.supported = Boolean.valueOf(supported);
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}
}
