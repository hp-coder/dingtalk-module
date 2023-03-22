package com.hp.dingding.pojo.file;

import com.hp.dingding.service.file.media.DingMediaType;
import com.taobao.api.FileItem;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author hp 2023/3/15
 */
@Getter
public class MediaRequest {
    private final DingMediaType mediaType;
    private final String fileName;
    private final InputStream inputStream;
    private String mimeType;

    public MediaRequest(DingMediaType mediaType, String fileName, InputStream inputStream) throws IOException {
        this.mediaType = mediaType;
        this.fileName = fileName;
        this.inputStream = inputStream;
    }

    public MediaRequest(DingMediaType mediaType, String fileName, InputStream inputStream, String mimeType) throws IOException {
        this.mediaType = mediaType;
        this.fileName = fileName;
        this.inputStream = inputStream;
        this.mimeType = mimeType;
    }

    @PostConstruct
    private void validate() throws IOException {
        Assert.hasText(fileName, "文件名不能为空");
        final String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        Assert.isTrue(mediaType.getSuffix().contains(suffix), "非法文件类型" + suffix + "，请上传以下合法文件类型：" + mediaType.getSuffix());
        Assert.isTrue(inputStream.available() <= mediaType.getSize(),"文件大小超过限制，合法文件大小为：" + mediaType.getSize()+"b");
    }

    public FileItem toFileItem(){
        return new FileItem(fileName, inputStream, mimeType);
    }
}
