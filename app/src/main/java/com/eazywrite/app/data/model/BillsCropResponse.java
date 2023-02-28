package com.eazywrite.app.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillsCropResponse {

    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
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

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public static class ResultDTO {
        @SerializedName("object_list")
        private List<ObjectListDTO> objectList;

        public List<ObjectListDTO> getObjectList() {
            return objectList;
        }

        public void setObjectList(List<ObjectListDTO> objectList) {
            this.objectList = objectList;
        }

        public static class ObjectListDTO {
            @SerializedName("image_angle")
            private Integer imageAngle;
            @SerializedName("rotated_image_width")
            private Integer rotatedImageWidth;
            @SerializedName("rotated_image_height")
            private Integer rotatedImageHeight;
            @SerializedName("position")
            private List<Integer> position;
            @SerializedName("class")
            private String classX;
            @SerializedName("type")
            private String type;
            @SerializedName("type_description")
            private String typeDescription;
            @SerializedName("item_list")
            private List<ItemListDTO> itemList;

            public Integer getImageAngle() {
                return imageAngle;
            }

            public void setImageAngle(Integer imageAngle) {
                this.imageAngle = imageAngle;
            }

            public Integer getRotatedImageWidth() {
                return rotatedImageWidth;
            }

            public void setRotatedImageWidth(Integer rotatedImageWidth) {
                this.rotatedImageWidth = rotatedImageWidth;
            }

            public Integer getRotatedImageHeight() {
                return rotatedImageHeight;
            }

            public void setRotatedImageHeight(Integer rotatedImageHeight) {
                this.rotatedImageHeight = rotatedImageHeight;
            }

            public List<Integer> getPosition() {
                return position;
            }

            public void setPosition(List<Integer> position) {
                this.position = position;
            }

            public String getClassX() {
                return classX;
            }

            public void setClassX(String classX) {
                this.classX = classX;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTypeDescription() {
                return typeDescription;
            }

            public void setTypeDescription(String typeDescription) {
                this.typeDescription = typeDescription;
            }

            public List<ItemListDTO> getItemList() {
                return itemList;
            }

            public void setItemList(List<ItemListDTO> itemList) {
                this.itemList = itemList;
            }

            public static class ItemListDTO {
                @SerializedName("key")
                private String key;
                @SerializedName("value")
                private String value;
                @SerializedName("position")
                private List<Integer> position;
                @SerializedName("description")
                private String description;

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public List<Integer> getPosition() {
                    return position;
                }

                public void setPosition(List<Integer> position) {
                    this.position = position;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }
            }
        }
    }
}
