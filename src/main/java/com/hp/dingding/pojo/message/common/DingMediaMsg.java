package com.hp.dingding.pojo.message.common;

import com.hp.dingding.pojo.message.AbstractDingMsg;
import com.hp.dingding.pojo.message.IDingBotMsg;
import com.hp.dingding.service.file.media.DingMediaType;
import lombok.Getter;
import lombok.Setter;


/**
 * @author hp
 */
@Getter
@Setter
public class DingMediaMsg {


    @Getter
    @Setter
    public static class SampleAudio extends AbstractDingMsg implements IDingBotMsg {
        private String mediaId;
        private String duration;

        /**
         * @param duration 语音消息时长，毫秒
         */
        public SampleAudio(String mediaId, long duration) {
            this.mediaId = mediaId;
            this.duration = String.valueOf(duration);
        }
    }

    @Getter
    @Setter
    public static class SampleFile extends AbstractDingMsg implements IDingBotMsg  {
        private String mediaId;
        private String fileName;
        private String fileType;

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

    @Getter
    @Setter
    public static class SampleVideo extends AbstractDingMsg implements IDingBotMsg  {
        private String videoMediaId;
        private String videoType;
        private String picMediaId;
        private String duration;

        /**
         * @param duration 语音消息时长，单位秒。
         */
        public SampleVideo(String videoMediaId, String picMediaId, long duration) {
            this.videoMediaId = videoMediaId;
            this.videoType = DingMediaType.VIDEO.getSuffix();
            this.picMediaId = picMediaId;
            this.duration = String.valueOf(duration);
        }
    }
}
