package com.learn.robot;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 直接运行此类即可生成增量包。
 * 运行前需要自己定义下面3个静态变量
 * @author Steven
 */
public class FreePathStevenUtil {

    public  String patchFile="/Users/dinggang/Desktop/xxx.patch";//补丁文件,由eclipse svn plugin生成(非必须)

    public String projectPath = "/Users/dinggang/work/custservpls/src/main/webapp/custservpls";//项目路径

    public String desPath = "/Users/dinggang/Downloads/classes/";//补丁存放路径

    public  String classesPath="" +
            "/Users/dinggang/work/sysrightpls/src/main/java/com/unicom/sysrightpls/rightMgr/staffRightHighRisk/StaffRightHighRisk.java\n" +
            "/Users/dinggang/work/sysrightpls/src/main/webapp/app/sysrightman/rightMgr/staffRightHighRisk/staffRightHighRisk.html\n" +
            "/Users/dinggang/work/sysrightpls/src/main/webapp/app/sysrightman/rightMgr/staffRightHighRisk/staffRightHighRisk.page\n" +
            "/Users/dinggang/work/sysrightpls/src/main/webapp/sysrightpls/styles/rightMgr/staffRightHighRisk/staffRightHighRisk.css\n" +
            "/Users/dinggang/work/sysrightpls/src/main/webapp/sysrightpls/sysrightman/rightMgr/staffRightHighRisk/assembly.js\n" +
            "/Users/dinggang/work/sysrightpls/src/main/webapp/sysrightpls/sysrightman/rightMgr/staffRightHighRisk/init.js\n" +
            "/Users/dinggang/work/sysrightpls/src/main/webapp/sysrightpls/sysrightman/rightMgr/staffRightHighRisk/main.js\n" +
            "/Users/dinggang/work/sysrightpls/src/main/webapp/WEB-INF/page.application\n" +
            "/Users/dinggang/work/sysrightpls/src/main/webapp/Sidebar.html";//文件路径mac



    @Test
    public void test() throws Exception {
        copyFiles(getPatchFileList());
    }

    /****
     * 读取补丁配置文件解析出修改的文件并返回到list集合
     * (静态变量classesPath不为空读取本身，为空则读取CRM.patch文件)
     * @return
     * @throws Exception
     */
    public List<String> getPatchFileList() throws Exception {
        List<String> fileList = new ArrayList<String>();
        if (StringUtils.isNotEmpty(classesPath.replaceAll(" ",""))) {
            List<String> lines = Arrays.asList(classesPath.replaceAll(" ","").split("\n"));
            if (CollectionUtils.isNotEmpty(lines)) {
                lines.stream().filter(o -> o != null && !o.isEmpty()).collect(Collectors.toList());
                fileList.addAll(lines);
            }
        } else {
            FileInputStream f = new FileInputStream(patchFile);
            BufferedReader dr = new BufferedReader(new InputStreamReader(f, "utf-8"));
            String line;
            while ((line = dr.readLine()) != null) {
                if (!line.isEmpty()) {
                    line=(line.indexOf("Index:")!=-1)?line.substring(line.indexOf(":")+1,line.length()):line;
                    fileList.add(line);
                }
            }
            dr.close();
        }
//        fileList=fileList.stream().map(o -> o.replaceAll(" ", "").replaceAll(projectPath + "/src/main/java/", "")
//                .replaceAll(projectPath + "/src/main/resources/", "").replaceAll(".java", ".class")
//                .replaceAll("\\\\","/")).collect(Collectors.toList());
        return fileList;
    }

    /***
     *
     * @param list 修改的文件
     */
    public void copyFiles(List<String> list) {
        for (String fullFileName : list) {
            String fullDesFileNameStr = desPath + fullFileName.replaceAll("/Volumes/Steven/990公司/asiainfo/works-asiainfo/custservplseeeeeee/src/main","");
            fullFileName =fullFileName;//将要复制的文件全路径
            String desFilePathStr = fullDesFileNameStr.substring(0, fullDesFileNameStr.lastIndexOf("/"));
            File desFilePath = new File(desFilePathStr);
            if (!desFilePath.exists()) {
                desFilePath.mkdirs();
            }
            copyFile(fullFileName, fullDesFileNameStr);
            copyInnerClassFile(fullFileName, fullDesFileNameStr);//遍历目录，是否存在内部类，如果有内部，则将所有的额内部类挑选出来放到
        }

    }

    /***
     * 处理内部类的情况
     * 解析源路径名称，遍历此文件路径下是否存在这个类的内部类
     * 内部类编译后的格式一般是 OuterClassName$InnerClassName.class
     * @param sourceFullFileName 原路径
     * @param desFullFileName 目标路径
     */
    private void copyInnerClassFile(String sourceFullFileName, String desFullFileName) {
        String sourceFileName = sourceFullFileName.substring(sourceFullFileName.lastIndexOf("/") + 1);
        String sourcePackPath = sourceFullFileName.substring(0, sourceFullFileName.lastIndexOf("/"));
        String destPackPath = desFullFileName.substring(0, desFullFileName.lastIndexOf("/"));
        String tempFileName = sourceFileName.split("\\.")[0];
        File packFile = new File(sourcePackPath);
        if (packFile.isDirectory()) {
            String[] listFiles = packFile.list();
            for (String fileName : listFiles) {
                //可以采用正则表达式处理
                if (fileName.indexOf(tempFileName + "$") > -1 && fileName.endsWith(".class")) {
                    String newSourceFullFileName = sourcePackPath + "/" + fileName;
                    String newDesFullFileName = destPackPath + "/" + fileName;
                    copyFile(newSourceFullFileName, newDesFullFileName);
                    System.out.println("内部类复制完成:"+newSourceFullFileName);
                }
            }
        }

    }

    private void copyFile(String sourceFileNameStr, String desFileNameStr) {
        File srcFile = new File(sourceFileNameStr);
        File desFile = new File(desFileNameStr);
        try {
            copyFileDetail(srcFile, desFile);
            System.out.println("复制完成:"+srcFile);
        } catch (IOException e) {
            System.err.println("复制失败:"+srcFile+e.getMessage().substring(e.getMessage().indexOf("(")));
        }
    }


    public void copyFileDetail(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
}