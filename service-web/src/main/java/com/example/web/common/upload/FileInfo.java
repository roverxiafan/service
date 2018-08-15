package com.example.web.common.upload;

import lombok.Data;

import java.io.Serializable;

/**
 * @title: FileInfo
 * @Description: 文件信息
 * @author: roverxiafan
 * @date: 2017/01/29 09:38
 */
@Data
public class FileInfo implements Serializable {
    private static final long serialVersionUID = 9070507805148895688L;

    private String fileName;
    private String originalFilename;
    private String fileContentType;
    private String extName;
    private Long fileSize;
    private String filePath;
    private String fileUrl;
}
