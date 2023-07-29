<template>
  <div>
    <el-upload :action="serverIp + '/toolbox/wordToPdf?exchangeType=pdf'" :show-file-list="false" accept="docx"
               :on-success="handleSuccess" style="display: inline-block">
      <el-button type="primary" class="ml-5">上传Word.docx</el-button>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: "WordToPdf",
  data() {
    return {
      serverIp: process.env.VUE_APP_BASE_URL,
      fileName: "",
    }
  },
  created() {
    this.load()
  },
  methods: {
    reset() {
      this.fileName = ""
      this.load()
    },
    load() {

    },
    delBatch() {

    },
    handleSuccess(res, file, fileList) {
      this.$message.success("文件上传成功");
      window.open(this.serverIp + `/` + res.data)
      this.load();
    }
  }
}
</script>