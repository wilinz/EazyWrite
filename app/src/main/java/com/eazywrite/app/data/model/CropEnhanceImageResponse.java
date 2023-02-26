package com.eazywrite.app.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CropEnhanceImageResponse {

    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("version")
    private String version;
    @SerializedName("duration")
    private Integer duration;
    @SerializedName("result")
    private ResultDTO result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public static class ResultDTO {
        @SerializedName("image_list")
        private List<ImageListDTO> imageList;
        @SerializedName("origin_width")
        private Integer originWidth;
        @SerializedName("origin_height")
        private Integer originHeight;

        public List<ImageListDTO> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageListDTO> imageList) {
            this.imageList = imageList;
        }

        public Integer getOriginWidth() {
            return originWidth;
        }

        public void setOriginWidth(Integer originWidth) {
            this.originWidth = originWidth;
        }

        public Integer getOriginHeight() {
            return originHeight;
        }

        public void setOriginHeight(Integer originHeight) {
            this.originHeight = originHeight;
        }

        public static class ImageListDTO {
            @SerializedName("position")
            private List<Integer> position;
            @SerializedName("cropped_height")
            private Integer croppedHeight;
            @SerializedName("image")
            private String image;
            @SerializedName("cropped_width")
            private Integer croppedWidth;
            @SerializedName("angle")
            private Integer angle;

            public List<Integer> getPosition() {
                return position;
            }

            public void setPosition(List<Integer> position) {
                this.position = position;
            }

            public Integer getCroppedHeight() {
                return croppedHeight;
            }

            public void setCroppedHeight(Integer croppedHeight) {
                this.croppedHeight = croppedHeight;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public Integer getCroppedWidth() {
                return croppedWidth;
            }

            public void setCroppedWidth(Integer croppedWidth) {
                this.croppedWidth = croppedWidth;
            }

            public Integer getAngle() {
                return angle;
            }

            public void setAngle(Integer angle) {
                this.angle = angle;
            }
        }
    }
}
