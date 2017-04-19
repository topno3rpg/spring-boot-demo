import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.File;
import java.io.FileOutputStream;

public class EditClass {
    public static void main(String[] args) throws Exception {

        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath("D:\\爱学贷项目\\配置中心\\tech-sdk-1.0.1.jar");
        CtClass ctClass = pool.getCtClass("com.timevale.esign.sdk.tech.service.impl.EsignsdkServiceImpl");
        CtClass[] param = new CtClass[2];
        param[0] = pool.get("java.lang.String");
        param[1] = pool.get("java.lang.String");
        CtMethod method = ctClass.getDeclaredMethod("init", param);
        System.err.println(method.getName());
//		method.insertBefore("Log.d($2);");
        method.addLocalVariable("xxx", pool.get("java.lang.String"));
        method.addParameter(pool.get("java.lang.String"));
        method.addParameter(pool.get("java.lang.String"));
        byte[] byteArr = ctClass.toBytecode();
        FileOutputStream fos = new FileOutputStream(new File("D:\\爱学贷项目\\配置中心\\EsignsdkServiceImpl.class"));
        fos.write(byteArr);
        fos.close();
        System.out.println("over!!");

        // FileInputStream fis = new FileInputStream(filePath);

        // DataInputStream di = new DataInputStream(fis);
        // ClassFile cf = new ClassFile(di);
        // // cf.setConstantPool(infos);
        // fis.close();
        // File f = new File(filePath);
        // // ClassFileWriter.writeToFile(f, cf);
    }
}