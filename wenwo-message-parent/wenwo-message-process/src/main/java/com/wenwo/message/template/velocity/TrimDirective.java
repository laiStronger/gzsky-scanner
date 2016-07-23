/**
 * 
 */
package com.wenwo.message.template.velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

/**
 * trim 指令
 * @author "StanleyDing"
 * @date 2013-9-26
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-26,	"StanleyDing", 	Create
 */
public class TrimDirective extends Directive {

	@Override
	public String getName() {
		return "textLimit";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		Node variableNode = node.jjtGetChild(0);
		StringWriter interWriter = new StringWriter(64);
		variableNode.render(context, interWriter);
		String text = interWriter.toString();

		SimpleNode sizeNode = (SimpleNode)node.jjtGetChild(1);
		int size;
		try {
			size = (Integer)sizeNode.value(context);
		} catch (Exception e) {
			throw new VelocityException("取指令第二个长度参数报错：", e);
		}
		
		if(text.length()>size) {
			text = text.substring(0,size-3) +"...";
		}
		writer.write(text);
		return true;
	}

}
