package io.github.horvathandris.localisation.generator.builder

import kotlin.test.Test
import kotlin.test.assertEquals

class JavaSourceBuilderTest {

    @Test
    fun `imports are ordered alphabetically`() {
        val source = JavaSourceBuilder()
            .setPackageName("io.github.horvathandris.something")
            .addImport("java.a.b.X")
            .addImport("java.a.b.A")
            .addImport("java.b.X")
            .addImport("java.a.X")
            .build(4)

        val expected = """
            package io.github.horvathandris.something;

            import java.a.X;
            import java.a.b.A;
            import java.a.b.X;
            import java.b.X;
            
            
        """.trimIndent()

        assertEquals(expected, source)
    }

    @Test
    fun `can generate nested classes`() {
        val source = JavaSourceBuilder()
            .setPackageName("com.example")
            .addImport("java.util.List")
            .addClass(
                JavaSourceBuilder.ClassBuilder()
                    .setName("Outer")
                    .addField("private int x;")
                    .addNestedClasses(
                        JavaSourceBuilder.ClassBuilder()
                            .setName("Inner")
                            .setModifiers("public static final")
                            .addField("private String y;")
                            .addMethod(
                                JavaSourceBuilder.MethodBuilder()
                                    .setName("getY")
                                    .setReturnType("String")
                                    .setModifiers("public")
                                    .addBodyLine("return y;")
                            )
                    )
            )
            .build(4)

        val expected = """
            package com.example;

            import java.util.List;
            
            public class Outer {
                private int x;
                public static final class Inner {
                    private String y;
                    public String getY() {
                        return y;
                    }
                }
            }
        """.trimIndent()

        assertEquals(expected, source.trim())
    }

    @Test
    fun `can generate method with parameters and body`() {
        val method = JavaSourceBuilder.MethodBuilder()
            .setName("helloWorld")
            .setReturnType("String")
            .setModifiers("public static")
            .addParameter("String", "name")
            .addBodyLine("return \"Hello, \" + name + \"!\";")
            .build(4, 0)

        val expected = """
            public static String helloWorld(String name) {
                return "Hello, " + name + "!";
            }
        """.trimIndent()

        assertEquals(expected, method.trim())
    }
}