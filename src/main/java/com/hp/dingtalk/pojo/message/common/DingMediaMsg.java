package com.hp.dingtalk.pojo.message.common;

import com.hp.dingtalk.pojo.message.AbstractDingMsg;
import com.hp.dingtalk.pojo.message.IDingBotMsg;
import com.hp.dingtalk.service.file.media.DingMediaType;
import lombok.Value;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


/**
 * @author hp
 */
public interface DingMediaMsg {

    @Value
    class SampleAudio extends AbstractDingMsg implements IDingBotMsg {
        String mediaId;
        String duration;

        public SampleAudio(String mediaId, Duration duration) {
            this.mediaId = mediaId;
            this.duration = String.valueOf(duration.get(ChronoUnit.SECONDS));
        }
    }

    @Value
    class SampleFile extends AbstractDingMsg implements IDingBotMsg {
        String mediaId;
        String fileName;
        String fileType;

        public SampleFile(String mediaId, String fileName, SampleFileType fileType) {
            this.mediaId = mediaId;
            this.fileName = fileName;
            this.fileType = fileType.name();
        }

        public enum SampleFileType {
            /***/
            xlsx, pdf, zip, rar, doc, docx
        }
    }

    @Value
    class SampleVideo extends AbstractDingMsg implements IDingBotMsg {
        String videoMediaId;
        String videoType;
        String picMediaId;
        String duration;

        public SampleVideo(String videoMediaId, String picMediaId, Duration duration) {
            this.videoMediaId = videoMediaId;
            this.videoType = DingMediaType.VIDEO.getSuffix();
            this.picMediaId = picMediaId;
            this.duration = String.valueOf(duration.get(ChronoUnit.SECONDS));
        }
    }
}
