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
package com.puppetlabs.geppetto.forge.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.semver.Version;

/**
 * Module meta-data
 */
public class Metadata extends Entity {

	@Expose
	private String author;

	@Expose
	private Map<String, byte[]> checksums = Collections.emptyMap();

	@Expose
	private List<Dependency> dependencies = Collections.emptyList();

	@Expose
	private String description;

	@Expose
	private String issues_url;

	@Expose
	private String license;

	@Expose
	private ModuleName name;

	@Expose
	private String project_page;

	@Expose
	private String source;

	@Expose
	private String summary;

	@Expose
	private List<String> tags = Collections.emptyList();;

	@Expose
	private List<Type> types = Collections.emptyList();;

	@Expose
	private List<Requirement> requirements = Collections.emptyList();;

	@Expose
	private List<SupportedOS> operatingsystem_support = Collections.emptyList();

	@Expose
	private Version version;

	private transient Map<String, Object> dynamicAttributes;

	/**
	 * Add a named value to the dynamic attribute map of this instance.
	 *
	 * @param name
	 * @param value
	 */
	public void addDynamicAttribute(String name, Object value) {
		if(dynamicAttributes == null)
			dynamicAttributes = new HashMap<String, Object>();
		dynamicAttributes.put(name, value);
	}

	public void clear() {
		name = null;
		version = null;
		summary = null;
		author = null;
		description = null;
		source = null;
		project_page = null;
		license = null;
		dependencies = Collections.emptyList();
		operatingsystem_support = Collections.emptyList();
		requirements = Collections.emptyList();
		types = Collections.emptyList();
		tags = Collections.emptyList();
		checksums = Collections.emptyMap();
		if(dynamicAttributes != null)
			dynamicAttributes.clear();
	}

	/**
	 * The verbose name of the author of this module.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * A map with filename &lt;-&gt; checksum entries for each file in the module
	 *
	 * @return An unmodifiable map of checksums
	 * @deprecated Checksums are stored in a separater checksums.json file
	 */
	@Deprecated
	public Map<String, byte[]> getChecksums() {
		return checksums;
	}

	/**
	 * The list of module dependencies.
	 *
	 * @return An unmodifiable list of dependencies.
	 */
	public List<Dependency> getDependencies() {
		return dependencies;
	}

	/**
	 * A description of the module.
	 *
	 * @return the description
	 * @deprecated Use summary for a one-liner or a readme.md if more doc is needed
	 */
	@Deprecated
	public String getDescription() {
		return description;
	}

	/**
	 * Returns a map of attributes that were present in the JSON for this metadata
	 * but not recognized as proper attributes.
	 *
	 * @return An unmodifiable Map of dynamic attributes, possibly empty but never null.
	 */
	public Map<String, Object> getDynamicAttributes() {
		return dynamicAttributes == null
				? Collections.<String, Object> emptyMap()
						: Collections.unmodifiableMap(dynamicAttributes);
	}

	/**
	 * URL to issue tracker
	 *
	 * @return the description
	 */
	public String getIssuesURL() {
		return issues_url;
	}

	/**
	 * The license pertaining to this module.
	 *
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * The qualified name of the module.
	 *
	 * @return the qualified name
	 */
	public ModuleName getName() {
		return name;
	}

	/**
	 * @return An unmodifiable list of supported operating systems
	 */
	public List<SupportedOS> getOperatingSystemSupport() {
		return operatingsystem_support;
	}

	/**
	 * A URL that points to the project page for this module.
	 *
	 * @return the project_page
	 */
	public String getProjectPage() {
		return project_page;
	}

	/**
	 * @return An unmodifiable list of requirements
	 */
	public List<Requirement> getRequirements() {
		return requirements;
	}

	/**
	 * A URL that points to the source for this module.
	 *
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * A brief summary
	 *
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * The list of Tags that this module includes.
	 *
	 * @return An unmodifiable list of tags or an emtpy list.
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * The list of Types that this module includes.
	 *
	 * @return An unmodifiable list of types
	 * @deprecated Types are no longer stored in the metadata.json file
	 */
	@Deprecated
	public List<Type> getTypes() {
		return types;
	}

	/**
	 * The version of the module.
	 *
	 * @return the version
	 */
	public Version getVersion() {
		return version;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = trimToNull(author);
	}

	/**
	 * @param checksums
	 *            the checksums to set
	 * @deprecated Checksums are stored in a separater checksums.json file
	 */
	@Deprecated
	public void setChecksums(Map<String, byte[]> checksums) {
		this.checksums = asUnmodifiableMap(checksums);
	}

	/**
	 * @param dependencies
	 *            the dependencies to set
	 */
	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = asUnmodifiableList(dependencies);
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = trimToNull(description);
	}

	/**
	 * @param issues_url
	 *            the issues_url to set
	 */
	public void setIssuesURL(String issues_url) {
		this.issues_url = trimToNull(issues_url);
	}

	/**
	 * @param license
	 *            the license to set
	 */
	public void setLicense(String license) {
		this.license = trimToNull(license);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(ModuleName name) {
		this.name = name;
	}

	/**
	 * @param operatingSystemSupport
	 *            the list of supported operating systems to set
	 */
	public void setOperatingSystemSupport(List<SupportedOS> operatingSystemSupport) {
		this.operatingsystem_support = asUnmodifiableList(operatingSystemSupport);
	}

	/**
	 * @param project_page
	 *            the project_page to set
	 */
	public void setProjectPage(String project_page) {
		this.project_page = trimToNull(project_page);
	}

	/**
	 * @param requirements
	 *            the requirements to set
	 */
	public void setRequirements(List<Requirement> requirements) {
		this.requirements = asUnmodifiableList(requirements);
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = trimToNull(source);
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = trimToNull(summary);
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = asUnmodifiableList(tags);
	}

	/**
	 * @param types
	 *            the types to set
	 * @deprecated Types are no longer stored in the metadata.json file
	 */
	@Deprecated
	public void setTypes(List<Type> types) {
		this.types = asUnmodifiableList(types);
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}
}
