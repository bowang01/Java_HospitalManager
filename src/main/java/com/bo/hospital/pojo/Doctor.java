package com.bo.hospital.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@TableName("doctor")
@ExcelTarget("doctor")
public class Doctor implements Serializable {
    @JsonProperty("dId")
    @TableId(value = "d_id")
    @Excel(name = "Account")
    private Integer dId;
    @JsonProperty("dPassword")
    @Excel(name = "Password")
    private String dPassword;
    @JsonProperty("dName")
    @Excel(name = "Name")
    private String dName;
    @JsonProperty("dGender")
    @Excel(name = "Gender")
    private String dGender;
    @JsonProperty("dCard")
    @Excel(name = "MCNZ No.")
    private String dCard;
    @JsonProperty("dEmail")
    @Excel(name = "Email")
    private String dEmail;
    @JsonProperty("dPhone")
    @Excel(name = "Phone")
    private String dPhone;
    @JsonProperty("dPost")
    @Excel(name = "Position")
    private String dPost;
    @JsonProperty("dIntroduction")
    @Excel(name = "Introduction")
    private String dIntroduction;
    @JsonProperty("dSection")
    @Excel(name = "Department")
    private String dSection;
    @JsonProperty("dState")
    @Excel(name = "On Duty", replace = {"On Duty_1","Resigned_0"})
    private Integer dState;// must be Integer; int would reset to 0 on update
    @JsonProperty("dPrice")
    @Excel(name = "Appointment Price")
    private Double dPrice;
    @JsonProperty("dPeople")
    @Excel(name = "Rating Count")
    private Integer dPeople;// must be Integer; int would reset to 0 on update
    @JsonProperty("dStar")
    @Excel(name = "Total Score")
    private Double dStar;// must be Integer; int would reset to 0 on update
    @JsonProperty("dAvgStar")
    @Excel(name = "Average Score")
    private Double dAvgStar;// must be Integer; int would reset to 0 on update

    /**
     * whether scheduled, schedule id
     */
    @TableField(exist = false)
    private String arrangeId;

    public Doctor() {
    }

    public Doctor(Integer dId, String dPassword, String dName, String dGender, String dCard, String dEmail, String dPhone, String dPost, String dIntroduction, String dSection, Integer dState, Double dPrice, Integer dPeople, Double dStar, Double dAvgStar, String arrangeId) {
        this.dId = dId;
        this.dPassword = dPassword;
        this.dName = dName;
        this.dGender = dGender;
        this.dCard = dCard;
        this.dEmail = dEmail;
        this.dPhone = dPhone;
        this.dPost = dPost;
        this.dIntroduction = dIntroduction;
        this.dSection = dSection;
        this.dState = dState;
        this.dPrice = dPrice;
        this.dPeople = dPeople;
        this.dStar = dStar;
        this.dAvgStar = dAvgStar;
        this.arrangeId = arrangeId;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public String getdPassword() {
        return dPassword;
    }

    public void setdPassword(String dPassword) {
        this.dPassword = dPassword;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdGender() {
        return dGender;
    }

    public void setdGender(String dGender) {
        this.dGender = dGender;
    }

    public String getdCard() {
        return dCard;
    }

    public void setdCard(String dCard) {
        this.dCard = dCard;
    }

    public String getdEmail() {
        return dEmail;
    }

    public void setdEmail(String dEmail) {
        this.dEmail = dEmail;
    }

    public String getdPhone() {
        return dPhone;
    }

    public void setdPhone(String dPhone) {
        this.dPhone = dPhone;
    }

    public String getdPost() {
        return dPost;
    }

    public void setdPost(String dPost) {
        this.dPost = dPost;
    }

    public String getdIntroduction() {
        return dIntroduction;
    }

    public void setdIntroduction(String dIntroduction) {
        this.dIntroduction = dIntroduction;
    }

    public String getdSection() {
        return dSection;
    }

    public void setdSection(String dSection) {
        this.dSection = dSection;
    }

    public Integer getdState() {
        return dState;
    }

    public void setdState(Integer dState) {
        this.dState = dState;
    }

    public Double getdPrice() {
        return dPrice;
    }

    public void setdPrice(Double dPrice) {
        this.dPrice = dPrice;
    }

    public Integer getdPeople() {
        return dPeople;
    }

    public void setdPeople(Integer dPeople) {
        this.dPeople = dPeople;
    }

    public Double getdStar() {
        return dStar;
    }

    public void setdStar(Double dStar) {
        this.dStar = dStar;
    }

    public Double getdAvgStar() {
        return dAvgStar;
    }

    public void setdAvgStar(Double dAvgStar) {
        this.dAvgStar = dAvgStar;
    }

    public String getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(String arrangeId) {
        this.arrangeId = arrangeId;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "dId=" + dId +
                ", dPassword='" + dPassword + '\'' +
                ", dName='" + dName + '\'' +
                ", dGender='" + dGender + '\'' +
                ", dCard='" + dCard + '\'' +
                ", dEmail='" + dEmail + '\'' +
                ", dPhone='" + dPhone + '\'' +
                ", dPost='" + dPost + '\'' +
                ", dIntroduction='" + dIntroduction + '\'' +
                ", dSection='" + dSection + '\'' +
                ", dState=" + dState +
                ", dPrice=" + dPrice +
                ", dPeople=" + dPeople +
                ", dStar=" + dStar +
                ", dAvgStar=" + dAvgStar +
                ", arrangeId='" + arrangeId + '\'' +
                '}';
    }
}
