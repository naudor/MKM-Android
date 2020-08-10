package com.MKMAndroid.classes;

import java.util.ArrayList;

/**
 * Created by adunjo on 24/03/14.
 */
public class Filter{

   // private Language language;
   // private Condition condition;
    private boolean conditionMT;
    private boolean conditionNM;
    private boolean conditionEX;
    private boolean conditionGD;
    private boolean conditionLP;
    private boolean conditionPL;
    private boolean conditionPO;

    private boolean languageEN;
    private boolean languageFR;
    private boolean languageGR;
    private boolean languageSP;
    private boolean languageIT;
    private boolean languageCH;
    private boolean languageJP;
    private boolean languagePT;
    private boolean languageRU;
    private boolean languageKO;
    private boolean languageCHT;

    private boolean isFoil;
    private boolean isAltered;
    private boolean isSigned;
    private boolean isPlayset;

    private int amount;

    public boolean isConditionMT() {
        return conditionMT;
    }

    public void setConditionMT(boolean conditionMT) {
        this.conditionMT = conditionMT;
    }

    public boolean isConditionNM() {
        return conditionNM;
    }

    public void setConditionNM(boolean conditionNM) {
        this.conditionNM = conditionNM;
    }

    public boolean isConditionEX() {
        return conditionEX;
    }

    public void setConditionEX(boolean conditionEX) {
        this.conditionEX = conditionEX;
    }

    public boolean isConditionGD() {
        return conditionGD;
    }

    public void setConditionGD(boolean conditionGD) {
        this.conditionGD = conditionGD;
    }

    public boolean isConditionLP() {
        return conditionLP;
    }

    public void setConditionLP(boolean conditionLP) {
        this.conditionLP = conditionLP;
    }

    public boolean isConditionPL() {
        return conditionPL;
    }

    public void setConditionPL(boolean conditionPL) {
        this.conditionPL = conditionPL;
    }

    public boolean isConditionPO() {
        return conditionPO;
    }

    public void setConditionPO(boolean conditionPO) {
        this.conditionPO = conditionPO;
    }

    public boolean isLanguageEN() {
        return languageEN;
    }

    public void setLanguageEN(boolean languageEN) {
        this.languageEN = languageEN;
    }

    public boolean isLanguageFR() {
        return languageFR;
    }

    public void setLanguageFR(boolean languageFR) {
        this.languageFR = languageFR;
    }

    public boolean isLanguageGR() {
        return languageGR;
    }

    public void setLanguageGR(boolean languageGR) {
        this.languageGR = languageGR;
    }

    public boolean isLanguageSP() {
        return languageSP;
    }

    public void setLanguageSP(boolean languageSP) {
        this.languageSP = languageSP;
    }

    public boolean isLanguageIT() {
        return languageIT;
    }

    public void setLanguageIT(boolean languageIT) {
        this.languageIT = languageIT;
    }

    public boolean isLanguageCH() {
        return languageCH;
    }

    public void setLanguageCH(boolean languageCH) {
        this.languageCH = languageCH;
    }

    public boolean isLanguageJP() {
        return languageJP;
    }

    public void setLanguageJP(boolean languageJP) {
        this.languageJP = languageJP;
    }

    public boolean isLanguagePT() {
        return languagePT;
    }

    public void setLanguagePT(boolean languagePT) {
        this.languagePT = languagePT;
    }

    public boolean isLanguageRU() {
        return languageRU;
    }

    public void setLanguageRU(boolean languageRU) {
        this.languageRU = languageRU;
    }

    public boolean isLanguageKO() {
        return languageKO;
    }

    public void setLanguageKO(boolean languageKO) {
        this.languageKO = languageKO;
    }

    public boolean isLanguageCHT() {
        return languageCHT;
    }

    public void setLanguageCHT(boolean languageCHT) {
        this.languageCHT = languageCHT;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean isSigned) {
        this.isSigned = isSigned;
    }

    public boolean isFoil() {
        return isFoil;
    }

    public void setFoil(boolean isFoil) {
        this.isFoil = isFoil;
    }

    public boolean isAltered() {
        return isAltered;
    }

    public void setAltered(boolean isAltered) {
        this.isAltered = isAltered;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isPlayset() {return isPlayset;}

    public void setPlayset(boolean isPlayset) {this.isPlayset = isPlayset;}


    public ArrayList<String> getConditionsCard(){

        ArrayList<String> conditions = new ArrayList<String>();
        if(this.conditionMT) conditions.add("MT");
        if(this.conditionNM) conditions.add("NM");
        if(this.conditionEX) conditions.add("EX");
        if(this.conditionGD) conditions.add("GD");
        if(this.conditionLP) conditions.add("LP");
        if(this.conditionPL) conditions.add("PL");
        if(this.conditionPO) conditions.add("PO");

        return conditions;
    }

    public ArrayList<String> getLanguagesCard(){

        ArrayList<String> languages = new ArrayList<String>();
        if(this.languageEN) languages.add("English");
        if(this.languageFR) languages.add("French");
        if(this.languageGR) languages.add("German");
        if(this.languageSP) languages.add("Spanish");
        if(this.languageIT) languages.add("Italian");
        if(this.languageCH) languages.add("Chinese");
        if(this.languageJP) languages.add("Japanese");
        if(this.languagePT) languages.add("Portuguese");
        if(this.languageRU) languages.add("Russian");
        if(this.languageKO) languages.add("Korean");
        if(this.languageCHT) languages.add("T-Chinese");


        return languages;

    }

     /* public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }*/

}
