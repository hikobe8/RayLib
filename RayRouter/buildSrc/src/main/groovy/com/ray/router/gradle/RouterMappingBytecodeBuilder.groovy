package com.ray.router.gradle

import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.FieldVisitor
import jdk.internal.org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class RouterMappingBytecodeBuilder implements Opcodes {

    static final CLASS_NAME = 'com/ray/router/mapping/generated/RouterMapping'

    static byte[] get(Set<String> allMappingNames) {
        MethodVisitor methodVisitor

        //1.创建一个类
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        classWriter.visit(V1_8,
                ACC_PUBLIC | ACC_SUPER, CLASS_NAME,
                null,
                "java/lang/Object",
                null)
        //2.创建构造方法
        methodVisitor = classWriter.visitMethod(ACC_PUBLIC,
                "<init>", "()V",
                null,
                null)
        methodVisitor.visitCode()
        methodVisitor.visitVarInsn(ALOAD, 0)
        methodVisitor.visitMethodInsn(INVOKESPECIAL,
                "java/lang/Object",
                "<init>",
                "()V",
                false)
        methodVisitor.visitInsn(RETURN)
        methodVisitor.visitMaxs(1, 1)
        methodVisitor.visitEnd()
        //3.创建get方法
        methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "get", "()Ljava/util/Map;", "()Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;", null)
        ///3.1 创建HashMap
        methodVisitor.visitCode()
        methodVisitor.visitTypeInsn(NEW, "java/util/HashMap")
        methodVisitor.visitInsn(DUP)
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/util/HashMap", "<init>", "()V", false)
        methodVisitor.visitVarInsn(ASTORE, 0)
        //3.2 向Map中添加所有映射表的内容
        allMappingNames.each {
            methodVisitor.visitVarInsn(ALOAD, 0)
            methodVisitor.visitMethodInsn(INVOKESTATIC,
                    "com/ray/rayrouter/mapping/$it",
                    "get",
                    "()Ljava/util/Map;",
                    false)
            methodVisitor.visitMethodInsn(INVOKEINTERFACE,
                    "java/util/Map",
                    "putAll",
                    "(Ljava/util/Map;)V",
                    true)
        }
        //3.3 返回map
        methodVisitor.visitVarInsn(ALOAD, 0)
        methodVisitor.visitInsn(ARETURN)
        methodVisitor.visitMaxs(2, 1)
        methodVisitor.visitEnd()

        classWriter.visitEnd()

        return classWriter.toByteArray()
    }

}