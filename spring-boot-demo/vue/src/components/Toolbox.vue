<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane v-for="item in tabs" :label="item.label" :name="item.name">
        <components :is="item.name" v-if="item.name === activeName"></components>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import wordToPdf from "@/components/toolbox/WordToPdf";
export default {
  name: "Toolbox",
  components:{
    wordToPdf,
  },
  data() {
    return {
      activeName: "wordToPdf",
      tabs:[{
        name:"wordToPdf",
        label:'Word转Pdf',
      },{
        name:"wordToExcel",
        label:'word转Excel',
      }]
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
}
</script>
