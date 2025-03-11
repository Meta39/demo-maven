package com.fu.springboot3demo.entity.hibernate;

import java.util.LinkedHashMap;
import java.util.Map;

// 定义数据转换器类
public class DataTransformer {
    // 创建患者信息的方法
    public static Map<String, Object> createPatient(Map<String, Object> record) {
        Map<String, Object> patient = new LinkedHashMap<>();
        patient.put("SourcePatientId", record.get("SourcePatientId"));
        patient.put("MedicalCardType", record.get("MedicalCardType"));
        patient.put("MedicalCardID", record.get("MedicalCardID"));
        return patient;
    }

    // 创建就诊信息的方法
    public static Map<String, Object> createMedicalInformations(Map<String, Object> record) {
        Map<String, Object> medicalInfo = new LinkedHashMap<>();
        medicalInfo.put("VisitDateTime", record.get("VisitDateTime"));
        medicalInfo.put("DeptCode", record.get("DeptCode"));
        medicalInfo.put("DeptName", record.get("DeptName"));
        medicalInfo.put("DoctorId", record.get("DoctorId"));
        medicalInfo.put("DoctorName", record.get("DoctorName"));
        medicalInfo.put("TotalCost", record.get("TotalCost"));
        medicalInfo.put("VisitId", record.get("VisitId"));
        medicalInfo.put("Remark", record.get("Remark"));
        return medicalInfo;
    }

    // 创建费用项目信息的方法
    public static Map<String, Object> createCostItemLists(Map<String, Object> record) {
        Map<String, Object> costItem = new LinkedHashMap<>();
        costItem.put("CostDate", record.get("CostDate"));
        costItem.put("CostItemId", record.get("CostItemId"));
        costItem.put("CostItemName", record.get("CostItemName"));
        costItem.put("CostItemCount", record.get("CostItemCount"));
        costItem.put("FeeNo", record.get("FeeNo"));
        return costItem;
    }

    // 创建费用明细信息的方法
    public static Map<String, Object> createDetailsItemLists(Map<String, Object> record) {
        Map<String, Object> detailItem = new LinkedHashMap<>();
        detailItem.put("CostId", record.get("CostId"));
        detailItem.put("CostName", record.get("CostName"));
        detailItem.put("DrugSpecifications", record.get("DrugSpecifications"));
        detailItem.put("CostNumber", record.get("CostNumber"));
        detailItem.put("NumberUnit", record.get("NumberUnit"));
        detailItem.put("CostPrice", record.get("CostPrice"));
        return detailItem;
    }
}
