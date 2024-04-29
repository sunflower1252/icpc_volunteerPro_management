<template>
  <el-container style="width:100vw;height:100vh;">
    <el-header height="120px" style="position: relative;">
      <div style="display: flex; align-items: center; justify-content: space-between;">
        <h1 style="float: none;">VolunterPro</h1>
        <div style="flex: 1;">
          <el-badge :value="0" class="item">
            <el-button size="small">通知</el-button>
          </el-badge>
          <el-badge :value="0" class="item">
            <el-button size="small">消息</el-button>
          </el-badge>
        </div>

        <el-menu style="position: absolute; top: 0; right: 0;">
          <el-menu-item index="1" @click="tomyself">个人管理中心</el-menu-item>
          <el-menu-item index="2" @click="toLogin">登录/注册</el-menu-item>
        </el-menu>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px" style="background-size:contain;">
        <el-menu>
          <el-menu-item index="1" @click="toProject">消防</el-menu-item>
          <el-menu-item index="2" @click="toActivity">抗震救灾</el-menu-item>
          <el-menu-item index="3" @click="toBlog">社区</el-menu-item>
          <el-menu-item index="4" @click="toAbout">其他</el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  methods: {
    toVolunteer() {
      this.$router.push('/volunteer');
    },
    toProject() {
      this.$router.push('/project');
    },
    toActivity() {
      this.$router.push('/activity');
    },
    toBlog() {
      this.$router.push('/blog');
    },
    toAbout() {
      this.$router.push('/about');
    },
    toLogin() {
      this.$router.push('/Login');
    },
    tomyself() {
      this.$router.push('/myself');
    },
  },
};
</script>  
<style>
.el-container {
  width: 100vw;
  height: 100vh;
}

.el-header {
  background-color: #d6520a;
  color: rgb(60, 27, 222);
}
.el-aside{
  background-image: url('../assets/E-1157834-4576F4AB.jpg');
  background-size:cover;
  background-position:center; 
  width:10px;
}

.el-main {
  width:10px;
  background-image: url('../assets/350.jpg');
  background-size: cover;
  background-position: center;
}

/* 添加以下样式以调整菜单的位置 */
.el-header .el-menu {
  padding-top: 15px; /* 适应 header 的高度 */
}
.el-button {
  /* 修改所有按钮的样式 */
  background-color: #42b983; /* 背景色 */
  color: #fff; /* 文字颜色 */
  border: 1px solid #42b983; /* 边框颜色 */
  border-radius: 3px; /* 边框圆角 */
  font-size: 14px; /* 字体大小 */
  padding: 8px 16px; /* 内边距 */
  /* 可以添加 hover、active 状态的样式 */
  &:hover {
    background-color: #36a276;
  }
  &:active {
    background-color: #308c66;
  }
}
.el-button--small {
  /* 专为 size="small" 的按钮设置样式 */
  font-size: 12px;
}

</style>