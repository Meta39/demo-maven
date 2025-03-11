package com.fu.springboot3demo.entity.hibernate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// 定义层级配置类
public class HierarchyConfig {

    // 层级配置类
    private static class LevelConfig {
        String parentKey; // 父层级的键
        String[] keys; // 当前层级的键
        Function<Map<String, Object>, Map<String, Object>> creator; // 当前层级的创建方法

        LevelConfig(String parentKey, String[] keys, Function<Map<String, Object>, Map<String, Object>> creator) {
            this.parentKey = parentKey;
            this.keys = keys;
            this.creator = creator;
        }
    }

    // 存储层级配置信息的列表
    private final List<LevelConfig> levels = new ArrayList<>();

    // 添加层级配置的方法（根节点）
    public HierarchyConfig addRootLevel(String[] keys, Function<Map<String, Object>, Map<String, Object>> creator) {
        return addChildLevel(null, keys, creator);
    }

    // 添加层级配置的方法（子节点）
    public HierarchyConfig addChildLevel(String parentKey, String[] keys, Function<Map<String, Object>, Map<String, Object>> creator) {
        levels.add(new LevelConfig(parentKey, keys, creator));
        return this;
    }

    // 转换方法：将平面结构的数据转换为嵌套的层级结构
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> transform(List<Map<String, Object>> records) {
        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> result = new ArrayList<>();
        Deque<HierarchyTask> stack = new ArrayDeque<>(); // 使用栈代替递归

        // 初始化栈：处理第一层数据
        LevelConfig firstLevel = levels.get(0);
        Map<Object, List<Map<String, Object>>> firstGroups = groupRecordsByKeys(records, firstLevel.keys);
        for (Map.Entry<Object, List<Map<String, Object>>> entry : firstGroups.entrySet()) {
            Map<String, Object> rootNode = firstLevel.creator.apply(entry.getValue().get(0)); // 创建根节点
            result.add(rootNode); // 将根节点添加到结果中
            stack.push(new HierarchyTask(rootNode, entry.getValue(), 1)); // 将任务压入栈
        }

        // 迭代处理所有层级
        while (!stack.isEmpty()) {
            HierarchyTask task = stack.pop(); // 弹出任务
            Map<String, Object> parent = task.parent; // 当前父节点
            List<Map<String, Object>> currentRecords = task.records; // 当前记录
            int nextLevelIndex = task.nextLevelIndex; // 下一个层级的索引

            // 如果层级已处理完毕，跳过
            if (nextLevelIndex >= levels.size()) {
                continue;
            }

            LevelConfig nextLevel = levels.get(nextLevelIndex); // 获取下一个层级的配置
            Map<Object, List<Map<String, Object>>> grouped = groupRecordsByKeys(currentRecords, nextLevel.keys); // 分组

            for (Map.Entry<Object, List<Map<String, Object>>> entry : grouped.entrySet()) {
                Map<String, Object> childNode = nextLevel.creator.apply(entry.getValue().get(0)); // 创建子节点

                if (nextLevel.parentKey != null) {
                    // 将子节点添加到父节点的列表中
                    List<Map<String, Object>> children = (List<Map<String, Object>>) parent.computeIfAbsent(nextLevel.parentKey, k -> new ArrayList<>());
                    children.add(childNode);
                }

                stack.push(new HierarchyTask(childNode, entry.getValue(), nextLevelIndex + 1)); // 将任务压入栈
            }
        }

        return result;
    }

    // 辅助类：封装层级处理任务
    private static class HierarchyTask {
        Map<String, Object> parent; // 父节点
        List<Map<String, Object>> records; // 当前记录
        int nextLevelIndex; // 下一个层级的索引

        HierarchyTask(Map<String, Object> parent, List<Map<String, Object>> records, int nextLevelIndex) {
            this.parent = parent;
            this.records = records;
            this.nextLevelIndex = nextLevelIndex;
        }
    }

    // 分组逻辑：根据指定的键对记录进行分组
    private Map<Object, List<Map<String, Object>>> groupRecordsByKeys(List<Map<String, Object>> records, String[] keys) {
        if (keys.length == 1) {
            // 单键分组
            String key = keys[0];
            return records.stream().collect(Collectors.groupingBy(record -> record.get(key)));
        } else {
            // 多键分组
            return records.stream().collect(Collectors.groupingBy(record -> Arrays.stream(keys)
                    .map(record::get)
                    .collect(Collectors.toList())));
        }
    }

}