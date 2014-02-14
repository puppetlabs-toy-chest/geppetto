<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" />

	<xsl:param name="build-url" />
	<xsl:param name="artifact-prefix" />
	<xsl:param name="download-target" />

	<xsl:template match="/">
		<project default="{$download-target}">
			<xsl:for-each select="/project/target[@name = $download-target]">
				<xsl:copy>
					<xsl:apply-templates select="@*" mode="copy" />

					<xsl:apply-templates
						select="document(concat($build-url, '/api/xml?tree=id,actions%5Bparameters%5Bname,value%5D%5D,artifacts%5BrelativePath%5D'))"
						mode="data" />

					<xsl:apply-templates select="node()" mode="copy" />
				</xsl:copy>
			</xsl:for-each>
		</project>
	</xsl:template>

	<!-- identity transform idiom -->
	<xsl:template match="node() | @*" mode="copy">
		<xsl:copy>
			<xsl:apply-templates select="node() | @*" mode="copy" />
		</xsl:copy>
	</xsl:template>

	<!-- template to process build's metadata -->
	<xsl:template match="/" mode="data">
		<xsl:variable name="build-id"
			select="concat((*/action/parameter[name = 'BUILD_TYPE'])/value, substring(translate(*/id, '-_', ''), 1, 12))" />

		<xsl:for-each select="*/artifact/relativePath[starts-with(., $artifact-prefix)]">
			<xsl:variable name="artifact-local-path" select="concat($build-id, '/', substring-after(., $artifact-prefix))" />

			<touch file="{$artifact-local-path}" mkdirs="true" verbose="false" />
			<get src="{concat($build-url, '/artifact/', .)}" dest="{$artifact-local-path}" verbose="false" />
		</xsl:for-each>
	</xsl:template>
</xsl:transform>
