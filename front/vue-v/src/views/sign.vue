<template>
  <el-container>
    <el-header height="60px">
      <h2>VolunterPro</h2>
    </el-header>
    <el-container>
      <el-aside width="150px">
        <el-menu>
          <el-menu-item index="2" @click="toProject">选项1</el-menu-item>
          <el-menu-item index="3" @click="toActivity">选项2</el-menu-item>
          <el-menu-item index="4" @click="toBlog">选项3</el-menu-item>
          <el-menu-item index="5" @click="toAbout">选项4</el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  methods: {
    toVolunteer() {
      this.$router.push('/volunteer')
    },
    toProject() {
      this.$router.push('/project')
    },
    toActivity() {
      this.$router.push('/activity')
    },
    toBlog() {
      this.$router.push('/blog')
    },
    toAbout() {
      this.$router.push('/about')
    }
  }
}
</script>

<style>
.el-header {
  background-color: #409EFF;
  color: white;
}
.el-footer {
  background-color: #909399;
  color: black;
  text-align: center;
}
.el-aside {
  background-color: chartreuse;
}
.el-main {
  background-color: darkkhaki;
}
.el-col {
  height: 200px;
}
.col1 {
  background-color: teal;
}
.col2 {
  background-color: tomato;
}
.col3 {
  background-color: thistle;
}
</style>
