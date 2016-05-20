/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.springStarter.samples.support;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.lamfire.logger.Logger;
import com.lamfire.logger.LoggerFactory;
import com.lamfire.utils.IOUtils;
import com.lamfire.utils.StringUtils;

/**
 * @author zxc May 20, 2016 9:15:56 PM
 */
public class FileHelper {

    private static final Logger             logger    = LoggerFactory.getLogger(FileHelper.class);

    public interface IDo {

        void work(String data);
    }

    /**
     * 保存文件
     * 
     * @param line
     * @param fileName
     */
    public void save(String line, String fileName) {
        try {
            IOUtils.write(new FileOutputStream(new File(fileName), true), line + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取resource文件
     * 
     * @param fileName
     * @param ido
     * @throws Exception
     */
    public void readSourceFile(String fileName, IDo ido) throws Exception {
        URL filePath = Thread.currentThread().getContextClassLoader().getResource(fileName);
        readFile(filePath.getFile(), ido);
    }

    /**
     * 读取特定路径下文件,以不消耗内存为前提
     * 
     * @param filePath
     * @param ido
     * @throws Exception
     */
    public void readFile(String filePath, IDo ido) throws Exception {
        LineIterator it = FileUtils.lineIterator(new File(filePath), "UTF-8");
        try {
            while (it.hasNext()) {
                String data = it.nextLine();
                if (StringUtils.isBlank(data)) break;
                try {
                    ido.work(data);
                } catch (Exception e) {
                    logger.error("ido.work error!", e);
                    continue;
                }
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
    }
}
