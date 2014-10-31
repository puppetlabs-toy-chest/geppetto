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
package com.puppetlabs.geppetto.forge.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jrubyparser.CompatVersion;
import org.jrubyparser.Parser;
import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.Colon2Node;
import org.jrubyparser.ast.ConstNode;
import org.jrubyparser.ast.DStrNode;
import org.jrubyparser.ast.EvStrNode;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;
import org.jrubyparser.ast.RootNode;
import org.jrubyparser.ast.StrNode;
import org.jrubyparser.ast.SymbolNode;
import org.jrubyparser.lexer.SyntaxException;
import org.jrubyparser.parser.ParserConfiguration;

import com.puppetlabs.geppetto.common.os.StreamUtil;

public abstract class RubyParserUtils {

	private static String evalColon2Node(Colon2Node c2cNode) throws IOException {
		// Check if this is a known constant
		Node qualNode = c2cNode.getLeftNode();
		if(qualNode instanceof ConstNode) {
			String qual = ((ConstNode) qualNode).getName();
			String name = c2cNode.getName();
			return getRubyConstant(qual, name);
		}
		throw new IOException("Unable to evaluate node " + c2cNode.getName() + "::<expr> into a string");
	}

	/**
	 * Perform best-effort evaluation of interpolated string
	 *
	 * @param node
	 * @return
	 */
	private static String evalDStrNode(DStrNode node) throws IOException {
		StringBuilder bld = new StringBuilder();
		for(Node child : node.childNodes())
			bld.append(stringValue(child));
		return bld.toString();
	}

	/**
	 * Perform best-effort evaluation of expression in interpolated string
	 *
	 * @param node
	 *            The #{} expression
	 * @return The string that the expression evaluates to
	 */
	private static String evalEvStrNode(EvStrNode node) throws IOException {
		Node body = node.getBody();
		try {
			if(body instanceof NewlineNode)
				// Expressions are aways wrapped in NewlineNode even when there's no newline, go figure
				return stringValue(((NewlineNode) body).getNextNode());
		}
		catch(IOException e) {
		}
		return "#{<expr>}";
	}

	/**
	 * Find a list of nodes that correspond to a specific path of node types extending
	 * from a root node. The type of the root node is not included in the path.
	 *
	 * @param root
	 *            The root node
	 * @param path
	 *            The node type path
	 * @return The list of nodes, possibly empty but never <code>null</code>
	 */
	public static List<Node> findNodes(Node root, NodeType[] path) {
		if(root == null || path == null)
			return Collections.emptyList();

		ArrayList<Node> result = new ArrayList<Node>();
		findNodes(root, path, 0, result);
		return result;
	}

	private static void findNodes(Node root, NodeType[] path, int pathIndex, List<Node> result) {
		if(pathIndex >= path.length) {
			result.add(root);
			return;
		}

		NodeType searchedType = path[pathIndex++];
		for(Node child : root.childNodes()) {
			while(child instanceof NewlineNode)
				child = ((NewlineNode) child).getNextNode();
			if(child == null)
				continue;
			if(child.getNodeType() == searchedType)
				findNodes(child, path, pathIndex, result);
		}
	}

	/**
	 * Return the value of well known String constants such as File::PATH_SEPARATOR
	 *
	 * @param qual
	 *            The qualifier, e.g. &quot;File&quot;
	 * @param name
	 *            The name, e.g. &quot;PATH_SEPARATOR&quot;
	 * @return The string value
	 * @throws IOException
	 *             when the value is unknown
	 */
	private static String getRubyConstant(String qual, String name) throws IOException {
		if("File".equals(qual)) {
			if("SEPARATOR".equals(name) || "Separator".equals(name))
				return File.separator;
			if("PATH_SEPARATOR".equals(name))
				return File.pathSeparator;
		}
		throw new IOException("Unable to evaluate constant " + qual + "::" + name + " into a string");
	}

	public static RootNode parse(String id, Reader reader) throws SyntaxException {
		Parser parser = new Parser();
		return (RootNode) parser.parse(id, reader, new ParserConfiguration(0, CompatVersion.RUBY2_0));
	}

	/**
	 * Parse a File containing Ruby syntax and return the root node of the AST.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static RootNode parseFile(File file) throws IOException, SyntaxException {
		String fileStr = file.getAbsolutePath();
		Reader reader = new BufferedReader(new FileReader(file));
		try {
			return parse(fileStr, reader);
		}
		finally {
			StreamUtil.close(reader);
		}
	}

	public static RootNode parseString(String id, String content) throws SyntaxException {
		return parse(id, new StringReader(content));
	}

	public static String stringValue(Node node) throws IOException {
		switch(node.getNodeType()) {
			case NEWLINENODE:
				return "\n";
			case COMMENTNODE:
				return "";
			case SYMBOLNODE:
				return ((SymbolNode) node).getName();
			case STRNODE:
				return ((StrNode) node).getValue();
			case FALSENODE:
				return "false";
			case TRUENODE:
				return "true";
			case DSTRNODE:
				return evalDStrNode((DStrNode) node);
			case EVSTRNODE:
				return evalEvStrNode((EvStrNode) node);
			case COLON2NODE:
				return evalColon2Node((Colon2Node) node);
			case CALLNODE: {
				// We can handle simple string concatenation
				CallNode argCall = (CallNode) node;
				if("+".equals(argCall.getName())) {
					StringBuilder bld = new StringBuilder();
					bld.append(stringValue(argCall.getReceiver()));
					for(Node arg : argCall.getArgs().childNodes())
						bld.append(stringValue(arg));
					return bld.toString();
				}
				throw new IOException("Unable to evaluate call node " + argCall.getName() + " into a string");
			}
			default:
				throw new IOException("Unable to evaluate node of type " + node.getNodeType() + " into a string");
		}
	}
}
