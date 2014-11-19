package com.puppetlabs.geppetto.ruby.jrubyparser;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jrubyparser.ast.ArrayNode;
import org.jrubyparser.ast.BlockNode;
import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.Colon2Node;
import org.jrubyparser.ast.ConstDeclNode;
import org.jrubyparser.ast.ConstNode;
import org.jrubyparser.ast.DStrNode;
import org.jrubyparser.ast.EvStrNode;
import org.jrubyparser.ast.HashNode;
import org.jrubyparser.ast.IterNode;
import org.jrubyparser.ast.ListNode;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.StrNode;
import org.jrubyparser.ast.SymbolNode;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Evaluates (a limited set of) Ruby constant expressions. TODO: Colon3Node
 * (i.e. name relative to global root) not handled - as FQN are returned as list
 * of String there is currently no marker if it is relative or absolute. {@link }
 */
public class ConstEvaluator extends AbstractJRubyVisitor {
	private static final Map<String, Map<String, String>> defaultConstants;

	static {
		defaultConstants = Maps.newHashMap();
		Map<String, String> fileConstants = Maps.newHashMap();
		fileConstants.put("SEPARATOR", File.separator);
		fileConstants.put("Separator", File.separator);
		fileConstants.put("PATH_SEPARATOR", File.pathSeparator);
		defaultConstants.put("File", fileConstants);
	}

	private static String getRubyConstant(String qual, String name) {
		String value = null;
		Map<String, String> constants = defaultConstants.get(qual);
		if(constants != null)
			value = constants.get(name);
		return value;
	}

	private final Map<String, String> constants = Maps.newHashMap();

	public ConstEvaluator(Node root) {
		new AbstractJRubyVisitor() {
			@Override
			public Object visitConstDeclNode(ConstDeclNode node) {
				Node p = realParent(node.getParent());
				if(p instanceof BlockNode) {
					p = realParent(p.getParent());
					if(p instanceof IterNode) {
						p = realParent(p.getParent());
						if(p instanceof CallNode) {
							String n = ((CallNode) p).getName();
							if("newtype".equals(n) || "newprovider".equals(n) || "newfunction".equals(n))
								// Constant is declared at root block of known call
								constants.put(node.getName(), evalToString(node.getValue()));
						}
					}
				}
				return p;
			}
		}.all(root);
	}

	private List<String> addAll(List<String> a, List<String> b) {
		if(a.isEmpty())
			return b;
		if(b.isEmpty())
			return a;
		List<String> result = Lists.newArrayListWithExpectedSize(a.size() + b.size());
		result.addAll(a);
		result.addAll(b);
		return result;
	}

	private void appendAsString(Object val, StringBuilder bld) {
		if(val == null)
			return;

		if(val instanceof String)
			bld.append(val);
		else if(val instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> map = (Map<Object, Object>) val;
			bld.append('{');
			int start = bld.length();
			for(Entry<Object, Object> e : map.entrySet()) {
				if(bld.length() > start)
					bld.append(",");
				appendAsString(e.getKey(), bld);
				bld.append(" : ");
				appendAsString(e.getValue(), bld);
			}
			bld.append('}');
		}
		else {
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) val;
			for(String elem : list)
				bld.append(elem);
		}
	}

	private void appendEvalAsString(Node node, StringBuilder bld) {
		appendAsString(eval(node), bld);
	}

	public Object eval(Node node) {
		if(node == null)
			return null;
		// Can't visit ListNode, because they are not supposed to be "evaluated" (duh! impl sucks).
		// Must compare against exact class since everything derived implements "accept".
		if(node.getClass() == ListNode.class)
			return this.visitListNode((ListNode) node);
		return node.accept(this);
	}

	public String evalToString(Node node) {
		StringBuilder bld = new StringBuilder();
		appendEvalAsString(node, bld);
		return bld.toString();
	}

	private Node realParent(Node n) {
		while(n instanceof NewlineNode)
			n = n.getParent();
		return n;
	}

	private List<String> splice(Object a, Object b) {
		return addAll(stringList(a), stringList(b));
	}

	public List<String> stringList(Object x) {
		if(x instanceof List) {
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) x;
			return list;
		}
		if(x instanceof String)
			return Collections.singletonList((String) x);
		return Collections.emptyList(); // empty list
	}

	@Override
	public Object visitArrayNode(ArrayNode iVisited) {
		List<Node> children = iVisited.childNodes();
		int sz = children.size();
		if(sz == 0)
			return Collections.emptyList();
		if(sz == 1)
			return Collections.singletonList(eval(children.get(0)));
		List<Object> result = Lists.newArrayListWithCapacity(sz);
		for(int idx = 0; idx < sz; ++idx)
			result.add(eval(children.get(idx)));
		return result;
	}

	@Override
	public Object visitCallNode(CallNode argCall) {
		if("+".equals(argCall.getName())) {
			StringBuilder bld = new StringBuilder();
			appendEvalAsString(argCall.getReceiver(), bld);
			for(Node arg : argCall.getArgs().childNodes())
				appendEvalAsString(arg, bld);
			return bld.toString();
		}
		if("intern".equals(argCall.getName()))
			return eval(argCall.getReceiver());
		return null;
	}

	@Override
	public Object visitColon2Node(Colon2Node c2cNode) {
		// Check if this is a known constant
		Node qualNode = c2cNode.getLeftNode();
		if(qualNode instanceof ConstNode) {
			String qual = ((ConstNode) qualNode).getName();
			String name = c2cNode.getName();
			String constant = getRubyConstant(qual, name);
			if(constant != null)
				return constant;
		}
		return splice(eval(qualNode), c2cNode.getName());
	}

	@Override
	public Object visitConstNode(ConstNode iVisited) {
		String constValue = null;
		if(realParent(iVisited.getParent()) instanceof EvStrNode)
			constValue = constants.get(iVisited.getName());
		if(constValue == null)
			constValue = iVisited.getName();
		return constValue;
	}

	/**
	 * Perform best-effort evaluation of interpolated string
	 *
	 * @param node
	 * @return
	 */
	@Override
	public Object visitDStrNode(DStrNode node) {
		StringBuilder bld = new StringBuilder();
		for(Node child : node.childNodes())
			bld.append(eval(child));
		return bld.toString();
	}

	/**
	 * Perform best-effort evaluation of expression in interpolated string
	 *
	 * @param node
	 *            The #{} expression
	 * @return The string that the expression evaluates to
	 */
	@Override
	public Object visitEvStrNode(EvStrNode node) {
		StringBuilder bld = new StringBuilder();
		appendEvalAsString(node.getBody(), bld);
		return bld.toString();
	}

	@Override
	public Object visitHashNode(HashNode iVisited) {
		Map<Object, Object> result = Maps.newHashMap();
		List<Node> children = iVisited.childNodes();
		children = children.get(0).childNodes();
		for(int i = 0; i < children.size(); i++) {
			Object key = eval(children.get(i++));
			Object value = eval(children.get(i));
			result.put(key, value);
		}
		return result;
	}

	@Override
	public Object visitListNode(ListNode iVisited) {
		List<Object> result = Lists.newArrayList();
		for(Node n : iVisited.childNodes())
			result.add(eval(n));
		return result;
	}

	@Override
	public Object visitNewlineNode(NewlineNode nlNode) {
		return eval(nlNode.getNextNode());
	}

	@Override
	public Object visitStrNode(StrNode iVisited) {
		return iVisited.getValue();
	}

	@Override
	public Object visitSymbolNode(SymbolNode iVisited) {
		return iVisited.getName();
	}
}
