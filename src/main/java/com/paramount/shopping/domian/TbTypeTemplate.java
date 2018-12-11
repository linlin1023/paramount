package com.paramount.shopping.domian;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TbTypeTemplate implements Serializable{
    private Long id;

    private String name;

    private String specIds;

    private List<Map> specIdsList;

    private String brandIds;

    private List<Map> brandIdsList;

    private String customAttributeItems;

    private List<Map> customAttributeItemsList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSpecIds() {
        return specIds;
    }

    public void setSpecIds(String specIds) {
        if(specIds != null && specIds.length() > 0 ){
            this.specIds = specIds == null ? null : specIds.trim();
            this.specIdsList = JSON.parseArray(this.specIds,Map.class);
        }
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        if(brandIds != null && brandIds.length() > 0 ){
            this.brandIds = brandIds == null ? null : brandIds.trim();
            this.brandIdsList = JSON.parseArray(this.brandIds,Map.class);
        }

    }

    public String getCustomAttributeItems() {
        return customAttributeItems;
    }

    public void setCustomAttributeItems(String customAttributeItems) {
        if(customAttributeItems != null && !customAttributeItems.equals("")&& customAttributeItems.length()>0){
            this.customAttributeItems = customAttributeItems == null ? null : customAttributeItems.trim();
            this.customAttributeItemsList = JSON.parseArray(this.customAttributeItems, Map.class);
        }
    }

    public List<Map> getSpecIdsList() {
        return specIdsList;
    }

    public void setSpecIdsList(List<Map> specIdsList) {
        this.specIdsList = specIdsList;
        this.specIds = JSON.toJSONString(specIdsList);
    }

    public List<Map> getBrandIdsList() {
        return brandIdsList;
    }

    public void setBrandIdsList(List<Map> brandIdsList) {
        this.brandIdsList = brandIdsList;
        this.brandIds = JSON.toJSONString(brandIdsList);
    }

    public List<Map> getCustomAttributeItemsList() {
        return customAttributeItemsList;
    }

    public void setCustomAttributeItemsList(List<Map> customAttributeItemsList) {
        this.customAttributeItemsList = customAttributeItemsList;
        this.customAttributeItems = JSON.toJSONString(customAttributeItemsList);
    }

    @Override
    public String toString() {
        return "TbTypeTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specIds='" + specIds + '\'' +
                ", specIdsList=" + specIdsList +
                ", brandIds='" + brandIds + '\'' +
                ", brandIdsList=" + brandIdsList +
                ", customAttributeItems='" + customAttributeItems + '\'' +
                ", customAttributeItemsList=" + customAttributeItemsList +
                '}';
    }
}