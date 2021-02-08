package com.library.retrofit.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传类
 */
public class UploadEntity {

    private long size = 0;
    private String url;
    private List<File> files = new ArrayList<>();

    public UploadEntity(String url, File file) {
        this.url = url;
        this.files.add(file);
        initSize();
    }

    public UploadEntity(String url, List<File> files) {
        this.url = url;
        this.files = files;
        initSize();
    }

    private void initSize() {
        for (File file : files) {
            size += file.length();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public File getSimpleFile() {
        return files.get(0);
    }

    public List<File> getFiles() {
        return files;
    }

    public long getFilesTotalSize() {
        return size;
    }
}