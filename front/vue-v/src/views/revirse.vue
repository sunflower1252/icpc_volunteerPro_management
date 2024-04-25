<template>

    <el-input v-model="input1" placeholder="请输入姓名" :disabled="!isEditing" @blur="handleBlur(1)">
  <el-button slot="append" :icon="isEditing ? 'el-icon-check' : 'el-icon-edit'" @click="handleEdit(1)" :type="isEditing ? 'success' : 'primary'" size="mini"></el-button>
</el-input>
</template>
<script>
export default {
  data() {
    return {
      isEditing: false,
      originalValue: '',
      input1: '',
    };
  },
  methods: {
    handleEdit(index) {
      if (index === 1) {
        this.originalValue = this.input1;
      }
      this.isEditing = !this.isEditing;
    },
    handleBlur(index) {
      if (this.input1 !== this.originalValue && !this.isEditing) {
        this.$confirm('您有未保存的修改，是否保存？', '提示', {
          confirmButtonText: '保存',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          this.input1 = this.originalValue;
          this.$message({
            type: 'success',
            message: '保存成功!'
          });
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消保存'
          });
        });
      }
    },
  },
};

</script>