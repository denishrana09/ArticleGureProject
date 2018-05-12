package com.example.denish.articlegureproject;

/**
 * Created by denish on 11/5/18.
 */

public class DataItem {
    String imgPic;
    String imgText;
    int likes;

    public DataItem() {
    }

    public DataItem(String imgPic, String imgText, int likes) {
        this.imgPic = imgPic;
        this.imgText = imgText;
        this.likes = likes;
    }

    public String getImgPic() {
        return imgPic;
    }

    public void setImgPic(String imgPic) {
        this.imgPic = imgPic;
    }

    public String getImgText() {
        return imgText;
    }

    public void setImgText(String imgText) {
        this.imgText = imgText;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "imgPic='" + imgPic + '\'' +
                ", imgText='" + imgText + '\'' +
                ", likes=" + likes +
                '}';
    }
}
