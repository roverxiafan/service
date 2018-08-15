package com.example.web.common.upload;

import com.example.exception.BaseException;
import com.example.util.StringEx;
import com.example.web.result.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @title: FileUpload
 * @Description: 文件上传
 * @author: roverxiafan
 * @date: 2017/01/29 09:38
 */
@Slf4j
public class FileUpload {
    @Resource
    private UploadProp uploadProp;

    /**
     * 文件上传
     * @param file MultipartFile
     * @param allowedExtensions 允许的文件格式
     * @return FileInfo
     * @throws IOException IOException
     */
    public FileInfo upload(MultipartFile file, String allowedExtensions) throws IOException {
        if(file==null || file.isEmpty()){
            throw new BaseException(RetCodeEnum.EMPTY_FILE.val(), RetCodeEnum.EMPTY_FILE.msg());
        }
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String extName = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.length());

        if(StringUtils.isNoneBlank(allowedExtensions)) {
            List<String> allowedType = Arrays.asList(allowedExtensions.split(","));
            if (!allowedType.contains(extName.toLowerCase())) {
                throw new BaseException(RetCodeEnum.ILLEGAL_FILE_TYPE.val(), RetCodeEnum.ILLEGAL_FILE_TYPE.msg());
            }
        }

        List<String> forbiddenType = Arrays.asList(uploadProp.getForbiddenExtensions().split(","));
        if (forbiddenType.contains(extName)) {
            throw new BaseException(RetCodeEnum.ILLEGAL_FILE_TYPE.val(), RetCodeEnum.ILLEGAL_FILE_TYPE.msg());
        }
        String relativePath = String.format("%s%s%s.%s", generateFilepath(), File.separator, StringEx.randomToken(36), extName);
        Path path = Paths.get(String.format("%s%s%s", uploadProp.getBasePath(), File.separator, relativePath));
        if(!path.getParent().toFile().exists()) {
            Files.createDirectories(path.getParent());
        }
        if(!path.toFile().exists()) {
            Files.createFile(path);
        }

        file.transferTo(path.toFile());

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(path.getFileName().toString());
        fileInfo.setOriginalFilename(originalFileName);
        fileInfo.setExtName(extName);
        fileInfo.setFileSize(file.getSize());
        fileInfo.setFilePath(path.toAbsolutePath().toString());
        fileInfo.setFileUrl(path.toAbsolutePath().toString().replace(uploadProp.getBasePath(), uploadProp.getBaseUrl()).replaceAll("\\\\", "/"));
        fileInfo.setFileContentType(file.getContentType());

        return fileInfo;

    }

    /**
     * 删除文件或文件夹
     * @param path 文件路径
     *
     */
    public void delete(String path) {
        File file = new File(path);
        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        }
    }

    /**
     * 生成上传文件路径
     * @return file path
     */
    private String generateFilepath() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + File.separator + System.currentTimeMillis()%100;
    }

}
