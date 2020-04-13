package com.summer.springboot.groovy.test;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.control.CompilationFailedException;

import java.io.File;
import java.io.IOException;

/**
 * @Author liuwei
 * @Description //TODO
 * @Date 1$ 1$
 **/
@Slf4j
public class Test {

	// 调用evaluate方法直接执行一段Groovy
	public static void testGroovy1() throws CompilationFailedException, IOException {
		GroovyShell groovyShell = new GroovyShell();
		groovyShell.evaluate("println 'My First Groovy shell.'");

	}

	// 调用GroovyShell_1_1
	public static void testGroovy2() throws CompilationFailedException, IOException {
		GroovyShell groovyShell = new GroovyShell();
		Object result = groovyShell.evaluate(new File("D:\\code\\summer\\summer-java\\summer-groovy\\src\\main\\java\\com\\summer\\groovy\\demo\\groovy\\GroovyShell_1_1.groovy"));

		System.out.println(result.toString());
	}

	// 调用GroovyShell_1_2
	public static void testGroovy3() throws CompilationFailedException, IOException {
		// 调用带参数的groovy shell时，使用bind绑定数据
		Binding binding = new Binding();
		binding.setProperty("name", "lll");

		GroovyShell groovyShell = new GroovyShell(binding);
		Object result = groovyShell.evaluate(new File("D:\\code\\summer\\summer-java\\summer-groovy\\src\\main\\java\\com\\summer\\groovy\\demo\\groovy\\GroovyShell_1_2.groovy"));
		System.out.println(result.toString());
	}


	public static void main(String[] args) throws IOException {
//		Test.testGroovy1();
//		Test.testGroovy2();
		Test.testGroovy3();
	}
}
